package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvidenciaDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return ConexaoFactory.conexao();
    }

    
    
    public void inserir(Evidencia e) throws SQLException, ClassNotFoundException {
        int novoId;
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT NVL(MAX(id_evid), 0) + 1 FROM evidencia");
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO evidencia (id_evid, ds_arquivo, tp_arquivo, dt_envio, fk_beneficiario_id_bene, fk_caso_id_caso) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoId);
            stmt.setString(2, e.getArquivo());
            stmt.setString(3, e.getTipoArquivo());  
            stmt.setDate(4, Date.valueOf(e.getDataEnvio()));
            stmt.setInt(5, e.getBeneficiario().getIdBeneficiario());
            if (e.getCaso() == null || e.getCaso().getIdCaso() == 0)
                stmt.setNull(6, Types.INTEGER);
            else
                stmt.setInt(6, e.getCaso().getIdCaso());
            stmt.executeUpdate();
            e.setIdEvidencia(novoId);
        }
    }

    public void atualizar(Evidencia e) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE evidencia SET ds_arquivo=?, tp_arquivo=?, dt_envio=?, fk_beneficiario_id_bene=?, fk_caso_id_caso=? WHERE id_evid=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, e.getArquivo());
            stmt.setString(2, e.getTipoArquivo());
            stmt.setDate(3, Date.valueOf(e.getDataEnvio()));
            stmt.setInt(4, e.getBeneficiario().getIdBeneficiario());
            if (e.getCaso() == null || e.getCaso().getIdCaso() == 0)
                stmt.setNull(5, Types.INTEGER);
            else
                stmt.setInt(5, e.getCaso().getIdCaso());
            stmt.setInt(6, e.getIdEvidencia());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM evidencia WHERE id_evid=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Evidencia buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM evidencia WHERE id_evid=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<Evidencia> selecionar() throws SQLException, ClassNotFoundException {
        List<Evidencia> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM evidencia ORDER BY id_evid");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(conexao, rs));
        }
        return lista;
    }

    private Evidencia montar(Connection conexao, ResultSet rs) throws SQLException {
        Evidencia e = new Evidencia();
        e.setIdEvidencia(rs.getInt("id_evid"));
        e.setArquivo(rs.getString("ds_arquivo"));
        e.setTipoArquivo(rs.getString("tp_arquivo"));
        Date data = rs.getDate("dt_envio");
        if (data != null) e.setDataEnvio(data.toLocalDate());
        e.setBeneficiario(RelacionamentoDAO.buscarBeneficiario(conexao, rs.getInt("fk_beneficiario_id_bene")));
        int idCaso = rs.getInt("fk_caso_id_caso");
        if (!rs.wasNull()) e.setCaso(RelacionamentoDAO.buscarCaso(conexao, idCaso));
        return e;
    }
}

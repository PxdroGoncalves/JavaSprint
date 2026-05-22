package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoStatusDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return ConexaoFactory.conexao();
    }

    
    
    public void inserir(HistoricoStatus h) throws SQLException, ClassNotFoundException {
        int novoId;
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT NVL(MAX(id_hist), 0) + 1 FROM historico_status");
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO historico_status (id_hist, dt_alteracao, st_anterior, st_novo, fk_caso_id_caso, fk_integrante_id_integ) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoId);
            
            stmt.setDate(2, h.getDataAlteracao() != null
                    ? Date.valueOf(h.getDataAlteracao())
                    : Date.valueOf(java.time.LocalDate.now()));
            
            if (h.getStatusAnterior() != null && !h.getStatusAnterior().isBlank()) {
                stmt.setString(3, h.getStatusAnterior());
            } else {
                stmt.setNull(3, Types.VARCHAR);
            }
            stmt.setString(4, h.getStatusNovo());
            stmt.setInt(5, h.getCaso().getIdCaso());
            
            if (h.getIntegrante() != null && h.getIntegrante().getIdIntegrante() > 0) {
                stmt.setInt(6, h.getIntegrante().getIdIntegrante());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.executeUpdate();
            h.setIdHistorico(novoId);
        }
    }

    public void atualizar(HistoricoStatus h) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE historico_status SET dt_alteracao=?, st_anterior=?, st_novo=?, fk_caso_id_caso=?, fk_integrante_id_integ=? WHERE id_hist=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, h.getDataAlteracao() != null
                    ? Date.valueOf(h.getDataAlteracao())
                    : Date.valueOf(java.time.LocalDate.now()));
            if (h.getStatusAnterior() != null && !h.getStatusAnterior().isBlank()) {
                stmt.setString(2, h.getStatusAnterior());
            } else {
                stmt.setNull(2, Types.VARCHAR);
            }
            stmt.setString(3, h.getStatusNovo());
            stmt.setInt(4, h.getCaso().getIdCaso());
            if (h.getIntegrante() != null && h.getIntegrante().getIdIntegrante() > 0) {
                stmt.setInt(5, h.getIntegrante().getIdIntegrante());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.setInt(6, h.getIdHistorico());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM historico_status WHERE id_hist=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public HistoricoStatus buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM historico_status WHERE id_hist=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<HistoricoStatus> selecionar() throws SQLException, ClassNotFoundException {
        List<HistoricoStatus> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM historico_status ORDER BY id_hist");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(conexao, rs));
        }
        return lista;
    }

    private HistoricoStatus montar(Connection conexao, ResultSet rs) throws SQLException {
        HistoricoStatus h = new HistoricoStatus();
        h.setIdHistorico(rs.getInt("id_hist"));
        Date data = rs.getDate("dt_alteracao");
        if (data != null) h.setDataAlteracao(data.toLocalDate());
        h.setStatusAnterior(rs.getString("st_anterior"));
        h.setStatusNovo(rs.getString("st_novo"));
        h.setCaso(RelacionamentoDAO.buscarCaso(conexao, rs.getInt("fk_caso_id_caso")));
        int idInteg = rs.getInt("fk_integrante_id_integ");
        if (!rs.wasNull()) h.setIntegrante(RelacionamentoDAO.buscarIntegrante(conexao, idInteg));
        return h;
    }
}

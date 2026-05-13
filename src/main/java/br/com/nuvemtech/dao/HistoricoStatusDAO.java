package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoStatusDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(HistoricoStatus h) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO historico_status (id_hist, dt_alteracao, st_novo, fk_caso_id_caso, fk_integrante_id_integ) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, h.getIdHistorico());
            stmt.setTimestamp(2, Timestamp.valueOf(h.getDataAlteracao()));
            stmt.setString(3, h.getStatus());
            stmt.setInt(4, h.getCaso().getIdCaso());
            stmt.setInt(5, h.getIntegrante().getIdIntegrante());
            stmt.executeUpdate();
        }
    }

    public void atualizar(HistoricoStatus h) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE historico_status SET dt_alteracao=?, st_novo=?, fk_caso_id_caso=?, fk_integrante_id_integ=? WHERE id_hist=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(h.getDataAlteracao()));
            stmt.setString(2, h.getStatus());
            stmt.setInt(3, h.getCaso().getIdCaso());
            stmt.setInt(4, h.getIntegrante().getIdIntegrante());
            stmt.setInt(5, h.getIdHistorico());
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
        Timestamp data = rs.getTimestamp("dt_alteracao");
        if (data != null) h.setDataAlteracao(data.toLocalDateTime());
        h.setStatus(rs.getString("st_novo"));
        h.setCaso(RelacionamentoDAO.buscarCaso(conexao, rs.getInt("fk_caso_id_caso")));
        h.setIntegrante(RelacionamentoDAO.buscarIntegrante(conexao, rs.getInt("fk_integrante_id_integ")));
        return h;
    }
}

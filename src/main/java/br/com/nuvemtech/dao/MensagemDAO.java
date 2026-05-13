package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MensagemDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Mensagem m) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO mensagem (id_mens, dt_mens, tx_mens, tp_remetente,fk_beneficiario_id_bene, fk_dentista_id_dent, fk_integrante_id_integ)VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, m.getIdMensagem());
            stmt.setTimestamp(2, Timestamp.valueOf(m.getDataEnvio()));
            stmt.setString(3, m.getTexto());
            stmt.setString(4, m.getRemetente());
            stmt.setNull(5, Types.INTEGER);
            stmt.setNull(6, Types.INTEGER);
            stmt.setNull(7, Types.INTEGER);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Mensagem m) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE mensagem SET dt_mens=?, tx_mens=?, tp_remetente=?,fk_beneficiario_id_bene=?,fk_dentista_id_dent=?,fk_integrante_id_integ=? WHERE id_mens=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(m.getDataEnvio()));
            stmt.setString(2, m.getTexto());
            stmt.setString(3, m.getRemetente());
            if (m.getBeneficiario() != null) stmt.setInt(4, m.getBeneficiario().getIdBeneficiario());
            else stmt.setNull(4, Types.INTEGER);
            if (m.getDentista() != null) stmt.setInt(5, m.getDentista().getIdDentista());
            else stmt.setNull(5, Types.INTEGER);
            if (m.getIntegrante() != null) stmt.setInt(6, m.getIntegrante().getIdIntegrante());
            else stmt.setNull(6, Types.INTEGER);
            stmt.setInt(7, m.getIdMensagem());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM mensagem WHERE id_mens=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Mensagem buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM mensagem WHERE id_mens=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<Mensagem> selecionar() throws SQLException, ClassNotFoundException {
        List<Mensagem> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM mensagem ORDER BY id_mens");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(conexao, rs));
        }
        return lista;
    }

    private Mensagem montar(Connection conexao, ResultSet rs) throws SQLException {
        Mensagem m = new Mensagem();
        m.setIdMensagem(rs.getInt("id_mens"));
        m.setTexto(rs.getString("tx_mens"));
        m.setRemetente(rs.getString("tp_remetente"));
        Timestamp data = rs.getTimestamp("dt_mens");
        if (data != null) m.setDataEnvio(data.toLocalDateTime());
        if (rs.getObject("fk_beneficiario_id_bene") != null)
            m.setBeneficiario(RelacionamentoDAO.buscarBeneficiario(conexao, rs.getInt("fk_beneficiario_id_bene")));
        if (rs.getObject("fk_dentista_id_dent") != null)
            m.setDentista(RelacionamentoDAO.buscarDentista(conexao, rs.getInt("fk_dentista_id_dent")));
        if (rs.getObject("fk_integrante_id_integ") != null)
            m.setIntegrante(RelacionamentoDAO.buscarIntegrante(conexao, rs.getInt("fk_integrante_id_integ")));
        return m;
    }
}

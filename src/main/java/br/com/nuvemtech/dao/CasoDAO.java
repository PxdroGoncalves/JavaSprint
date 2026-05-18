package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CasoDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Caso c) throws SQLException, ClassNotFoundException {

        int novoId = 1;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmtId = conexao.prepareStatement("SELECT NVL(MAX(id_caso),0)+1 FROM caso");
             ResultSet rs = stmtId.executeQuery()) {

            if (rs.next()) novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO caso (id_caso, dt_abertura, dt_fechamento, st_caso, fk_beneficiario_id_bene, fk_dentista_id_dent, fk_integrante_id_integ) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoId);
            stmt.setDate(2, Date.valueOf(c.getDataAbertura()));

            if (c.getDataFechamento() == null) {
                stmt.setNull(3, Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(c.getDataFechamento()));
            }

            stmt.setString(4, c.getStatus());
            stmt.setInt(5, c.getBeneficiario().getIdBeneficiario());

            if (c.getDentista() == null || c.getDentista().getIdDentista() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, c.getDentista().getIdDentista());
            }

            stmt.setInt(7, c.getIntegrante().getIdIntegrante());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Caso c) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE caso SET dt_abertura=?, dt_fechamento=?, st_caso=?, fk_beneficiario_id_bene=?, fk_dentista_id_dent=?, fk_integrante_id_integ=? WHERE id_caso=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(c.getDataAbertura()));

            if (c.getDataFechamento() == null) stmt.setNull(2, Types.DATE);
            else stmt.setDate(2, Date.valueOf(c.getDataFechamento()));

            stmt.setString(3, c.getStatus());
            stmt.setInt(4, c.getBeneficiario().getIdBeneficiario());

            if (c.getDentista() == null || c.getDentista().getIdDentista() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, c.getDentista().getIdDentista());
            }

            stmt.setInt(6, c.getIntegrante().getIdIntegrante());
            stmt.setInt(7, c.getIdCaso());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM caso WHERE id_caso=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Caso buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM caso WHERE id_caso=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return RelacionamentoDAO.montarCaso(conexao, rs);
                return null;
            }
        }
    }

    public List<Caso> selecionar() throws SQLException, ClassNotFoundException {
        List<Caso> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM caso ORDER BY id_caso");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(RelacionamentoDAO.montarCaso(conexao, rs));
        }
        return lista;
    }

    public void atualizarStatus(Caso c) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE caso SET st_caso=?, dt_fechamento=?, fk_dentista_id_dent=? WHERE id_caso=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, c.getStatus());

            if (c.getDataFechamento() == null) stmt.setNull(2, Types.DATE);
            else stmt.setDate(2, Date.valueOf(c.getDataFechamento()));

            if (c.getDentista() == null || c.getDentista().getIdDentista() == 0) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, c.getDentista().getIdDentista());
            }

            stmt.setInt(4, c.getIdCaso());

            stmt.executeUpdate();
        }
    }
}
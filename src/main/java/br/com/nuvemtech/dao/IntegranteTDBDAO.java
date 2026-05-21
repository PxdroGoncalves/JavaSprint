package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.IntegranteTDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntegranteTDBDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(IntegranteTDB i) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO integrante_tdb (id_integ, nm_integ, email_integ, cargo, dt_cadastro, senha_integ) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, i.getIdIntegrante());
            stmt.setString(2, i.getNome());
            stmt.setString(3, i.getEmail());
            stmt.setString(4, i.getCargo());
            stmt.setDate(5, Date.valueOf(i.getDataCadastro()));
            stmt.setString(6, i.getSenha());
            stmt.executeUpdate();
        }
    }

    public void cadastrar(IntegranteTDB i) throws SQLException, ClassNotFoundException {
        int novoId;
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT NVL(MAX(id_integ), 0) + 1 FROM integrante_tdb");
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO integrante_tdb (id_integ, nm_integ, email_integ, cargo, dt_cadastro, senha_integ) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoId);
            stmt.setString(2, i.getNome());
            stmt.setString(3, i.getEmail());
            stmt.setString(4, i.getCargo());
            stmt.setDate(5, Date.valueOf(i.getDataCadastro() != null ? i.getDataCadastro() : java.time.LocalDate.now()));
            stmt.setString(6, i.getSenha());
            stmt.executeUpdate();
        }
    }

    public void atualizar(IntegranteTDB i) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE integrante_tdb SET nm_integ=?, email_integ=?, cargo=?, dt_cadastro=?, senha_integ=? WHERE id_integ=?";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, i.getNome());
            stmt.setString(2, i.getEmail());
            stmt.setString(3, i.getCargo());
            stmt.setDate(4, Date.valueOf(i.getDataCadastro()));
            stmt.setString(5, i.getSenha());
            stmt.setInt(6, i.getIdIntegrante());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("DELETE FROM integrante_tdb WHERE id_integ=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public IntegranteTDB buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM integrante_tdb WHERE id_integ=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
                return null;
            }
        }
    }

    public List<IntegranteTDB> selecionar() throws SQLException, ClassNotFoundException {
        List<IntegranteTDB> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM integrante_tdb ORDER BY id_integ"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        }
        return lista;
    }

    public IntegranteTDB login(String email, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM integrante_tdb WHERE email_integ = ? AND senha_integ = ?";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
            }
        }
        return null;
    }

    private IntegranteTDB montar(ResultSet rs) throws SQLException {
        IntegranteTDB i = new IntegranteTDB();
        i.setIdIntegrante(rs.getInt("id_integ"));
        i.setNome(rs.getString("nm_integ"));
        i.setEmail(rs.getString("email_integ"));
        i.setCargo(rs.getString("cargo"));
        Date cadastro = rs.getDate("dt_cadastro");
        if (cadastro != null) i.setDataCadastro(cadastro.toLocalDate());
        i.setSenha(rs.getString("senha_integ"));
        return i;
    }
}

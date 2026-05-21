package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.Dentista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DentistaDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Dentista d) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO dentista (id_dent, nm_dent, cro_dent, especialidade, email_dent, telefone_dent, dt_cadastro, senha_dent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, d.getIdDentista());
            stmt.setString(2, d.getNome());
            stmt.setString(3, d.getCro());
            stmt.setString(4, d.getEspecialidade());
            stmt.setString(5, d.getEmail());
            stmt.setString(6, d.getTelefone());
            stmt.setDate(7, Date.valueOf(d.getDataCadastro()));
            stmt.setString(8, d.getSenha());
            stmt.executeUpdate();
        }
    }

    public void cadastrar(Dentista d) throws SQLException, ClassNotFoundException {
        int novoId;
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT NVL(MAX(id_dent), 0) + 1 FROM dentista");
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO dentista (id_dent, nm_dent, cro_dent, especialidade, email_dent, telefone_dent, dt_cadastro, senha_dent) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoId);
            stmt.setString(2, d.getNome());
            stmt.setString(3, d.getCro());
            stmt.setString(4, d.getEspecialidade());
            stmt.setString(5, d.getEmail().toLowerCase());
            stmt.setString(6, d.getTelefone());
            stmt.setDate(7, Date.valueOf(d.getDataCadastro() != null ? d.getDataCadastro() : java.time.LocalDate.now()));
            stmt.setString(8, d.getSenha());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Dentista d) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE dentista SET nm_dent=?, cro_dent=?, especialidade=?, email_dent=?, telefone_dent=?, dt_cadastro=?, senha_dent=? WHERE id_dent=?";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, d.getNome());
            stmt.setString(2, d.getCro());
            stmt.setString(3, d.getEspecialidade());
            stmt.setString(4, d.getEmail());
            stmt.setString(5, d.getTelefone());
            stmt.setDate(6, Date.valueOf(d.getDataCadastro()));
            stmt.setString(7, d.getSenha());
            stmt.setInt(8, d.getIdDentista());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("DELETE FROM dentista WHERE id_dent=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Dentista buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM dentista WHERE id_dent=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
                return null;
            }
        }
    }

    public List<Dentista> selecionar() throws SQLException, ClassNotFoundException {
        List<Dentista> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM dentista ORDER BY id_dent"); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        }
        return lista;
    }

    public Dentista login(String email, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM dentista WHERE email_dent = ? AND senha_dent = ?";
        try (Connection conexao = abrirConexao(); PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
            }
        }
        return null;
    }

    private Dentista montar(ResultSet rs) throws SQLException {
        Dentista d = new Dentista();
        d.setIdDentista(rs.getInt("id_dent"));
        d.setNome(rs.getString("nm_dent"));
        d.setCro(rs.getString("cro_dent"));
        d.setEspecialidade(rs.getString("especialidade"));
        d.setEmail(rs.getString("email_dent"));
        d.setTelefone(rs.getString("telefone_dent"));
        Date cadastro = rs.getDate("dt_cadastro");
        if (cadastro != null) d.setDataCadastro(cadastro.toLocalDate());
        d.setSenha(rs.getString("senha_dent"));
        return d;
    }
}

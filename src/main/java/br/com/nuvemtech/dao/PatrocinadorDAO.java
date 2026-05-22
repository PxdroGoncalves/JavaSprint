package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.Patrocinador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatrocinadorDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return ConexaoFactory.conexao();
    }

    public void inserir(Patrocinador p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO patrocinador (id_patr, nm_patr, email_patr, cpf_cnpj_patr, telefone_patr, senha_patr) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdPatrocinador());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getCpfCnpj());
            stmt.setString(5, p.getTelefone());
            stmt.setString(6, p.getSenha());
            stmt.executeUpdate();
        }
    }

    public void cadastrar(Patrocinador p) throws SQLException, ClassNotFoundException {

        if (p.getNome() == null || p.getNome().isBlank()) {
            throw new RuntimeException("Nome obrigatório");
        }

        if (p.getEmail() == null || p.getEmail().isBlank()) {
            throw new RuntimeException("Email obrigatório");
        }

        if (p.getSenha() == null || p.getSenha().isBlank()) {
            throw new RuntimeException("Senha obrigatória");
        }

        if (p.getCpfCnpj() == null ||
                !(p.getCpfCnpj().length() == 11 || p.getCpfCnpj().length() == 14)) {

            throw new RuntimeException("CPF/CNPJ deve conter 11 ou 14 números");
        }

        String verificaEmail =
                "SELECT COUNT(*) FROM patrocinador WHERE email_patr = ?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(verificaEmail)) {

            stmt.setString(1, p.getEmail().toLowerCase());

            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new RuntimeException("Email já cadastrado");
            }
        }

        int novoId;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(
                     "SELECT NVL(MAX(id_patr), 0) + 1 FROM patrocinador");
             ResultSet rs = stmt.executeQuery()) {

            rs.next();
            novoId = rs.getInt(1);
        }

        p.setIdPatrocinador(novoId);

        String sql =
        "INSERT INTO patrocinador (id_patr,nm_patr,email_patr,cpf_cnpj_patr,senha_patr) VALUES (?, ?, ?, ?, ?, ?)"
   ;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoId);
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getEmail().toLowerCase());
            stmt.setString(4, p.getCpfCnpj());
            stmt.setString(5, p.getTelefone());
            stmt.setString(6, p.getSenha());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Patrocinador p) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE patrocinador SET nm_patr=?, email_patr=?, cpf_cnpj_patr=?, telefone_patr=?, senha_patr=? WHERE id_patr=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getCpfCnpj());
            stmt.setString(4, p.getTelefone());
            stmt.setString(5, p.getSenha());
            stmt.setInt(6, p.getIdPatrocinador());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM patrocinador WHERE id_patr=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Patrocinador buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM patrocinador WHERE id_patr=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
                return null;
            }
        }
    }

    public Patrocinador login(String email, String senha) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM patrocinador WHERE email_patr = ? AND senha_patr = ?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email.toLowerCase());
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
            }
        }
        return null;
    }

    public List<Patrocinador> selecionar() throws SQLException, ClassNotFoundException {
        List<Patrocinador> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM patrocinador ORDER BY id_patr");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(rs));
        }
        return lista;
    }

    private Patrocinador montar(ResultSet rs) throws SQLException {
        Patrocinador p = new Patrocinador();
        p.setIdPatrocinador(rs.getInt("id_patr"));
        p.setNome(rs.getString("nm_patr"));
        p.setEmail(rs.getString("email_patr"));
        p.setCpfCnpj(rs.getString("cpf_cnpj_patr"));
        p.setTelefone(rs.getString("telefone_patr"));
        p.setSenha(rs.getString("senha_patr"));
        return p;
    }
}

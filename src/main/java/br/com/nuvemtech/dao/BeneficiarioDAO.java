package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.Beneficiario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Beneficiario b) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO beneficiario (id_bene, nm_bene, cpf_bene, dt_nasc, email_bene, telefone_bene, endereco_bene, dt_cadastro, senha_bene) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, b.getIdBeneficiario());
            stmt.setString(2, b.getNome());
            stmt.setString(3, b.getCpf());
            stmt.setDate(4, Date.valueOf(b.getDataNascimento()));
            stmt.setString(5, b.getEmail());
            stmt.setString(6, b.getTelefone());
            stmt.setString(7, b.getEndereco());
            stmt.setDate(8, Date.valueOf(b.getDataCadastro()));
            stmt.setString(9, b.getSenha());

            stmt.executeUpdate();
        }
    }

    public void atualizar(Beneficiario b) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE beneficiario SET nm_bene=?, cpf_bene=?, dt_nasc=?, email_bene=?, telefone_bene=?, endereco_bene=?, dt_cadastro=?, senha_bene=? WHERE id_bene=?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, b.getNome());
            stmt.setString(2, b.getCpf());
            stmt.setDate(3, Date.valueOf(b.getDataNascimento()));
            stmt.setString(4, b.getEmail());
            stmt.setString(5, b.getTelefone());
            stmt.setString(6, b.getEndereco());
            stmt.setDate(7, Date.valueOf(b.getDataCadastro()));
            stmt.setString(8, b.getSenha());
            stmt.setInt(9, b.getIdBeneficiario());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt =
                     conexao.prepareStatement(
                             "DELETE FROM beneficiario WHERE id_bene=?"
                     )) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Beneficiario buscarPorId(int id)
            throws SQLException, ClassNotFoundException {

        String sql =
                "SELECT * FROM beneficiario WHERE id_bene=?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return montar(rs);
                }

                return null;
            }
        }
    }

    public List<Beneficiario> selecionar()
            throws SQLException, ClassNotFoundException {

        List<Beneficiario> lista =
                new ArrayList<>();

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt =
                     conexao.prepareStatement(
                             "SELECT * FROM beneficiario ORDER BY id_bene"
                     );
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montar(rs));
            }
        }

        return lista;
    }

    public Beneficiario login(String email, String senha)
            throws SQLException, ClassNotFoundException {

        String sql = """
                SELECT *
                FROM beneficiario
                WHERE email_bene = ?
                AND senha_bene = ?
                """;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt =
                     conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return montar(rs);
                }
            }
        }

        return null;
    }

    public Beneficiario buscarPorEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM dentista WHERE email_bene = ?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(rs);
                return null;
            }
        }
    }

    private Beneficiario montar(ResultSet rs)
            throws SQLException {

        Beneficiario b =
                new Beneficiario();

        b.setIdBeneficiario(
                rs.getInt("id_bene")
        );

        b.setNome(
                rs.getString("nm_bene")
        );

        b.setCpf(
                rs.getString("cpf_bene")
        );

        Date nasc =
                rs.getDate("dt_nasc");

        if (nasc != null) {
            b.setDataNascimento(
                    nasc.toLocalDate()
            );
        }

        b.setEmail(
                rs.getString("email_bene")
        );

        b.setTelefone(
                rs.getString("telefone_bene")
        );

        b.setEndereco(
                rs.getString("endereco_bene")
        );

        Date cadastro =
                rs.getDate("dt_cadastro");

        if (cadastro != null) {
            b.setDataCadastro(
                    cadastro.toLocalDate()
            );
        }

        b.setSenha(
                rs.getString("senha_bene")
        );

        return b;
    }
}
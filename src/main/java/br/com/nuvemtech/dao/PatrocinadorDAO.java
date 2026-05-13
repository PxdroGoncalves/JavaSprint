package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.Patrocinador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatrocinadorDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Patrocinador p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO patrocinador (id_patr, nm_patr, email_patr, anonimo) VALUES (?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdPatrocinador());
            stmt.setString(2, p.getNome());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getAnonimo());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Patrocinador p) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE patrocinador SET nm_patr=?, email_patr=?, anonimo=? WHERE id_patr=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getAnonimo());
            stmt.setInt(4, p.getIdPatrocinador());
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
        p.setAnonimo(rs.getString("anonimo"));
        return p;
    }
}

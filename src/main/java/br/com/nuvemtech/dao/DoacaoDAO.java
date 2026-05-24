package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return ConexaoFactory.conexao();
    }

    public void inserir(Doacao d) throws SQLException, ClassNotFoundException {
        int novoId;
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT NVL(MAX(id_doac), 0) + 1 FROM doacao");
             ResultSet rs = stmt.executeQuery()) {
            rs.next();
            novoId = rs.getInt(1);
        }

        String sql = "INSERT INTO doacao (id_doac, tp_doac, vl_doac, ds_equipamento, dt_doac, fk_patrocinador_id_patr) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoId);
            stmt.setString(2, d.getTipo());
            if (d.getValor() == null) stmt.setNull(3, Types.NUMERIC);
            else stmt.setBigDecimal(3, BigDecimal.valueOf(d.getValor()));
            stmt.setString(4, d.getDescricaoEquipamento());
            stmt.setDate(5, Date.valueOf(d.getDataDoacao()));
            stmt.setInt(6, d.getPatrocinador().getIdPatrocinador());
            stmt.executeUpdate();
            d.setIdDoacao(novoId);
        }
    }

    public void atualizar(Doacao d) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE doacao SET tp_doac=?, vl_doac=?, ds_equipamento=?, dt_doac=?, fk_patrocinador_id_patr=? WHERE id_doac=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, d.getTipo());
            if (d.getValor() == null) stmt.setNull(2, Types.NUMERIC);
            else stmt.setBigDecimal(2, BigDecimal.valueOf(d.getValor()));
            stmt.setString(3, d.getDescricaoEquipamento());
            stmt.setDate(4, Date.valueOf(d.getDataDoacao()));
            stmt.setInt(5, d.getPatrocinador().getIdPatrocinador());
            stmt.setInt(6, d.getIdDoacao());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM doacao WHERE id_doac=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Doacao buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM doacao WHERE id_doac=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<Doacao> selecionar() throws SQLException, ClassNotFoundException {
        List<Doacao> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM doacao ORDER BY id_doac");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(conexao, rs));
        }
        return lista;
    }

    public List<Doacao> buscarPorPatrocinador(int idPatrocinador) throws SQLException, ClassNotFoundException {
        List<Doacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM doacao WHERE fk_patrocinador_id_patr = ? ORDER BY dt_doac DESC";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPatrocinador);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) lista.add(montar(conexao, rs));
            }
        }
        return lista;
    }

    private Doacao montar(Connection conexao, ResultSet rs) throws SQLException {
        Doacao d = new Doacao();
        d.setIdDoacao(rs.getInt("id_doac"));
        d.setTipo(rs.getString("tp_doac"));
        BigDecimal valor = rs.getBigDecimal("vl_doac");
        if (valor != null) d.setValor(valor.doubleValue());
        d.setDescricaoEquipamento(rs.getString("ds_equipamento"));
        Date data = rs.getDate("dt_doac");
        if (data != null) d.setDataDoacao(data.toLocalDate());
        d.setPatrocinador(RelacionamentoDAO.buscarPatrocinador(conexao, rs.getInt("fk_patrocinador_id_patr")));
        return d;
    }
}

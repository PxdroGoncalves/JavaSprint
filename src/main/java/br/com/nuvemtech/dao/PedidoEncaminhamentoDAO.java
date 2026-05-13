package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoEncaminhamentoDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(PedidoEncaminhamento p) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO pedido_encaminhamento (id_pedido, dt_pedido, st_pedido, fk_caso_id_caso, fk_dentista_id_dent) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, p.getIdPedido());
            stmt.setDate(2, Date.valueOf(p.getDataPedido()));
            stmt.setString(3, p.getStatus());
            stmt.setInt(4, p.getCaso().getIdCaso());
            stmt.setInt(5, p.getDentista().getIdDentista());
            stmt.executeUpdate();
        }
    }

    public void atualizar(PedidoEncaminhamento p) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE pedido_encaminhamento SET dt_pedido=?, st_pedido=?, fk_caso_id_caso=?, fk_dentista_id_dent=? WHERE id_pedido=?";
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(p.getDataPedido()));
            stmt.setString(2, p.getStatus());
            stmt.setInt(3, p.getCaso().getIdCaso());
            stmt.setInt(4, p.getDentista().getIdDentista());
            stmt.setInt(5, p.getIdPedido());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM pedido_encaminhamento WHERE id_pedido=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public PedidoEncaminhamento buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pedido_encaminhamento WHERE id_pedido=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<PedidoEncaminhamento> selecionar() throws SQLException, ClassNotFoundException {
        List<PedidoEncaminhamento> lista = new ArrayList<>();
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM pedido_encaminhamento ORDER BY id_pedido");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montar(conexao, rs));
        }
        return lista;
    }

    private PedidoEncaminhamento montar(Connection conexao, ResultSet rs) throws SQLException {
        PedidoEncaminhamento p = new PedidoEncaminhamento();
        p.setIdPedido(rs.getInt("id_pedido"));
        Date data = rs.getDate("dt_pedido");
        if (data != null) p.setDataPedido(data.toLocalDate());
        p.setStatus(rs.getString("st_pedido"));
        p.setCaso(RelacionamentoDAO.buscarCaso(conexao, rs.getInt("fk_caso_id_caso")));
        p.setDentista(RelacionamentoDAO.buscarDentista(conexao, rs.getInt("fk_dentista_id_dent")));
        return p;
    }
}

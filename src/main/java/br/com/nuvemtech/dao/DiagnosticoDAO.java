package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {

    private Connection abrirConexao() throws SQLException, ClassNotFoundException {
        return new ConexaoFactory().conexao();
    }

    public void inserir(Diagnostico d) throws SQLException, ClassNotFoundException {

        int novoId = 1;

        try (Connection conexao = abrirConexao();
             PreparedStatement stmtId = conexao.prepareStatement("SELECT NVL(MAX(id_diag),0)+1 FROM diagnostico");
             ResultSet rs = stmtId.executeQuery()) {

            if (rs.next()) novoId = rs.getInt(1);
        }

        d.setIdDiagnostico(novoId);

        String sql = "INSERT INTO diagnostico (id_diag, dt_diag, ds_diag, fk_caso_id_caso, fk_dentista_id_dent) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novoId);
            stmt.setDate(2, Date.valueOf(d.getDataDiagnostico()));
            stmt.setString(3, d.getDescricao());
            stmt.setInt(4, d.getCaso().getIdCaso());

            if (d.getDentista() == null || d.getDentista().getIdDentista() == 0) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, d.getDentista().getIdDentista());
            }

            stmt.executeUpdate();
        }
    }

    public void atualizar(Diagnostico d) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE diagnostico SET dt_diag=?, ds_diag=?, fk_caso_id_caso=?, fk_dentista_id_dent=? WHERE id_diag=?";

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(d.getDataDiagnostico()));
            stmt.setString(2, d.getDescricao());
            stmt.setInt(3, d.getCaso().getIdCaso());

            if (d.getDentista() == null || d.getDentista().getIdDentista() == 0) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, d.getDentista().getIdDentista());
            }

            stmt.setInt(5, d.getIdDiagnostico());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM diagnostico WHERE id_diag=?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Diagnostico buscarPorId(int id) throws SQLException, ClassNotFoundException {
        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM diagnostico WHERE id_diag=?")) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return montar(conexao, rs);
                return null;
            }
        }
    }

    public List<Diagnostico> selecionar() throws SQLException, ClassNotFoundException {
        List<Diagnostico> lista = new ArrayList<>();

        try (Connection conexao = abrirConexao();
             PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM diagnostico ORDER BY id_diag");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(montar(conexao, rs));
            }
        }

        return lista;
    }

    private Diagnostico montar(Connection conexao, ResultSet rs) throws SQLException {
        Diagnostico d = new Diagnostico();

        d.setIdDiagnostico(rs.getInt("id_diag"));
        d.setDescricao(rs.getString("ds_diag"));

        Date data = rs.getDate("dt_diag");
        if (data != null) d.setDataDiagnostico(data.toLocalDate());

        d.setCaso(
                RelacionamentoDAO.buscarCaso(
                        conexao,
                        rs.getInt("fk_caso_id_caso")
                )
        );

        d.setDentista(
                RelacionamentoDAO.buscarDentista(
                        conexao,
                        rs.getInt("fk_dentista_id_dent")
                )
        );

        return d;
    }
}
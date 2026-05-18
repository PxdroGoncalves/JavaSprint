package br.com.nuvemtech.dao;

import br.com.nuvemtech.conexoes.ConexaoFactory;
import br.com.nuvemtech.entities.*;

import java.sql.*;

public class RelacionamentoDAO {

    public static Beneficiario buscarBeneficiario(Connection conexao, int id) throws SQLException {
        if (id <= 0) return null;
        String sql = "SELECT * FROM beneficiario WHERE id_bene = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                Beneficiario b = new Beneficiario();
                b.setIdBeneficiario(rs.getInt("id_bene"));
                b.setNome(rs.getString("nm_bene"));
                b.setCpf(rs.getString("cpf_bene"));

                Date nasc = rs.getDate("dt_nasc");
                if (nasc != null) b.setDataNascimento(nasc.toLocalDate());

                b.setEmail(rs.getString("email_bene"));
                b.setTelefone(rs.getString("telefone_bene"));
                b.setEndereco(rs.getString("endereco_bene"));

                Date cadastro = rs.getDate("dt_cadastro");
                if (cadastro != null) b.setDataCadastro(cadastro.toLocalDate());

                b.setSenha(rs.getString("senha_bene"));
                return b;
            }
        }
    }

    public static Dentista buscarDentista(Connection conexao, int id) throws SQLException {
        if (id <= 0) return null;
        String sql = "SELECT * FROM dentista WHERE id_dent = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

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
    }

    public static IntegranteTDB buscarIntegrante(Connection conexao, int id) throws SQLException {
        if (id <= 0) return null;
        String sql = "SELECT * FROM integrante_tdb WHERE id_integ = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

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
    }

    public static Patrocinador buscarPatrocinador(Connection conexao, int id) throws SQLException {
        if (id <= 0) return null;
        String sql = "SELECT * FROM patrocinador WHERE id_patr = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;

                Patrocinador p = new Patrocinador();
                p.setIdPatrocinador(rs.getInt("id_patr"));
                p.setNome(rs.getString("nm_patr"));
                p.setEmail(rs.getString("email_patr"));
                p.setAnonimo(rs.getString("anonimo"));
                return p;
            }
        }
    }

    public static Caso buscarCaso(Connection conexao, int id) throws SQLException {
        if (id <= 0) return null;
        String sql = "SELECT * FROM caso WHERE id_caso = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) return null;
                return montarCaso(conexao, rs);
            }
        }
    }

    public static Caso montarCaso(Connection conexao, ResultSet rs) throws SQLException {
        Caso c = new Caso();

        c.setIdCaso(rs.getInt("id_caso"));

        Date abertura = rs.getDate("dt_abertura");
        if (abertura != null) c.setDataAbertura(abertura.toLocalDate());

        Date fechamento = rs.getDate("dt_fechamento");
        if (fechamento != null) c.setDataFechamento(fechamento.toLocalDate());

        c.setStatus(rs.getString("st_caso"));

        c.setBeneficiario(
                buscarBeneficiario(
                        conexao,
                        rs.getInt("fk_beneficiario_id_bene")
                )
        );

        int idDentista = rs.getInt("fk_dentista_id_dent");
        if (!rs.wasNull()) {
            c.setDentista(buscarDentista(conexao, idDentista));
        }

        c.setIntegrante(
                buscarIntegrante(
                        conexao,
                        rs.getInt("fk_integrante_id_integ")
                )
        );

        c.setTemDiagnostico(
                existeDiagnostico(
                        conexao,
                        c.getIdCaso()
                )
        );

        return c;
    }

    public static boolean existeDiagnostico(Connection conexao, int idCaso) throws SQLException {
        String sql = "SELECT COUNT(*) FROM diagnostico WHERE fk_caso_id_caso = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idCaso);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
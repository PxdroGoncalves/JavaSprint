package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.BeneficiarioBO;
import br.com.nuvemtech.entities.Beneficiario;
import java.sql.SQLException;
import java.util.List;

public class BeneficiarioService {
    private final BeneficiarioBO bo = new BeneficiarioBO();

    public List<Beneficiario> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Beneficiario buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Beneficiario obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Beneficiario obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }

    public Beneficiario login(String email, String senha)
            throws SQLException, ClassNotFoundException {
        return bo.loginBo(email, senha);
    }
}

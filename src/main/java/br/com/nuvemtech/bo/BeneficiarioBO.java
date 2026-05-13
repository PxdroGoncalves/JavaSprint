package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.BeneficiarioDAO;
import br.com.nuvemtech.entities.Beneficiario;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class BeneficiarioBO {
    public List<Beneficiario> selecionarBo() throws SQLException, ClassNotFoundException {
        return new BeneficiarioDAO().selecionar();
    }

    public Beneficiario buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Beneficiario obj = new BeneficiarioDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Beneficiario obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new BeneficiarioDAO().inserir(obj);
    }

    public void atualizarBo(Beneficiario obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new BeneficiarioDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new BeneficiarioDAO().deletar(id);
    }

    private void validar(Beneficiario obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdBeneficiario() <= 0) throw new RegraNegocioException("ID invalido.");
    }

}

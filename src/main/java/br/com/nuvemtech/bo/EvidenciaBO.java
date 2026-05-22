package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.EvidenciaDAO;
import br.com.nuvemtech.entities.Evidencia;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class EvidenciaBO {

    public List<Evidencia> selecionarBo() throws SQLException, ClassNotFoundException {
        return new EvidenciaDAO().selecionar();
    }

    public Evidencia buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Evidencia obj = new EvidenciaDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Evidencia obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDate.now());
        new EvidenciaDAO().inserir(obj);
    }

    public void atualizarBo(Evidencia obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDate.now());
        new EvidenciaDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new EvidenciaDAO().deletar(id);
    }

    private void validarDados(Evidencia obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getArquivo() == null || obj.getArquivo().isBlank()) throw new RegraNegocioException("Arquivo obrigatorio.");
        if (obj.getBeneficiario() == null || obj.getBeneficiario().getIdBeneficiario() <= 0)
            throw new RegraNegocioException("Beneficiario obrigatorio.");
    }

    private void validar(Evidencia obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdEvidencia() <= 0) throw new RegraNegocioException("ID invalido.");
        validarDados(obj);
    }
}
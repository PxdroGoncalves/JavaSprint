package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.CasoDAO;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CasoBO {

    public void inserirBo(Caso caso)
            throws SQLException, ClassNotFoundException {

        validarInsercao(caso);

        if (caso.getDataAbertura() == null) {
            caso.setDataAbertura(LocalDate.now());
        }

        if (caso.getStatus() == null ||
                caso.getStatus().isBlank()) {
            caso.abrir();
        }

        new CasoDAO().inserir(caso);
    }

    public void atualizarBo(Caso caso)
            throws SQLException, ClassNotFoundException {

        validarAtualizacao(caso);

        if (caso.getDataAbertura() == null) {
            caso.setDataAbertura(LocalDate.now());
        }

        new CasoDAO().atualizar(caso);
    }

    public void deletarBo(int id)
            throws SQLException, ClassNotFoundException {

        if (id <= 0) {
            throw new RegraNegocioException(
                    "ID do caso invalido.");
        }

        new CasoDAO().deletar(id);
    }

    public Caso buscarPorIdBo(int id)
            throws SQLException, ClassNotFoundException {

        if (id <= 0) {
            throw new RegraNegocioException(
                    "ID do caso invalido.");
        }

        return new CasoDAO().buscarPorId(id);
    }

    public List<Caso> listarTodosBo()
            throws SQLException, ClassNotFoundException {

        return new CasoDAO().selecionar();
    }

    private void validarInsercao(Caso caso) {

        if (caso == null) {
            throw new RegraNegocioException(
                    "Dados do caso nao informados.");
        }

        if (caso.getBeneficiario() == null ||
                caso.getBeneficiario()
                        .getIdBeneficiario() <= 0) {

            throw new RegraNegocioException(
                    "Beneficiario obrigatorio.");
        }

        if (caso.getIntegrante() == null ||
                caso.getIntegrante()
                        .getIdIntegrante() <= 0) {

            throw new RegraNegocioException(
                    "Integrante obrigatorio.");
        }
    }

    private void validarAtualizacao(Caso caso) {

        validarInsercao(caso);

        if (caso.getIdCaso() <= 0) {
            throw new RegraNegocioException(
                    "ID do caso invalido.");
        }
    }
}
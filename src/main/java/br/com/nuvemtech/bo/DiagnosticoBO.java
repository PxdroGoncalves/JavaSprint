package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.CasoDAO;
import br.com.nuvemtech.dao.DiagnosticoDAO;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.entities.Diagnostico;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DiagnosticoBO {

    public void inserirBo(Diagnostico d)
            throws SQLException, ClassNotFoundException {

        validarInsercao(d);

        if (d.getDataDiagnostico() == null) {
            d.setDataDiagnostico(LocalDate.now());
        }

        new DiagnosticoDAO().inserir(d);

        CasoDAO casoDAO = new CasoDAO();
        Caso caso =
                casoDAO.buscarPorId(
                        d.getCaso().getIdCaso());

        if (caso != null) {
            caso.registrarDiagnostico();
            casoDAO.atualizarStatus(caso);
        }
    }

    public void atualizarBo(Diagnostico d)
            throws SQLException, ClassNotFoundException {

        validarAtualizacao(d);

        if (d.getDataDiagnostico() == null) {
            d.setDataDiagnostico(LocalDate.now());
        }

        new DiagnosticoDAO().atualizar(d);
    }

    public void deletarBo(int id)
            throws SQLException, ClassNotFoundException {

        if (id <= 0) {
            throw new RegraNegocioException(
                    "ID do diagnostico invalido.");
        }

        new DiagnosticoDAO().deletar(id);
    }

    public Diagnostico buscarPorIdBo(int id)
            throws SQLException, ClassNotFoundException {

        if (id <= 0) {
            throw new RegraNegocioException(
                    "ID do diagnostico invalido.");
        }

        return new DiagnosticoDAO().buscarPorId(id);
    }

    public List<Diagnostico> listarTodosBo()
            throws SQLException, ClassNotFoundException {

        return new DiagnosticoDAO().selecionar();
    }

    private void validarInsercao(Diagnostico d) {

        if (d == null) {
            throw new RegraNegocioException(
                    "Dados do diagnostico nao informados.");
        }

        if (d.getDescricao() == null ||
                d.getDescricao().isBlank()) {

            throw new RegraNegocioException(
                    "Descricao obrigatoria.");
        }

        if (d.getCaso() == null ||
                d.getCaso().getIdCaso() <= 0) {

            throw new RegraNegocioException(
                    "Caso obrigatorio.");
        }

        if (d.getDentista() == null ||
                d.getDentista()
                        .getIdDentista() <= 0) {

            throw new RegraNegocioException(
                    "Dentista obrigatorio.");
        }
    }

    private void validarAtualizacao(
            Diagnostico d) {

        validarInsercao(d);

        if (d.getIdDiagnostico() <= 0) {

            throw new RegraNegocioException(
                    "ID do diagnostico invalido.");
        }
    }
}
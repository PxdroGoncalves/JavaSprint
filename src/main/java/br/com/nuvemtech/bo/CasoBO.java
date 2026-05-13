package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.CasoDAO;
import br.com.nuvemtech.dao.DiagnosticoDAO;
import br.com.nuvemtech.entities.*;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CasoBO {
    public List<Caso> selecionarBo() throws SQLException, ClassNotFoundException {
        return new CasoDAO().selecionar();
    }

    public Caso buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Caso caso = new CasoDAO().buscarPorId(id);
        if (caso == null) throw new NotFoundException("Caso nao encontrado.");
        return caso;
    }

    public void inserirBo(Caso caso) throws SQLException, ClassNotFoundException {
        validar(caso);
        if (caso.getDataAbertura() == null) caso.setDataAbertura(LocalDate.now());
        if (caso.getStatus() == null || caso.getStatus().isBlank()) caso.abrir();
        new CasoDAO().inserir(caso);
    }

    public void atualizarBo(Caso caso) throws SQLException, ClassNotFoundException {
        validar(caso);
        if (caso.getDataAbertura() == null) caso.setDataAbertura(LocalDate.now());
        new CasoDAO().atualizar(caso);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new CasoDAO().deletar(id);
    }

    public void enviarPedidoBo(int idCaso, Dentista dentista) throws SQLException, ClassNotFoundException {
        CasoDAO dao = new CasoDAO();
        Caso caso = dao.buscarPorId(idCaso);
        if (caso == null) throw new NotFoundException("Caso nao encontrado.");
        if (dentista == null || dentista.getIdDentista() <= 0) throw new RegraNegocioException("Dentista obrigatorio.");
        caso.enviarPedido(dentista);
        dao.atualizarStatus(caso);
    }

    public void fecharBo(int idCaso) throws SQLException, ClassNotFoundException {
        CasoDAO casoDAO = new CasoDAO();
        Caso caso = casoDAO.buscarPorId(idCaso);
        if (caso == null) throw new NotFoundException("Caso nao encontrado.");
        caso.setTemDiagnostico(new DiagnosticoDAO().existePorCaso(idCaso));
        if (!caso.fechar()) throw new RegraNegocioException("Nao e possivel fechar o caso sem diagnostico.");
        casoDAO.atualizarStatus(caso);
    }

    private void validar(Caso caso) {
        if (caso == null) throw new RegraNegocioException("Dados do caso nao informados.");
        if (caso.getIdCaso() <= 0) throw new RegraNegocioException("ID do caso invalido.");
        if (caso.getBeneficiario() == null || caso.getBeneficiario().getIdBeneficiario() <= 0) throw new RegraNegocioException("Beneficiario obrigatorio.");
        if (caso.getIntegrante() == null || caso.getIntegrante().getIdIntegrante() <= 0) throw new RegraNegocioException("Integrante obrigatorio. Informe o integrante no JSON, sem valor fixo no codigo.");
    }
}

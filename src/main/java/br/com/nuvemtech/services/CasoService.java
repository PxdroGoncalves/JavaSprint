package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.CasoBO;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.entities.Dentista;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class CasoService {

    private final CasoBO bo = new CasoBO();

    public List<Caso> selecionar() throws SQLException, ClassNotFoundException {
        return bo.listarTodosBo();
    }

    public Caso buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Caso obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Caso obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }

    public void enviarPedido(int idCaso, Dentista dentista) throws SQLException, ClassNotFoundException {
        if (dentista == null || dentista.getIdDentista() <= 0)
            throw new br.com.nuvemtech.exceptions.RegraNegocioException("Dentista obrigatorio.");
        Caso caso = bo.buscarPorIdBo(idCaso);
        if (caso == null)
            throw new br.com.nuvemtech.exceptions.NotFoundException("Caso nao encontrado.");
        caso.setDentista(dentista);
        if ("PENDENTE".equals(caso.getStatus())) {
            caso.setStatus("EM_ANDAMENTO");
        }
        bo.atualizarBo(caso);
    }

    public void fechar(int idCaso) throws SQLException, ClassNotFoundException {
        Caso caso = bo.buscarPorIdBo(idCaso);
        if (caso != null) {
            boolean fechou = caso.fechar();
            if (!fechou) {
                throw new RegraNegocioException("Caso nao pode ser fechado sem diagnostico registrado.");
            }
            bo.atualizarBo(caso);
        }
    }
}

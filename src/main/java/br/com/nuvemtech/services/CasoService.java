package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.CasoBO;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.entities.Dentista;

import java.sql.SQLException;
import java.util.List;

public class CasoService {

    private final CasoBO bo = new CasoBO();

    public List<Caso> selecionar()
            throws SQLException, ClassNotFoundException {

        return bo.listarTodosBo();
    }

    public Caso buscarPorId(int id)
            throws SQLException, ClassNotFoundException {

        return bo.buscarPorIdBo(id);
    }

    public void inserir(Caso obj)
            throws SQLException, ClassNotFoundException {

        bo.inserirBo(obj);
    }

    public void atualizar(Caso obj)
            throws SQLException, ClassNotFoundException {

        bo.atualizarBo(obj);
    }

    public void deletar(int id)
            throws SQLException, ClassNotFoundException {

        bo.deletarBo(id);
    }

    public void enviarPedido(int idCaso,
                             Dentista dentista)
            throws SQLException, ClassNotFoundException {
        Caso caso = bo.buscarPorIdBo(idCaso);

        if (caso != null && dentista != null) {
            caso.setDentista(dentista);
            if ("PENDENTE".equals(caso.getStatus())) {
                caso.setStatus("EM_ANDAMENTO");
            }
            bo.atualizarBo(caso);
        }
    }

    public void fechar(int idCaso)
            throws SQLException, ClassNotFoundException {

        Caso caso = bo.buscarPorIdBo(idCaso);

        if (caso != null) {
            caso.fechar();
            bo.atualizarBo(caso);
        }
    }
}
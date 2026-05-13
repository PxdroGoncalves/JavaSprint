package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.HistoricoStatusBO;
import br.com.nuvemtech.entities.HistoricoStatus;
import java.sql.SQLException;
import java.util.List;

public class HistoricoStatusService {
    private final HistoricoStatusBO bo = new HistoricoStatusBO();

    public List<HistoricoStatus> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public HistoricoStatus buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

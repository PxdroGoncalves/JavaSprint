package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.PedidoEncaminhamentoBO;
import br.com.nuvemtech.entities.PedidoEncaminhamento;
import java.sql.SQLException;
import java.util.List;

public class PedidoEncaminhamentoService {
    private final PedidoEncaminhamentoBO bo = new PedidoEncaminhamentoBO();

    public List<PedidoEncaminhamento> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public PedidoEncaminhamento buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(PedidoEncaminhamento obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(PedidoEncaminhamento obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public PedidoEncaminhamento aceitarPedido(int id) throws SQLException, ClassNotFoundException {
        return bo.aceitarPedidoBo(id);
    }

    public PedidoEncaminhamento recusarPedido(int id) throws SQLException, ClassNotFoundException {
        return bo.recusarPedidoBo(id);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

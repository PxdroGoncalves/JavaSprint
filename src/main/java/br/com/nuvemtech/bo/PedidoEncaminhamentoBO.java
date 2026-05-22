package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.PedidoEncaminhamentoDAO;
import br.com.nuvemtech.entities.PedidoEncaminhamento;
import br.com.nuvemtech.entities.Dentista;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class PedidoEncaminhamentoBO {

    public List<PedidoEncaminhamento> selecionarBo() throws SQLException, ClassNotFoundException {
        return new PedidoEncaminhamentoDAO().selecionar();
    }

    public PedidoEncaminhamento buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        PedidoEncaminhamento obj = new PedidoEncaminhamentoDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(PedidoEncaminhamento obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        if (obj.getDataPedido() == null) obj.setDataPedido(java.time.LocalDate.now());
        if (obj.getStatus() == null || obj.getStatus().isBlank()) obj.setStatus("PENDENTE");
        new PedidoEncaminhamentoDAO().inserir(obj);
    }

    public void atualizarBo(PedidoEncaminhamento obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataPedido() == null) obj.setDataPedido(java.time.LocalDate.now());
        new PedidoEncaminhamentoDAO().atualizar(obj);
    }

    public PedidoEncaminhamento aceitarPedidoBo(int id) throws SQLException, ClassNotFoundException {
        PedidoEncaminhamentoDAO dao = new PedidoEncaminhamentoDAO();
        PedidoEncaminhamento pedido = dao.buscarPorId(id);
        if (pedido == null) throw new NotFoundException("Pedido nao encontrado.");
        Dentista dentista = pedido.getDentista();
        if (dentista == null) dentista = new Dentista();
        dentista.aceitarPedido(pedido);
        dao.atualizar(pedido);
        return pedido;
    }

    public PedidoEncaminhamento recusarPedidoBo(int id) throws SQLException, ClassNotFoundException {
        PedidoEncaminhamentoDAO dao = new PedidoEncaminhamentoDAO();
        PedidoEncaminhamento pedido = dao.buscarPorId(id);
        if (pedido == null) throw new NotFoundException("Pedido nao encontrado.");
        Dentista dentista = pedido.getDentista();
        if (dentista == null) dentista = new Dentista();
        dentista.recusarPedido(pedido);
        dao.atualizar(pedido);
        return pedido;
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new PedidoEncaminhamentoDAO().deletar(id);
    }

    private void validarDados(PedidoEncaminhamento obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getCaso() == null || obj.getCaso().getIdCaso() <= 0)
            throw new RegraNegocioException("Caso obrigatorio.");
        if (obj.getDentista() == null || obj.getDentista().getIdDentista() <= 0)
            throw new RegraNegocioException("Dentista obrigatorio.");
        
        if (obj.getIntegrante() == null || obj.getIntegrante().getIdIntegrante() <= 0)
            throw new RegraNegocioException("Integrante obrigatorio.");
    }

    private void validar(PedidoEncaminhamento obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdPedido() <= 0) throw new RegraNegocioException("ID invalido.");
        validarDados(obj);
    }
}

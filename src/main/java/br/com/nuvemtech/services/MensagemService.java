package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.MensagemBO;
import br.com.nuvemtech.entities.Mensagem;
import java.sql.SQLException;
import java.util.List;

public class MensagemService {
    private final MensagemBO bo = new MensagemBO();

    public List<Mensagem> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Mensagem buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Mensagem obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Mensagem obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

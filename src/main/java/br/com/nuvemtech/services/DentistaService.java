package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.DentistaBO;
import br.com.nuvemtech.entities.Dentista;
import java.sql.SQLException;
import java.util.List;

public class DentistaService {
    private final DentistaBO bo = new DentistaBO();

    public List<Dentista> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Dentista buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Dentista obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Dentista obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

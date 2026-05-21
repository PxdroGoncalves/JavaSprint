package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.IntegranteTDBBO;
import br.com.nuvemtech.entities.IntegranteTDB;

import java.sql.SQLException;
import java.util.List;

public class IntegranteTDBService {

    private final IntegranteTDBBO bo = new IntegranteTDBBO();

    public List<IntegranteTDB> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public IntegranteTDB buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void cadastrar(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        bo.cadastrarBo(obj);
    }

    public void atualizar(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }

    public IntegranteTDB login(String email, String senha) throws SQLException, ClassNotFoundException {
        return bo.loginBo(email, senha);
    }
}

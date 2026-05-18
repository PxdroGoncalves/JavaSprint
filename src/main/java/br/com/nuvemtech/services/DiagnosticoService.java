package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.DiagnosticoBO;
import br.com.nuvemtech.entities.Diagnostico;

import java.sql.SQLException;
import java.util.List;

public class DiagnosticoService {

    private final DiagnosticoBO bo = new DiagnosticoBO();

    public List<Diagnostico> selecionar() throws SQLException, ClassNotFoundException {
        return bo.listarTodosBo();
    }

    public Diagnostico buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Diagnostico obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Diagnostico obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}
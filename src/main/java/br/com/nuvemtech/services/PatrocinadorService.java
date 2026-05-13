package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.PatrocinadorBO;
import br.com.nuvemtech.entities.Patrocinador;
import java.sql.SQLException;
import java.util.List;

public class PatrocinadorService {
    private final PatrocinadorBO bo = new PatrocinadorBO();

    public List<Patrocinador> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Patrocinador buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Patrocinador obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Patrocinador obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

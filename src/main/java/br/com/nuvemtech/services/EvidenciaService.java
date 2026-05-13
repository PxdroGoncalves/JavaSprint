package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.EvidenciaBO;
import br.com.nuvemtech.entities.Evidencia;
import java.sql.SQLException;
import java.util.List;

public class EvidenciaService {
    private final EvidenciaBO bo = new EvidenciaBO();

    public List<Evidencia> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Evidencia buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Evidencia obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Evidencia obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }
}

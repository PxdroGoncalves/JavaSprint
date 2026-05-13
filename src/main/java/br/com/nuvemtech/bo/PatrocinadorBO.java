package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.PatrocinadorDAO;
import br.com.nuvemtech.entities.Patrocinador;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class PatrocinadorBO {
    public List<Patrocinador> selecionarBo() throws SQLException, ClassNotFoundException {
        return new PatrocinadorDAO().selecionar();
    }

    public Patrocinador buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Patrocinador obj = new PatrocinadorDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Patrocinador obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        new PatrocinadorDAO().inserir(obj);
    }

    public void atualizarBo(Patrocinador obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        new PatrocinadorDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new PatrocinadorDAO().deletar(id);
    }

    private void validar(Patrocinador obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdPatrocinador() <= 0) throw new RegraNegocioException("ID invalido.");
    }
}

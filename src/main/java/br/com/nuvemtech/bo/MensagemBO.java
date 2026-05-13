package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.MensagemDAO;
import br.com.nuvemtech.entities.Mensagem;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class MensagemBO {
    public List<Mensagem> selecionarBo() throws SQLException, ClassNotFoundException { return new MensagemDAO().selecionar(); }
    public Mensagem buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Mensagem obj = new MensagemDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }
    public void inserirBo(Mensagem obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDateTime.now());
        new MensagemDAO().inserir(obj);
    }
    public void atualizarBo(Mensagem obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDateTime.now());
        new MensagemDAO().atualizar(obj);
    }
    public void deletarBo(int id) throws SQLException, ClassNotFoundException { new MensagemDAO().deletar(id); }
    private void validar(Mensagem obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdMensagem() <= 0) throw new RegraNegocioException("ID invalido.");
    }
}

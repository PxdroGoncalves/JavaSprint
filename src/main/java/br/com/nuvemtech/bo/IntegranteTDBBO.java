package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.IntegranteTDBDAO;
import br.com.nuvemtech.entities.IntegranteTDB;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class IntegranteTDBBO {

    public List<IntegranteTDB> selecionarBo() throws SQLException, ClassNotFoundException {
        return new IntegranteTDBDAO().selecionar();
    }

    public IntegranteTDB buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        IntegranteTDB obj = new IntegranteTDBDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new IntegranteTDBDAO().inserir(obj);
    }

    public void cadastrarBo(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getNome() == null || obj.getNome().isBlank()) throw new RegraNegocioException("Nome obrigatorio.");
        if (obj.getEmail() == null || obj.getEmail().isBlank()) throw new RegraNegocioException("Email obrigatorio.");
        if (obj.getSenha() == null || obj.getSenha().isBlank()) throw new RegraNegocioException("Senha obrigatoria.");
        new IntegranteTDBDAO().cadastrar(obj);
    }

    public void atualizarBo(IntegranteTDB obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new IntegranteTDBDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new IntegranteTDBDAO().deletar(id);
    }

    public IntegranteTDB loginBo(String email, String senha) throws SQLException, ClassNotFoundException {
        IntegranteTDB obj = new IntegranteTDBDAO().login(email, senha);
        if (obj == null) throw new NotFoundException("Email ou senha invalidos.");
        return obj;
    }

    private void validar(IntegranteTDB obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdIntegrante() <= 0) throw new RegraNegocioException("ID invalido.");
    }
}

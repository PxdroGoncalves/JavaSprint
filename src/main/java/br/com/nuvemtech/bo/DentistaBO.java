package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.DentistaDAO;
import br.com.nuvemtech.entities.Dentista;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class DentistaBO {

    public List<Dentista> selecionarBo() throws SQLException, ClassNotFoundException {
        return new DentistaDAO().selecionar();
    }

    public Dentista buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Dentista obj = new DentistaDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Dentista obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new DentistaDAO().inserir(obj);
    }

    public void cadastrarBo(Dentista obj) throws SQLException, ClassNotFoundException {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getNome() == null || obj.getNome().isBlank()) throw new RegraNegocioException("Nome obrigatorio.");
        if (obj.getEmail() == null || obj.getEmail().isBlank()) throw new RegraNegocioException("Email obrigatorio.");
        if (obj.getSenha() == null || obj.getSenha().isBlank()) throw new RegraNegocioException("Senha obrigatoria.");
        new DentistaDAO().cadastrar(obj);
    }

    public void atualizarBo(Dentista obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new DentistaDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new DentistaDAO().deletar(id);
    }

    public Dentista loginBo(String email, String senha) throws SQLException, ClassNotFoundException {
        Dentista obj = new DentistaDAO().login(email, senha);
        if (obj == null) throw new NotFoundException("Email ou senha invalidos.");
        return obj;
    }

    private void validar(Dentista obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdDentista() <= 0) throw new RegraNegocioException("ID invalido.");
    }

    private void validarDados(Dentista obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getNome() == null || obj.getNome().isBlank()) throw new RegraNegocioException("Nome obrigatorio.");
        if (obj.getCro() == null || obj.getCro().isBlank()) throw new RegraNegocioException("CRO obrigatorio.");
    }
}
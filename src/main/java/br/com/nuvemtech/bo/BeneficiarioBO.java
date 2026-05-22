package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.BeneficiarioDAO;
import br.com.nuvemtech.entities.Beneficiario;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class BeneficiarioBO {

    public List<Beneficiario> selecionarBo() throws SQLException, ClassNotFoundException {
        return new BeneficiarioDAO().selecionar();
    }

    public Beneficiario buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Beneficiario obj = new BeneficiarioDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Beneficiario obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        obj.setCpf(limparCpf(obj.getCpf()));
        if (obj.getSenha() == null || obj.getSenha().isBlank())
            obj.setSenha(gerarSenhaTemporaria(obj));
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new BeneficiarioDAO().cadastrar(obj);
    }

    public void cadastrarBo(Beneficiario obj) throws SQLException, ClassNotFoundException {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getNome() == null || obj.getNome().isBlank()) throw new RegraNegocioException("Nome obrigatorio.");
        if (obj.getEmail() == null || obj.getEmail().isBlank()) throw new RegraNegocioException("Email obrigatorio.");
        if (obj.getCpf() != null) obj.setCpf(limparCpf(obj.getCpf()));
        if (obj.getSenha() == null || obj.getSenha().isBlank())
            obj.setSenha(gerarSenhaTemporaria(obj));
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new BeneficiarioDAO().cadastrar(obj);
    }

    public void atualizarBo(Beneficiario obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getCpf() != null) obj.setCpf(limparCpf(obj.getCpf()));
        if (obj.getSenha() == null || obj.getSenha().isBlank())
            obj.setSenha(gerarSenhaTemporaria(obj));
        if (obj.getDataCadastro() == null) obj.setDataCadastro(java.time.LocalDate.now());
        new BeneficiarioDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new BeneficiarioDAO().deletar(id);
    }

    public Beneficiario loginBo(String email, String senha) throws SQLException, ClassNotFoundException {
        Beneficiario obj = new BeneficiarioDAO().login(email, senha);
        if (obj == null) throw new NotFoundException("Email ou senha invalidos.");
        return obj;
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    private String gerarSenhaTemporaria(Beneficiario obj) {
        String base = obj.getCpf() != null ? obj.getCpf().replaceAll("[^0-9]", "") : "";
        if (base.length() >= 6) return base.substring(0, 6);
        return "nuvem123";
    }

    private void validar(Beneficiario obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdBeneficiario() <= 0) throw new RegraNegocioException("ID invalido.");
    }

    private void validarDados(Beneficiario obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getNome() == null || obj.getNome().isBlank()) throw new RegraNegocioException("Nome obrigatorio.");
        if (obj.getCpf() == null || obj.getCpf().isBlank()) throw new RegraNegocioException("CPF obrigatorio.");
        if (obj.getDataNascimento() == null) throw new RegraNegocioException("Data de nascimento obrigatoria.");
    }
}

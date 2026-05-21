package br.com.nuvemtech.entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Beneficiario extends Pessoa {
    private int idBeneficiario;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private String endereco;
    private LocalDate dataCadastro;
    private String senha;

    public Beneficiario() {}

    public Beneficiario(int idBeneficiario, String nome, String email, String cpf, LocalDate dataNascimento, String telefone, String endereco, LocalDate dataCadastro, String senha) {
        super(nome, email);
        this.idBeneficiario = idBeneficiario;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
        this.senha = senha;
    }

    public int getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(int idBeneficiario) { this.idBeneficiario = idBeneficiario; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    @JsonIgnore
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "\n\n=== BENEFICIARIO ===" + super.toString() + "\nID: " + idBeneficiario + "\nCPF: " + cpf + "\nData Nascimento: " + dataNascimento + "\nTelefone: " + telefone + "\nEndereco: " + endereco;
    }
}

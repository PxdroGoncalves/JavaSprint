package br.com.nuvemtech.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class IntegranteTDB extends Pessoa {
    private int idIntegrante;
    private String cargo;
    private LocalDate dataCadastro;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public IntegranteTDB() {}

    public IntegranteTDB(int idIntegrante, String nome, String email, String cargo, LocalDate dataCadastro, String senha) {
        super(nome, email);
        this.idIntegrante = idIntegrante;
        this.cargo = cargo;
        this.dataCadastro = dataCadastro;
        this.senha = senha;
    }

    public int getIdIntegrante() { return idIntegrante; }
    public void setIdIntegrante(int idIntegrante) { this.idIntegrante = idIntegrante; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    @Override
    public String toString() {
        return "\n\n=== INTEGRANTE TDB ===" + super.toString() + "\nID: " + idIntegrante + "\nCargo: " + cargo;
    }
}

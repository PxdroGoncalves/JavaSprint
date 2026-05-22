package br.com.nuvemtech.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patrocinador extends Pessoa {

    private int idPatrocinador;
    private String cpfCnpj;
    private String telefone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public Patrocinador() {}

    public Patrocinador(int idPatrocinador, String nome, String email,
                        String cpfCnpj, String telefone, String senha) {
        super(nome, email);
        this.idPatrocinador = idPatrocinador;
        this.cpfCnpj = cpfCnpj;
        this.telefone = telefone;
        this.senha = senha;
    }

    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

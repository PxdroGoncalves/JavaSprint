package br.com.nuvemtech.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patrocinador extends Pessoa {
    private int idPatrocinador;
    private String anonimo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public Patrocinador() {}

    public Patrocinador(int idPatrocinador, String nome, String email, String anonimo, String senha) {
        super(nome, email);
        this.idPatrocinador = idPatrocinador;
        this.anonimo = anonimo;
        this.senha = senha;
    }

    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }
    public String getAnonimo() { return anonimo; }
    public void setAnonimo(String anonimo) { this.anonimo = anonimo; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}

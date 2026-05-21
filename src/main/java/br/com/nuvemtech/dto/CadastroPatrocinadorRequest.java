package br.com.nuvemtech.dto;

public class CadastroPatrocinadorRequest {

    private String nome;
    private String email;
    private String senha;
    private String anonimo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAnonimo() {
        return anonimo;
    }

    public void setAnonimo(String anonimo) {
        this.anonimo = anonimo;
    }
}
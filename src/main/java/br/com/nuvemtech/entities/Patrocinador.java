package br.com.nuvemtech.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Patrocinador extends Pessoa {

    private int idPatrocinador;
    private String tipoApoio;
    private String cnpjCpf;
    private String telefone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public Patrocinador() {}

    public Patrocinador(int idPatrocinador, String nome, String email,
                        String tipoApoio, String cnpjCpf, String telefone, String senha) {
        super(nome, email);
        this.idPatrocinador = idPatrocinador;
        this.tipoApoio = tipoApoio;
        this.cnpjCpf = cnpjCpf;
        this.telefone = telefone;
        this.senha = senha;
    }

    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }

    public String getTipoApoio() { return tipoApoio; }
    public void setTipoApoio(String tipoApoio) { this.tipoApoio = tipoApoio; }

    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String cnpjCpf) { this.cnpjCpf = cnpjCpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
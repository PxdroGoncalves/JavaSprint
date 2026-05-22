package br.com.nuvemtech.dto;

public class CadastroPatrocinadorRequest {

    private String nome;
    private String email;
    private String senha;
    private String tipoApoio;
    private String cnpjCpf;
    private String telefone;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTipoApoio() { return tipoApoio; }
    public void setTipoApoio(String tipoApoio) { this.tipoApoio = tipoApoio; }

    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String cnpjCpf) { this.cnpjCpf = cnpjCpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
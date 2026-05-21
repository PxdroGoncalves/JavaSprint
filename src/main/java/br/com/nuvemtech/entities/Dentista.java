package br.com.nuvemtech.entities;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Dentista extends Pessoa {
    private int idDentista;
    private String cro;
    private String especialidade;
    private String telefone;
    private LocalDate dataCadastro;
    private String senha;

    public Dentista() {}

    public Dentista(int idDentista, String nome, String email, String cro, String especialidade, String telefone, LocalDate dataCadastro, String senha) {
        super(nome, email);
        this.idDentista = idDentista;
        this.cro = cro;
        this.especialidade = especialidade;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.senha = senha;
    }

    public int getIdDentista() { return idDentista; }
    public void setIdDentista(int idDentista) { this.idDentista = idDentista; }
    public String getCro() { return cro; }
    public void setCro(String cro) { this.cro = cro; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }
    @JsonIgnore
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String aceitarPedido(PedidoEncaminhamento pedido) {
        pedido.setStatus("ACEITO");
        return "Pedido aceito pelo dentista.";
    }

    public String recusarPedido(PedidoEncaminhamento pedido) {
        pedido.setStatus("RECUSADO");
        return "Pedido recusado pelo dentista.";
    }

    @Override
    public String toString() {
        return "\n\n=== DENTISTA ===" + super.toString() + "\nID: " + idDentista + "\nCRO: " + cro + "\nEspecialidade: " + especialidade + "\nTelefone: " + telefone;
    }
}

package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class Doacao {
    private int idDoacao;
    private String tipo;
    private Double valor;
    private String descricaoEquipamento;
    private LocalDate dataDoacao;
    private Patrocinador patrocinador;

    public Doacao() {}

    public int getIdDoacao() { return idDoacao; }
    public void setIdDoacao(int idDoacao) { this.idDoacao = idDoacao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getDescricaoEquipamento() { return descricaoEquipamento; }
    public void setDescricaoEquipamento(String descricaoEquipamento) { this.descricaoEquipamento = descricaoEquipamento; }
    public LocalDate getDataDoacao() { return dataDoacao; }
    public void setDataDoacao(LocalDate dataDoacao) { this.dataDoacao = dataDoacao; }
    public Patrocinador getPatrocinador() { return patrocinador; }
    public void setPatrocinador(Patrocinador patrocinador) { this.patrocinador = patrocinador; }
}

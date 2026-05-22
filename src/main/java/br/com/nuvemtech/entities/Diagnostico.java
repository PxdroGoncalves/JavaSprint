package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class Diagnostico {
    private int idDiagnostico;
    private String descricao;
    private String procedimento;
    private LocalDate dataDiagnostico;
    private Caso caso;

    public Diagnostico() {}

    public Diagnostico(int idDiagnostico, String descricao, String procedimento,
                       LocalDate dataDiagnostico, Caso caso) {
        this.idDiagnostico = idDiagnostico;
        this.descricao = descricao;
        this.procedimento = procedimento;
        this.dataDiagnostico = dataDiagnostico;
        this.caso = caso;
    }

    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getProcedimento() { return procedimento; }
    public void setProcedimento(String procedimento) { this.procedimento = procedimento; }

    public LocalDate getDataDiagnostico() { return dataDiagnostico; }
    public void setDataDiagnostico(LocalDate dataDiagnostico) { this.dataDiagnostico = dataDiagnostico; }

    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }

    public void registrar(String descricao) {
        this.descricao = descricao;
        if (this.dataDiagnostico == null) this.dataDiagnostico = LocalDate.now();
    }
}

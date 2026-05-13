package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class Diagnostico {
    private int idDiagnostico;
    private String descricao;
    private LocalDate dataDiagnostico;
    private Caso caso;
    private String procedimento;
    private Beneficiario beneficiario;
    private Dentista dentista;

    public Diagnostico() {}

    public Diagnostico(int idDiagnostico, String descricao, LocalDate dataDiagnostico, Caso caso, String procedimento, Beneficiario beneficiario, Dentista dentista) {
        this.idDiagnostico = idDiagnostico;
        this.descricao = descricao;
        this.dataDiagnostico = dataDiagnostico;
        this.caso = caso;
        this.procedimento = procedimento;
        this.beneficiario = beneficiario;
        this.dentista = dentista;
    }

    public int getIdDiagnostico() { return idDiagnostico; }
    public void setIdDiagnostico(int idDiagnostico) { this.idDiagnostico = idDiagnostico; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDate getDataDiagnostico() { return dataDiagnostico; }
    public void setDataDiagnostico(LocalDate dataDiagnostico) { this.dataDiagnostico = dataDiagnostico; }
    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public void registrar(String descricao) {
        this.descricao = descricao;
        if (this.dataDiagnostico == null) this.dataDiagnostico = LocalDate.now();
    }
}

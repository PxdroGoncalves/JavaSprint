package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class Evidencia {
    private int idEvidencia;
    private String arquivo;
    private LocalDate dataEnvio;
    private Beneficiario beneficiario;
    private Caso caso;

    public Evidencia() {}

    public int getIdEvidencia() { return idEvidencia; }
    public void setIdEvidencia(int idEvidencia) { this.idEvidencia = idEvidencia; }
    public String getArquivo() { return arquivo; }
    public void setArquivo(String arquivo) { this.arquivo = arquivo; }
    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate dataEnvio) { this.dataEnvio = dataEnvio; }
    public Beneficiario getBeneficiario() { return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }
    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }

    public void enviar() {
        if (dataEnvio == null) dataEnvio = LocalDate.now();
    }
}

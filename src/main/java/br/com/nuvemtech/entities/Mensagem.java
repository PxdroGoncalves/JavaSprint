package br.com.nuvemtech.entities;

import java.time.LocalDateTime;

public class Mensagem {
    private int idMensagem;
    private String texto;
    private String remetente;
    private LocalDateTime dataEnvio;
    private Beneficiario beneficiario;
    private Dentista dentista;
    private IntegranteTDB integrante;

    public Mensagem() {}

    public Mensagem(int idMensagem, String texto, String remetente, LocalDateTime dataEnvio, Beneficiario beneficiario, Dentista dentista, IntegranteTDB integrante) {
        this.idMensagem = idMensagem;
        this.texto = texto;
        this.remetente = remetente;
        this.dataEnvio = dataEnvio;
        this.beneficiario = beneficiario;
        this.dentista = dentista;
        this.integrante = integrante;
    }

    public int getIdMensagem() { return idMensagem; }
    public void setIdMensagem(int idMensagem) { this.idMensagem = idMensagem; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public String getRemetente() { return remetente; }
    public void setRemetente(String remetente) { this.remetente = remetente; }
    public LocalDateTime getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDateTime dataEnvio) { this.dataEnvio = dataEnvio; }

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

    public IntegranteTDB getIntegrante() {
        return integrante;
    }

    public void setIntegrante(IntegranteTDB integrante) {
        this.integrante = integrante;
    }
}

package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class Caso {
    private int idCaso;
    private LocalDate dataAbertura;
    private LocalDate dataFechamento;
    private String status;
    private Beneficiario beneficiario;
    private Dentista dentista;
    private IntegranteTDB integrante;
    private boolean temDiagnostico;

    public Caso() {}

    public Caso(int idCaso, LocalDate dataAbertura, LocalDate dataFechamento, String status,
                Beneficiario beneficiario, Dentista dentista, IntegranteTDB integrante, boolean temDiagnostico) {
        this.idCaso = idCaso;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.status = status;
        this.beneficiario = beneficiario;
        this.dentista = dentista;
        this.integrante = integrante;
        this.temDiagnostico = temDiagnostico;
    }

    public int getIdCaso() { return idCaso; }
    public void setIdCaso(int idCaso) { this.idCaso = idCaso; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    public LocalDate getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDate dataFechamento) { this.dataFechamento = dataFechamento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Beneficiario getBeneficiario() { return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) { this.beneficiario = beneficiario; }
    public Dentista getDentista() { return dentista; }
    public void setDentista(Dentista dentista) { this.dentista = dentista; }
    public IntegranteTDB getIntegrante() { return integrante; }
    public void setIntegrante(IntegranteTDB integrante) { this.integrante = integrante; }
    public boolean isTemDiagnostico() { return temDiagnostico; }
    public void setTemDiagnostico(boolean temDiagnostico) { this.temDiagnostico = temDiagnostico; }

    
    public void abrir() {
        this.status = "PENDENTE";
        if (this.dataAbertura == null) this.dataAbertura = LocalDate.now();
    }

    public void enviarPedido(Dentista dentista) {
        this.dentista = dentista;
        this.status = "EM_ANDAMENTO";
    }

    public void registrarDiagnostico() {
        this.temDiagnostico = true;
        this.status = "EM_ANDAMENTO";
    }

    
    public boolean fechar() {
        if (!temDiagnostico) return false;
        this.status = "CONCLUIDO";
        this.dataFechamento = LocalDate.now();
        return true;
    }

    @Override
    public String toString() {
        return "\n\n=== CASO ===\nID: " + idCaso + "\nStatus: " + status +
               "\nData Abertura: " + dataAbertura + "\nData Fechamento: " + dataFechamento;
    }
}

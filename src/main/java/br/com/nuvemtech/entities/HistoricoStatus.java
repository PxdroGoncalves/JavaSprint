package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class HistoricoStatus {
    private int idHistorico;
    private LocalDate dataAlteracao;
    private String statusAnterior;
    private String statusNovo;
    private Caso caso;
    private IntegranteTDB integrante;

    public HistoricoStatus() {}

    public HistoricoStatus(int idHistorico, LocalDate dataAlteracao, String statusAnterior,
                           String statusNovo, Caso caso, IntegranteTDB integrante) {
        this.idHistorico = idHistorico;
        this.dataAlteracao = dataAlteracao;
        this.statusAnterior = statusAnterior;
        this.statusNovo = statusNovo;
        this.caso = caso;
        this.integrante = integrante;
    }

    public int getIdHistorico() { return idHistorico; }
    public void setIdHistorico(int idHistorico) { this.idHistorico = idHistorico; }

    public LocalDate getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(LocalDate dataAlteracao) { this.dataAlteracao = dataAlteracao; }

    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }

    public String getStatusNovo() { return statusNovo; }
    public void setStatusNovo(String statusNovo) { this.statusNovo = statusNovo; }

    public String getStatus() { return statusNovo; }
    public void setStatus(String status) { this.statusNovo = status; }

    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }

    public IntegranteTDB getIntegrante() { return integrante; }
    public void setIntegrante(IntegranteTDB integrante) { this.integrante = integrante; }
}

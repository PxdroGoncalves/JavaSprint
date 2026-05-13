package br.com.nuvemtech.entities;

import java.time.LocalDateTime;

public class HistoricoStatus {
    private int idHistorico;
    private String status;
    private LocalDateTime dataAlteracao;
    private Caso caso;
    private IntegranteTDB integrante;

    public HistoricoStatus() {}

    public HistoricoStatus(int idHistorico, String status, LocalDateTime dataAlteracao, Caso caso, IntegranteTDB integrante) {
        this.idHistorico = idHistorico;
        this.status = status;
        this.dataAlteracao = dataAlteracao;
        this.caso = caso;
        this.integrante = integrante;
    }

    public int getIdHistorico() { return idHistorico; }
    public void setIdHistorico(int idHistorico) { this.idHistorico = idHistorico; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(LocalDateTime dataAlteracao) { this.dataAlteracao = dataAlteracao; }
    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }
    public IntegranteTDB getIntegrante() { return integrante; }
    public void setIntegrante(IntegranteTDB integrante) { this.integrante = integrante; }
}

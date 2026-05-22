package br.com.nuvemtech.entities;

import java.time.LocalDate;

public class PedidoEncaminhamento {
    private int idPedido;
    private LocalDate dataPedido;
    private String status;
    private Caso caso;
    private Dentista dentista;
    private IntegranteTDB integrante;

    public PedidoEncaminhamento() {}

    public PedidoEncaminhamento(int idPedido, LocalDate dataPedido, String status,
                                Caso caso, Dentista dentista, IntegranteTDB integrante) {
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.status = status;
        this.caso = caso;
        this.dentista = dentista;
        this.integrante = integrante;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public LocalDate getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDate dataPedido) { this.dataPedido = dataPedido; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Caso getCaso() { return caso; }
    public void setCaso(Caso caso) { this.caso = caso; }

    public Dentista getDentista() { return dentista; }
    public void setDentista(Dentista dentista) { this.dentista = dentista; }

    public IntegranteTDB getIntegrante() { return integrante; }
    public void setIntegrante(IntegranteTDB integrante) { this.integrante = integrante; }

    public void enviar() {
        if (dataPedido == null) dataPedido = LocalDate.now();
        status = "PENDENTE";
    }
}

package br.com.nuvemtech.resources;

import java.time.LocalDateTime;

public class ApiError {
    private String mensagem;
    private int status;
    private LocalDateTime dataHora;

    public ApiError() {}

    public ApiError(String mensagem, int status) {
        this.mensagem = mensagem;
        this.status = status;
        this.dataHora = LocalDateTime.now();
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}

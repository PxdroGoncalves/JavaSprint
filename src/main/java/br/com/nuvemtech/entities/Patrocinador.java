package br.com.nuvemtech.entities;

public class Patrocinador extends Pessoa {
    private int idPatrocinador;
    private String anonimo;

    public Patrocinador() {}

    public Patrocinador(int idPatrocinador, String nome, String email, String anonimo) {
        super(nome, email);
        this.idPatrocinador = idPatrocinador;
        this.anonimo = anonimo;
    }

    public int getIdPatrocinador() { return idPatrocinador; }
    public void setIdPatrocinador(int idPatrocinador) { this.idPatrocinador = idPatrocinador; }
    public String getAnonimo() { return anonimo; }
    public void setAnonimo(String anonimo) { this.anonimo = anonimo; }
}

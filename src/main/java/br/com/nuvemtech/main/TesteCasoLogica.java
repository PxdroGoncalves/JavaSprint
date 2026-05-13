package br.com.nuvemtech.main;

import br.com.nuvemtech.entities.Beneficiario;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.entities.Dentista;
import br.com.nuvemtech.entities.IntegranteTDB;

import javax.swing.JOptionPane;

public class TesteCasoLogica {
    public static void main(String[] args) {
        Beneficiario beneficiario = new Beneficiario();
        beneficiario.setIdBeneficiario(1);
        beneficiario.setNome("Beneficiario Teste");

        IntegranteTDB integrante = new IntegranteTDB();
        integrante.setIdIntegrante(1);
        integrante.setNome("Integrante Teste");

        Dentista dentista = new Dentista();
        dentista.setIdDentista(1);
        dentista.setNome("Dentista Teste");

        Caso caso = new Caso();
        caso.setIdCaso(1);
        caso.setBeneficiario(beneficiario);
        caso.setIntegrante(integrante);

        caso.abrir();
        caso.enviarPedido(dentista);
        boolean fechouSemDiagnostico = caso.fechar();
        caso.registrarDiagnostico();
        boolean fechouComDiagnostico = caso.fechar();

        JOptionPane.showMessageDialog(null,
                "Status final: " + caso.getStatus() +
                "\nFechou sem diagnostico? " + fechouSemDiagnostico +
                "\nFechou com diagnostico? " + fechouComDiagnostico +
                "\nData abertura: " + caso.getDataAbertura() +
                "\nData fechamento: " + caso.getDataFechamento());
    }
}

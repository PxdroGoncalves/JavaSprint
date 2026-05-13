package br.com.nuvemtech.main;

import javax.swing.JOptionPane;

public class EntradaDados {
    public static String texto(String mensagem) {
        return JOptionPane.showInputDialog(mensagem);
    }

    public static int inteiro(String mensagem) {
        return Integer.parseInt(JOptionPane.showInputDialog(mensagem));
    }

    public static double real(String mensagem) {
        return Double.parseDouble(JOptionPane.showInputDialog(mensagem));
    }

    public static boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
}

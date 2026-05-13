package br.com.nuvemtech.main;

import br.com.nuvemtech.conexoes.ConexaoFactory;

import javax.swing.JOptionPane;
import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try (Connection conexao = new ConexaoFactory().conexao()) {
            JOptionPane.showMessageDialog(null, "Conectado com o Banco Dados");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na conexao: " + e.getMessage());
        }
    }
}

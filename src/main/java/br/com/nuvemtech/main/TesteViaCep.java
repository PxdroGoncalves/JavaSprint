package br.com.nuvemtech.main;

import br.com.nuvemtech.entities.EnderecoViaCep;
import br.com.nuvemtech.services.ViaCepService;

import javax.swing.JOptionPane;

public class TesteViaCep {
    public static void main(String[] args) {
        try {
            String cep = EntradaDados.texto("Digite o CEP:");
            EnderecoViaCep endereco = new ViaCepService().buscarEndereco(cep);
            JOptionPane.showMessageDialog(null, endereco.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar CEP: " + e.getMessage());
        }
    }
}

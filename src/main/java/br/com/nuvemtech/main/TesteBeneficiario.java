package br.com.nuvemtech.main;

import br.com.nuvemtech.entities.Beneficiario;
import br.com.nuvemtech.services.BeneficiarioService;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.List;

public class TesteBeneficiario {
    public static void main(String[] args) {
        BeneficiarioService service = new BeneficiarioService();
        try {
            int opcao = EntradaDados.inteiro("1-Inserir\n2-Atualizar\n3-Deletar\n4-Selecionar");

            if (opcao == 1) {
                Beneficiario b = lerBeneficiario();
                service.inserir(b);
                JOptionPane.showMessageDialog(null, "Beneficiario inserido.");
            } else if (opcao == 2) {
                Beneficiario b = lerBeneficiario();
                service.atualizar(b);
                JOptionPane.showMessageDialog(null, "Beneficiario atualizado.");
            } else if (opcao == 3) {
                int id = EntradaDados.inteiro("ID do beneficiario para deletar:");
                service.deletar(id);
                JOptionPane.showMessageDialog(null, "Beneficiario deletado.");
            } else if (opcao == 4) {
                List<Beneficiario> lista = service.selecionar();
                StringBuilder sb = new StringBuilder();
                for (Beneficiario b : lista) {
                    sb.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private static Beneficiario lerBeneficiario() {
        Beneficiario b = new Beneficiario();
        b.setIdBeneficiario(EntradaDados.inteiro("ID do beneficiario:"));
        b.setNome(EntradaDados.texto("Nome:"));
        b.setEmail(EntradaDados.texto("Email:"));
        b.setCpf(EntradaDados.texto("CPF:"));
        b.setDataNascimento(LocalDate.parse(EntradaDados.texto("Data de nascimento AAAA-MM-DD:")));
        b.setTelefone(EntradaDados.texto("Telefone:"));
        b.setEndereco(EntradaDados.texto("Endereco:"));
        String cadastro = EntradaDados.texto("Data de cadastro AAAA-MM-DD (deixe vazio para data atual):");
        if (!EntradaDados.vazio(cadastro)) b.setDataCadastro(LocalDate.parse(cadastro));
        b.setSenha(EntradaDados.texto("Senha:"));
        return b;
    }
}

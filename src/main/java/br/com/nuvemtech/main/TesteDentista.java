package br.com.nuvemtech.main;

import br.com.nuvemtech.entities.Dentista;
import br.com.nuvemtech.services.DentistaService;

import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.List;

public class TesteDentista {
    public static void main(String[] args) {
        DentistaService service = new DentistaService();
        try {
            int opcao = EntradaDados.inteiro("1-Inserir\n2-Atualizar\n3-Deletar\n4-Selecionar");

            if (opcao == 1) {
                Dentista d = lerDentista();
                service.inserir(d);
                JOptionPane.showMessageDialog(null, "Dentista inserido.");
            } else if (opcao == 2) {
                Dentista d = lerDentista();
                service.atualizar(d);
                JOptionPane.showMessageDialog(null, "Dentista atualizado.");
            } else if (opcao == 3) {
                int id = EntradaDados.inteiro("ID do dentista para deletar:");
                service.deletar(id);
                JOptionPane.showMessageDialog(null, "Dentista deletado.");
            } else if (opcao == 4) {
                List<Dentista> lista = service.selecionar();
                StringBuilder sb = new StringBuilder();
                for (Dentista d : lista) sb.append(d).append("\n");
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }

    private static Dentista lerDentista() {
        Dentista d = new Dentista();
        d.setIdDentista(EntradaDados.inteiro("ID do dentista:"));
        d.setNome(EntradaDados.texto("Nome:"));
        d.setEmail(EntradaDados.texto("Email:"));
        d.setCro(EntradaDados.texto("CRO:"));
        d.setEspecialidade(EntradaDados.texto("Especialidade:"));
        d.setTelefone(EntradaDados.texto("Telefone:"));
        String cadastro = EntradaDados.texto("Data de cadastro AAAA-MM-DD (deixe vazio para data atual):");
        if (!EntradaDados.vazio(cadastro)) d.setDataCadastro(LocalDate.parse(cadastro));
        d.setSenha(EntradaDados.texto("Senha:"));
        return d;
    }
}

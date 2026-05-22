package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.MensagemDAO;
import br.com.nuvemtech.entities.Mensagem;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class MensagemBO {

    public List<Mensagem> selecionarBo() throws SQLException, ClassNotFoundException {
        return new MensagemDAO().selecionar();
    }

    public Mensagem buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Mensagem obj = new MensagemDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(Mensagem obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDateTime.now());
        new MensagemDAO().inserir(obj);
    }

    public void atualizarBo(Mensagem obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataEnvio() == null) obj.setDataEnvio(java.time.LocalDateTime.now());
        new MensagemDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new MensagemDAO().deletar(id);
    }

    private void validarDados(Mensagem obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getTexto() == null || obj.getTexto().isBlank())
            throw new RegraNegocioException("Texto da mensagem obrigatorio.");

        String rem = obj.getRemetente();
        if (rem == null || rem.isBlank())
            throw new RegraNegocioException("Remetente obrigatorio.");

        if (!rem.equals("BENEFICIARIO") && !rem.equals("DENTISTA") && !rem.equals("INTEGRANTE_TDB"))
            throw new RegraNegocioException("Remetente invalido. Use: BENEFICIARIO, DENTISTA ou INTEGRANTE_TDB.");

        if ("BENEFICIARIO".equals(rem) &&
                (obj.getBeneficiario() == null || obj.getBeneficiario().getIdBeneficiario() <= 0))
            throw new RegraNegocioException("Remetente BENEFICIARIO exige id do beneficiario.");

        if ("DENTISTA".equals(rem) &&
                (obj.getDentista() == null || obj.getDentista().getIdDentista() <= 0))
            throw new RegraNegocioException("Remetente DENTISTA exige id do dentista.");

        if ("INTEGRANTE_TDB".equals(rem) &&
                (obj.getIntegrante() == null || obj.getIntegrante().getIdIntegrante() <= 0))
            throw new RegraNegocioException("Remetente INTEGRANTE_TDB exige id do integrante.");
    }

    private void validar(Mensagem obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdMensagem() <= 0) throw new RegraNegocioException("ID invalido.");
        validarDados(obj);
    }
}

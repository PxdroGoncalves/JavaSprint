package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.HistoricoStatusDAO;
import br.com.nuvemtech.entities.HistoricoStatus;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HistoricoStatusBO {

    
    private static final java.util.Set<String> STATUS_VALIDOS = java.util.Set.of(
            "PENDENTE", "EM_ANDAMENTO", "CONCLUIDO", "CANCELADO"
    );

    public List<HistoricoStatus> selecionarBo() throws SQLException, ClassNotFoundException {
        return new HistoricoStatusDAO().selecionar();
    }

    public HistoricoStatus buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        HistoricoStatus obj = new HistoricoStatusDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }

    public void inserirBo(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        validarDados(obj);
        if (obj.getDataAlteracao() == null) obj.setDataAlteracao(LocalDate.now());
        new HistoricoStatusDAO().inserir(obj);
    }

    public void atualizarBo(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataAlteracao() == null) obj.setDataAlteracao(LocalDate.now());
        new HistoricoStatusDAO().atualizar(obj);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new HistoricoStatusDAO().deletar(id);
    }

    private void validarDados(HistoricoStatus obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getStatusNovo() == null || obj.getStatusNovo().isBlank())
            throw new RegraNegocioException("Status novo obrigatorio.");
        if (!STATUS_VALIDOS.contains(obj.getStatusNovo()))
            throw new RegraNegocioException("Status invalido. Use: PENDENTE, EM_ANDAMENTO, CONCLUIDO ou CANCELADO.");
        if (obj.getStatusAnterior() != null && !obj.getStatusAnterior().isBlank()
                && !STATUS_VALIDOS.contains(obj.getStatusAnterior()))
            throw new RegraNegocioException("Status anterior invalido.");
        if (obj.getCaso() == null || obj.getCaso().getIdCaso() <= 0)
            throw new RegraNegocioException("Caso obrigatorio.");
        
    }

    private void validar(HistoricoStatus obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdHistorico() <= 0) throw new RegraNegocioException("ID invalido.");
        validarDados(obj);
    }
}

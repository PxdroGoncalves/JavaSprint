package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.HistoricoStatusDAO;
import br.com.nuvemtech.entities.HistoricoStatus;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.util.List;

public class HistoricoStatusBO {
    public List<HistoricoStatus> selecionarBo() throws SQLException, ClassNotFoundException { return new HistoricoStatusDAO().selecionar(); }
    public HistoricoStatus buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        HistoricoStatus obj = new HistoricoStatusDAO().buscarPorId(id);
        if (obj == null) throw new NotFoundException("Registro nao encontrado.");
        return obj;
    }
    public void inserirBo(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataAlteracao() == null) obj.setDataAlteracao(java.time.LocalDateTime.now());
        new HistoricoStatusDAO().inserir(obj);
    }
    public void atualizarBo(HistoricoStatus obj) throws SQLException, ClassNotFoundException {
        validar(obj);
        if (obj.getDataAlteracao() == null) obj.setDataAlteracao(java.time.LocalDateTime.now());
        new HistoricoStatusDAO().atualizar(obj);
    }
    public void deletarBo(int id) throws SQLException, ClassNotFoundException { new HistoricoStatusDAO().deletar(id); }
    private void validar(HistoricoStatus obj) {
        if (obj == null) throw new RegraNegocioException("Dados obrigatorios nao informados.");
        if (obj.getIdHistorico() <= 0) throw new RegraNegocioException("ID invalido.");
    }
}

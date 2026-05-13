package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.CasoDAO;
import br.com.nuvemtech.dao.DiagnosticoDAO;
import br.com.nuvemtech.entities.Diagnostico;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DiagnosticoBO {
    public List<Diagnostico> selecionarBo() throws SQLException, ClassNotFoundException { return new DiagnosticoDAO().selecionar(); }
    public Diagnostico buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Diagnostico d = new DiagnosticoDAO().buscarPorId(id);
        if (d == null) throw new NotFoundException("Diagnostico nao encontrado.");
        return d;
    }
    public void inserirBo(Diagnostico d) throws SQLException, ClassNotFoundException {
        validar(d);
        if (d.getDataDiagnostico() == null) d.setDataDiagnostico(LocalDate.now());
        new DiagnosticoDAO().inserir(d);
        var casoDAO = new CasoDAO();
        var caso = casoDAO.buscarPorId(d.getCaso().getIdCaso());
        if (caso != null) {
            caso.registrarDiagnostico();
            casoDAO.atualizarStatus(caso);
        }
    }
    public void atualizarBo(Diagnostico d) throws SQLException, ClassNotFoundException {
        validar(d);
        if (d.getDataDiagnostico() == null) d.setDataDiagnostico(LocalDate.now());
        new DiagnosticoDAO().atualizar(d);
    }
    public void deletarBo(int id) throws SQLException, ClassNotFoundException { new DiagnosticoDAO().deletar(id); }
    private void validar(Diagnostico d) {
        if (d == null) throw new RegraNegocioException("Dados do diagnostico nao informados.");
        if (d.getIdDiagnostico() <= 0) throw new RegraNegocioException("ID do diagnostico invalido.");
        if (d.getDescricao() == null || d.getDescricao().isBlank()) throw new RegraNegocioException("Descricao obrigatoria.");
        if (d.getCaso() == null || d.getCaso().getIdCaso() <= 0) throw new RegraNegocioException("Caso obrigatorio.");
        if (d.getDentista() == null || d.getDentista().getIdDentista() <= 0) throw new RegraNegocioException("Dentista obrigatorio.");
    }
}

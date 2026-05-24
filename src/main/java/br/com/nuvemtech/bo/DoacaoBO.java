package br.com.nuvemtech.bo;

import br.com.nuvemtech.dao.DoacaoDAO;
import br.com.nuvemtech.entities.Doacao;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DoacaoBO {

    public List<Doacao> selecionarBo() throws SQLException, ClassNotFoundException {
        return new DoacaoDAO().selecionar();
    }

    public Doacao buscarPorIdBo(int id) throws SQLException, ClassNotFoundException {
        Doacao d = new DoacaoDAO().buscarPorId(id);
        if (d == null) throw new NotFoundException("Doacao nao encontrada.");
        return d;
    }

    public void inserirBo(Doacao d) throws SQLException, ClassNotFoundException {
        validarDados(d);
        if (d.getDataDoacao() == null) d.setDataDoacao(LocalDate.now());
        new DoacaoDAO().inserir(d);
    }

    public void atualizarBo(Doacao d) throws SQLException, ClassNotFoundException {
        validar(d);
        if (d.getDataDoacao() == null) d.setDataDoacao(LocalDate.now());
        new DoacaoDAO().atualizar(d);
    }

    public void deletarBo(int id) throws SQLException, ClassNotFoundException {
        new DoacaoDAO().deletar(id);
    }

    public List<Doacao> buscarPorPatrocinadorBo(int idPatrocinador) throws SQLException, ClassNotFoundException {
        return new DoacaoDAO().buscarPorPatrocinador(idPatrocinador);
    }

    private void validarDados(Doacao d) {
        if (d == null) throw new RegraNegocioException("Dados da doacao nao informados.");
        if (d.getTipo() == null || d.getTipo().isBlank()) throw new RegraNegocioException("Tipo da doacao obrigatorio.");
        d.setTipo(d.getTipo().trim().toUpperCase());
        if (d.getTipo().equalsIgnoreCase("MONETARIO") && (d.getValor() == null || d.getValor() <= 0))
            throw new RegraNegocioException("Doacao monetaria precisa ter valor maior que zero.");
        if (d.getTipo().equalsIgnoreCase("EQUIPAMENTO") && (d.getDescricaoEquipamento() == null || d.getDescricaoEquipamento().isBlank()))
            throw new RegraNegocioException("Doacao de equipamento precisa ter descricao.");
        if (d.getPatrocinador() == null || d.getPatrocinador().getIdPatrocinador() <= 0)
            throw new RegraNegocioException("Patrocinador obrigatorio.");
    }

    private void validar(Doacao d) {
        if (d == null) throw new RegraNegocioException("Dados da doacao nao informados.");
        if (d.getIdDoacao() <= 0) throw new RegraNegocioException("ID da doacao invalido.");
        validarDados(d);
    }
}
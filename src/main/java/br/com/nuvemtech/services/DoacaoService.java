package br.com.nuvemtech.services;

import br.com.nuvemtech.bo.DoacaoBO;
import br.com.nuvemtech.entities.Doacao;
import java.sql.SQLException;
import java.util.List;

public class DoacaoService {
    private final DoacaoBO bo = new DoacaoBO();

    public List<Doacao> selecionar() throws SQLException, ClassNotFoundException {
        return bo.selecionarBo();
    }

    public Doacao buscarPorId(int id) throws SQLException, ClassNotFoundException {
        return bo.buscarPorIdBo(id);
    }

    public void inserir(Doacao obj) throws SQLException, ClassNotFoundException {
        bo.inserirBo(obj);
    }

    public void atualizar(Doacao obj) throws SQLException, ClassNotFoundException {
        bo.atualizarBo(obj);
    }

    public void deletar(int id) throws SQLException, ClassNotFoundException {
        bo.deletarBo(id);
    }

    public List<Doacao> buscarPorPatrocinador(int idPatrocinador) throws SQLException, ClassNotFoundException {
        return bo.buscarPorPatrocinadorBo(idPatrocinador);
    }
}

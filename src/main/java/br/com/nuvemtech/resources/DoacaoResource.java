package br.com.nuvemtech.resources;

import br.com.nuvemtech.services.DoacaoService;
import br.com.nuvemtech.entities.Doacao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/doacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoacaoResource {
    private final DoacaoService service = new DoacaoService();

    @GET
    public List<Doacao> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    /**
     * Busca uma doação pelo ID — funciona como link rastreável.
     * Retorna: tipo, valor, descrição, data e dados do patrocinador.
     * Exemplo: GET /doacoes/1
     */
    @GET
    @Path("/{id}")
    public Doacao buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    /**
     * Lista todas as doações de um patrocinador específico.
     * Permite ao patrocinador ver seu histórico completo de doações.
     * Exemplo: GET /doacoes/patrocinador/1
     */
    @GET
    @Path("/patrocinador/{idPatrocinador}")
    public Response buscarPorPatrocinador(@PathParam("idPatrocinador") int idPatrocinador)
            throws SQLException, ClassNotFoundException {
        List<Doacao> lista = service.buscarPorPatrocinador(idPatrocinador);
        if (lista.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensagem\": \"Nenhuma doacao encontrada para este patrocinador.\"}")
                    .build();
        }
        return Response.ok(lista).build();
    }

    @POST
    public Response inserir(Doacao obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        Object salvo = service.buscarPorId(obj.getIdDoacao());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdDoacao()));
        return Response.created(builder.build()).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Doacao obj) throws SQLException, ClassNotFoundException {
        obj.setIdDoacao(id);
        service.atualizar(obj);
        return Response.ok(service.buscarPorId(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        service.deletar(id);
        return Response.noContent().build();
    }
}


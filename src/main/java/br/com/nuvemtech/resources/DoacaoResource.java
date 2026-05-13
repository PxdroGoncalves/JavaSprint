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

    @GET
    @Path("/{id}")
    public Doacao buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
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

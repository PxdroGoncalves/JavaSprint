package br.com.nuvemtech.resources;

import br.com.nuvemtech.services.MensagemService;
import br.com.nuvemtech.entities.Mensagem;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagemResource {
    private final MensagemService service = new MensagemService();

    @GET
    public List<Mensagem> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Mensagem buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Mensagem obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        Object salvo = service.buscarPorId(obj.getIdMensagem());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdMensagem()));
        return Response.created(builder.build()).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Mensagem obj) throws SQLException, ClassNotFoundException {
        obj.setIdMensagem(id);
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

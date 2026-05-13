package br.com.nuvemtech.resources;

import br.com.nuvemtech.services.CasoService;
import br.com.nuvemtech.entities.Caso;
import br.com.nuvemtech.entities.Dentista;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/casos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CasoResource {
    private final CasoService service = new CasoService();

    @GET
    public List<Caso> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Caso buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Caso obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        Object salvo = service.buscarPorId(obj.getIdCaso());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdCaso()));
        return Response.created(builder.build()).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Caso obj) throws SQLException, ClassNotFoundException {
        obj.setIdCaso(id);
        service.atualizar(obj);
        return Response.ok(service.buscarPorId(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        service.deletar(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{idCaso}/enviar-pedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enviarPedido(@PathParam("idCaso") int idCaso, Dentista dentista) throws SQLException, ClassNotFoundException {
        service.enviarPedido(idCaso, dentista);
        return Response.ok().build();
    }

    @PUT
    @Path("/{idCaso}/fechar")
    public Response fechar(@PathParam("idCaso") int idCaso) throws SQLException, ClassNotFoundException {
        service.fechar(idCaso);
        return Response.ok().build();
    }
}

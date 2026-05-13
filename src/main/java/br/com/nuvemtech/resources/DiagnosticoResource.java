package br.com.nuvemtech.resources;

import br.com.nuvemtech.services.DiagnosticoService;
import br.com.nuvemtech.entities.Diagnostico;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/diagnosticos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiagnosticoResource {
    private final DiagnosticoService service = new DiagnosticoService();

    @GET
    public List<Diagnostico> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Diagnostico buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Diagnostico obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        Object salvo = service.buscarPorId(obj.getIdDiagnostico());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdDiagnostico()));
        return Response.created(builder.build()).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Diagnostico obj) throws SQLException, ClassNotFoundException {
        obj.setIdDiagnostico(id);
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

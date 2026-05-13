package br.com.nuvemtech.resources;

import br.com.nuvemtech.services.BeneficiarioService;
import br.com.nuvemtech.entities.Beneficiario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/beneficiarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BeneficiarioResource {
    private final BeneficiarioService service = new BeneficiarioService();

    @GET
    public List<Beneficiario> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Beneficiario buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Beneficiario obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdBeneficiario()));
        return Response.created(builder.build()).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Beneficiario obj) throws SQLException, ClassNotFoundException {
        obj.setIdBeneficiario(id);
        service.atualizar(obj);
        return Response.ok(obj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        service.deletar(id);
        return Response.noContent().build();
    }
}

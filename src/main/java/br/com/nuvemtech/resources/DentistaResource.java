package br.com.nuvemtech.resources;

import br.com.nuvemtech.dto.CadastroDentistaRequest;
import br.com.nuvemtech.dto.LoginRequest;
import br.com.nuvemtech.services.DentistaService;
import br.com.nuvemtech.entities.Dentista;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/dentistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DentistaResource {

    private final DentistaService service = new DentistaService();

    @GET
    public List<Dentista> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Dentista buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Dentista obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdDentista()));
        return Response.created(builder.build()).entity(obj).build();
    }

    @POST
    @Path("/cadastrar")
    public Response cadastrar(CadastroDentistaRequest req) throws SQLException, ClassNotFoundException {
        Dentista obj = new Dentista();
        obj.setNome(req.getNome());
        obj.setEmail(req.getEmail());
        obj.setSenha(req.getSenha());
        obj.setCro(req.getCro());
        obj.setEspecialidade(req.getEspecialidade());
        obj.setTelefone(req.getTelefone());
        service.cadastrar(obj);
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Dentista obj) throws SQLException, ClassNotFoundException {
        obj.setIdDentista(id);
        service.atualizar(obj);
        return Response.ok(obj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        service.deletar(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest request) throws SQLException, ClassNotFoundException {
        Dentista obj = service.login(request.getEmail(), request.getSenha());
        return Response.ok(obj).build();
    }
}

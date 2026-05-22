package br.com.nuvemtech.resources;

import br.com.nuvemtech.dto.CadastroPatrocinadorRequest;
import br.com.nuvemtech.dto.LoginRequest;
import br.com.nuvemtech.services.PatrocinadorService;
import br.com.nuvemtech.entities.Patrocinador;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/patrocinadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatrocinadorResource {

    private final PatrocinadorService service = new PatrocinadorService();

    @GET
    public List<Patrocinador> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public Patrocinador buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(Patrocinador obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdPatrocinador()));
        return Response.created(builder.build()).entity(obj).build();
    }

    @POST
    @Path("/cadastrar")
    public Response cadastrar(CadastroPatrocinadorRequest request) {
        try {
            Patrocinador patrocinador = new Patrocinador();
            patrocinador.setNome(request.getNome());
            patrocinador.setEmail(request.getEmail());
            patrocinador.setSenha(request.getSenha());
            patrocinador.setCpfCnpj(request.getCpfCnpj());
            patrocinador.setTelefone(request.getTelefone());
            service.cadastrar(patrocinador);
            return Response.status(Response.Status.CREATED).entity(patrocinador).build();

        } catch (RuntimeException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro interno do servidor").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Patrocinador obj) throws SQLException, ClassNotFoundException {
        obj.setIdPatrocinador(id);
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
        Patrocinador obj = service.login(request.getEmail(), request.getSenha());
        return Response.ok(obj).build();
    }
}

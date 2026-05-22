package br.com.nuvemtech.resources;

import br.com.nuvemtech.dto.CadastroBeneficiarioRequest;
import br.com.nuvemtech.dto.LoginRequest;
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

    // POST /beneficiarios — cadastra com auto-ID (usado pelo prontuário)
    @POST
    public Response inserir(Beneficiario obj, @Context UriInfo uriInfo) throws SQLException, ClassNotFoundException {
        service.inserir(obj);
        Beneficiario salvo = service.buscarPorId(obj.getIdBeneficiario());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdBeneficiario()));
        return Response.created(builder.build()).entity(salvo != null ? salvo : obj).build();
    }

    // POST /beneficiarios/cadastrar — endpoint original de auto-cadastro
    @POST
    @Path("/cadastrar")
    public Response cadastrar(CadastroBeneficiarioRequest req) throws SQLException, ClassNotFoundException {
        Beneficiario obj = new Beneficiario();
        obj.setNome(req.getNome());
        obj.setEmail(req.getEmail());
        obj.setSenha(req.getSenha());
        obj.setCpf(req.getCpf());
        obj.setDataNascimento(req.getDataNascimento());
        obj.setTelefone(req.getTelefone());
        obj.setEndereco(req.getEndereco());
        service.cadastrar(obj);
        return Response.status(Response.Status.CREATED).entity(obj).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Beneficiario obj) throws SQLException, ClassNotFoundException {
        obj.setIdBeneficiario(id);
        service.atualizar(obj);
        return Response.ok(service.buscarPorId(id)).build();
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
        Beneficiario obj = service.login(request.getEmail(), request.getSenha());
        if (obj == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(new ApiError("Email ou senha inválidos", 401)).build();
        }
        return Response.ok(obj).build();
    }
}

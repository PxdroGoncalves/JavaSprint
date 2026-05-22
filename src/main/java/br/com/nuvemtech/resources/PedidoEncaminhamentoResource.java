package br.com.nuvemtech.resources;

import br.com.nuvemtech.entities.IntegranteTDB;
import br.com.nuvemtech.services.PedidoEncaminhamentoService;
import br.com.nuvemtech.entities.PedidoEncaminhamento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/pedidos-encaminhamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoEncaminhamentoResource {
    private final PedidoEncaminhamentoService service = new PedidoEncaminhamentoService();

    @GET
    public List<PedidoEncaminhamento> selecionar() throws SQLException, ClassNotFoundException {
        return service.selecionar();
    }

    @GET
    @Path("/{id}")
    public PedidoEncaminhamento buscarPorId(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        return service.buscarPorId(id);
    }

    @POST
    public Response inserir(PedidoEncaminhamento obj,
                            @Context UriInfo uriInfo,
                            @HeaderParam("Authorization") String authHeader) throws SQLException, ClassNotFoundException {

        if (obj.getIntegrante() == null || obj.getIntegrante().getIdIntegrante() <= 0) {
            int idIntegrante = extrairIdIntegrante(authHeader);
            IntegranteTDB integrante = new IntegranteTDB();
            integrante.setIdIntegrante(idIntegrante);
            obj.setIntegrante(integrante);
        }

        service.inserir(obj);
        Object salvo = service.buscarPorId(obj.getIdPedido());
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(obj.getIdPedido()));
        return Response.created(builder.build()).entity(salvo).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, PedidoEncaminhamento obj) throws SQLException, ClassNotFoundException {
        obj.setIdPedido(id);
        service.atualizar(obj);
        return Response.ok(service.buscarPorId(id)).build();
    }

    @PUT
    @Path("/{id}/aceitar")
    public Response aceitarPedido(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        PedidoEncaminhamento pedido = service.aceitarPedido(id);
        return Response.ok(pedido).build();
    }

    @PUT
    @Path("/{id}/recusar")
    public Response recusarPedido(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        PedidoEncaminhamento pedido = service.recusarPedido(id);
        return Response.ok(pedido).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        service.deletar(id);
        return Response.noContent().build();
    }

    private int extrairIdIntegrante(String authHeader) {
        if (authHeader != null && authHeader.startsWith("integrante-")) {
            try {
                return Integer.parseInt(authHeader.substring("integrante-".length()).trim());
            } catch (NumberFormatException ignored) {}
        }
        return 1; // fallback: integrante padrão do sistema
    }
}
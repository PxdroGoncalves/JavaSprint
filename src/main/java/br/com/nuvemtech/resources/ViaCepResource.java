package br.com.nuvemtech.resources;

import br.com.nuvemtech.entities.EnderecoViaCep;
import br.com.nuvemtech.services.ViaCepService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.util.Map;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ViaCepResource {
    private final ViaCepService service = new ViaCepService();

    @GET
    @Path("/cep/{cep}")
    public EnderecoViaCep buscarPorCep(@PathParam("cep") String cep) throws IOException, InterruptedException {
        return service.buscarEndereco(cep);
    }

    @GET
    @Path("/cep/{cep}/formatado")
    public Map<String, String> buscarPorCepFormatado(@PathParam("cep") String cep) throws IOException, InterruptedException {
        return Map.of("endereco", service.buscarEnderecoFormatado(cep));
    }
}

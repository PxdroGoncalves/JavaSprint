package br.com.nuvemtech.resources;

import br.com.nuvemtech.entities.EnderecoViaCep;
import br.com.nuvemtech.exceptions.RegraNegocioException;
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
    public EnderecoViaCep buscarPorCep(@PathParam("cep") String cep) {
        try {
            return service.buscarEndereco(cep);
        } catch (IOException e) {
            throw new RegraNegocioException("Erro ao consultar o ViaCEP: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RegraNegocioException("Consulta ao ViaCEP foi interrompida.");
        }
    }

    @GET
    @Path("/cep/{cep}/formatado")
    public Map<String, String> buscarPorCepFormatado(@PathParam("cep") String cep) {
        try {
            return Map.of("endereco", service.buscarEnderecoFormatado(cep));
        } catch (IOException e) {
            throw new RegraNegocioException("Erro ao consultar o ViaCEP: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RegraNegocioException("Consulta ao ViaCEP foi interrompida.");
        }
    }
}

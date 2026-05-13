package br.com.nuvemtech.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResource {

    @GET
    public Map<String, Object> home() {
        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("mensagem", "API NuvemTech Sprint 4 rodando com sucesso");
        resposta.put("swagger", "/q/swagger-ui");
        resposta.put("endpoints", List.of(
                "/beneficiarios",
                "/dentistas",
                "/integrantes",
                "/casos",
                "/diagnosticos",
                "/historicos-status",
                "/pedidos-encaminhamento",
                "/pedidos-encaminhamento/{id}/aceitar",
                "/pedidos-encaminhamento/{id}/recusar",
                "/patrocinadores",
                "/doacoes",
                "/evidencias",
                "/mensagens",
                "/enderecos/cep/{cep}",
                "/enderecos/cep/{cep}/formatado"
        ));
        return resposta;
    }
}

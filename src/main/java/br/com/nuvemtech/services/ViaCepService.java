package br.com.nuvemtech.services;

import br.com.nuvemtech.entities.EnderecoViaCep;
import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ViaCepService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public EnderecoViaCep buscarEndereco(String cep) throws IOException, InterruptedException {
        String cepLimpo = limparCep(cep);
        validarCep(cepLimpo);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://viacep.com.br/ws/" + cepLimpo + "/json/"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RegraNegocioException("Nao foi possivel consultar o ViaCEP.");
        }

        JsonNode json = mapper.readTree(response.body());
        if (json.has("erro") && json.get("erro").asBoolean()) {
            throw new NotFoundException("CEP nao encontrado.");
        }

        EnderecoViaCep endereco = new EnderecoViaCep();
        endereco.setCep(pegarTexto(json, "cep"));
        endereco.setLogradouro(pegarTexto(json, "logradouro"));
        endereco.setComplemento(pegarTexto(json, "complemento"));
        endereco.setBairro(pegarTexto(json, "bairro"));
        endereco.setLocalidade(pegarTexto(json, "localidade"));
        endereco.setUf(pegarTexto(json, "uf"));

        return endereco;
    }

    public String buscarEnderecoFormatado(String cep) throws IOException, InterruptedException {
        return buscarEndereco(cep).enderecoFormatado();
    }

    private String pegarTexto(JsonNode json, String campo) {
        if (!json.has(campo) || json.get(campo).isNull()) return "";
        return json.get(campo).asText();
    }

    private String limparCep(String cep) {
        if (cep == null) return "";
        return cep.replace("-", "").replace(".", "").trim();
    }

    private void validarCep(String cep) {
        if (!cep.matches("\\d{8}")) {
            throw new RegraNegocioException("CEP invalido. Informe 8 numeros.");
        }
    }
}

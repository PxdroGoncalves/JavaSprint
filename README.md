# NuvemTech - Sprint 4 Java

Projeto final de Domain Driven Design Using Java desenvolvido com Quarkus, JDBC, Oracle Database e API RESTful.

## Objetivo

A solução gerencia o fluxo de atendimento social da NuvemTech/Turma do Bem, permitindo cadastrar beneficiários, dentistas, integrantes, casos, diagnósticos, pedidos de encaminhamento, patrocinadores, doações, evidências, mensagens e histórico de status.

## O que foi ajustado

- O projeto foi montado usando a base Quarkus do professor como padrão de estrutura.
- O código de Aluno foi removido da solução final, pois era apenas base de exemplo.
- Os dados fixos foram retirados do código.
- Datas, telefone, senha, integrante e dentista agora vêm pelo JSON ou são definidos por regra de negócio quando fizer sentido.
- A API REST foi criada para as classes principais do projeto.
- Foram criadas camadas entities, dao, bo, resources, exceptions e conexoes.
- Foram adicionadas regras de negócio para abrir caso, enviar pedido, registrar diagnóstico e fechar caso.

## Como rodar

Configure seu usuário e senha Oracle em:

```text
src/main/java/br/com/nuvemtech/conexoes/ConexaoFactory.java
```

Depois execute:

```bash
mvnw.cmd quarkus:dev
```

No navegador, para ver se a API subiu:

```text
http://localhost:8080/
```

Para testar os endpoints pelo Swagger:

```text
http://localhost:8080/q/swagger-ui
```

## Endpoints principais

| Método | Endpoint | Função |
|---|---|---|
| GET | / | Tela inicial da API com lista de endpoints |
| GET | /beneficiarios | Lista beneficiários |
| GET | /beneficiarios/{id} | Busca beneficiário |
| POST | /beneficiarios | Cadastra beneficiário |
| PUT | /beneficiarios/{id} | Atualiza beneficiário |
| DELETE | /beneficiarios/{id} | Remove beneficiário |
| GET | /dentistas | Lista dentistas |
| POST | /dentistas | Cadastra dentista |
| GET | /integrantes | Lista integrantes |
| POST | /integrantes | Cadastra integrante |
| GET | /casos | Lista casos |
| POST | /casos | Abre caso |
| PUT | /casos/{idCaso}/enviar-pedido | Envia caso para dentista |
| PUT | /casos/{idCaso}/fechar | Fecha caso se existir diagnóstico |
| GET | /diagnosticos | Lista diagnósticos |
| POST | /diagnosticos | Registra diagnóstico |
| GET | /historicos-status | Lista histórico |
| POST | /historicos-status | Cadastra histórico |
| GET | /pedidos-encaminhamento | Lista pedidos |
| POST | /pedidos-encaminhamento | Cadastra pedido |
| GET | /patrocinadores | Lista patrocinadores |
| POST | /patrocinadores | Cadastra patrocinador |
| GET | /doacoes | Lista doações |
| POST | /doacoes | Cadastra doação |
| GET | /evidencias | Lista evidências |
| POST | /evidencias | Cadastra evidência |
| GET | /mensagens | Lista mensagens |
| POST | /mensagens | Cadastra mensagem |

## Exemplo de Beneficiário

```json
{
  "idBeneficiario": 1,
  "nome": "Pedro Rodrigues",
  "email": "pedro@email.com",
  "cpf": "12345678901",
  "dataNascimento": "2004-05-10",
  "telefone": "11999999999",
  "endereco": "Rua Exemplo, 100",
  "senha": "123456"
}
```

## Exemplo de Caso

```json
{
  "idCaso": 1,
  "beneficiario": { "idBeneficiario": 1 },
  "integrante": { "idIntegrante": 1 }
}
```

## Exemplo de Dentista

```json
{
  "idDentista": 1,
  "nome": "Dra. Ana Souza",
  "email": "ana@email.com",
  "cro": "CROSP12345",
  "especialidade": "Ortodontia",
  "telefone": "11988887777",
  "senha": "123456"
}
```

## Banco de dados

O script SQL está em:

```text
src/main/resources/db/schema.sql
```

## Camada Service

A camada `services` foi adicionada para deixar o projeto mais parecido com a base do professor e separar melhor as responsabilidades.

Fluxo usado no projeto:

```text
Resource -> Service -> BO -> DAO -> Banco de Dados
```

- `Resource`: recebe as chamadas REST.
- `Service`: centraliza as chamadas usadas pela API.
- `BO`: valida e aplica as regras de negócio.
- `DAO`: executa os comandos SQL no Oracle.
- `Entity`: representa os dados do sistema.


## Retorno com dados relacionados

Os endpoints que possuem relacionamento agora retornam os objetos completos, não apenas o ID.

Exemplos:

- `/casos` retorna beneficiário, dentista e integrante preenchidos.
- `/pedidos-encaminhamento` retorna caso e dentista preenchidos.
- `/doacoes` retorna patrocinador preenchido.
- `/evidencias` retorna beneficiário e caso preenchidos.
- `/diagnosticos`, `/historicos-status` e `/mensagens` também retornam os relacionamentos principais.

Isso foi ajustado na camada DAO usando buscas auxiliares de relacionamento, evitando respostas com vários campos `null` quando o registro relacionado existe no banco.

Também foi ajustado para que os endpoints de cadastro e atualização dessas classes retornem o registro recarregado do banco, já com os relacionamentos preenchidos. Assim, no `POST` e no `PUT`, o retorno não fica apenas com o ID do objeto relacionado.

## API ViaCEP

A API ViaCEP foi incluída para consultar endereço pelo CEP e ajudar no preenchimento do endereço do beneficiário.

| Método | Endpoint | Função |
|---|---|---|
| GET | / | Tela inicial da API com lista de endpoints |
| GET | /enderecos/cep/{cep} | Consulta endereço no ViaCEP |
| GET | /enderecos/cep/{cep}/formatado | Retorna endereço formatado |
| PUT | /pedidos-encaminhamento/{id}/aceitar | Aceita pedido de encaminhamento |
| PUT | /pedidos-encaminhamento/{id}/recusar | Recusa pedido de encaminhamento |

Exemplo:

```text
GET http://localhost:8080/enderecos/cep/01001000
```

## Classes de teste

Também foram adicionadas classes simples de teste com `JOptionPane`, seguindo o estilo usado nas sprints anteriores.

As classes ficam em:

```text
src/main/java/br/com/nuvemtech/main
```

Classes criadas:

- `TesteConexao`
- `TesteBeneficiario`
- `TesteDentista`
- `TesteCasoLogica`
- `TesteViaCep`

Essas classes servem para demonstrar testes manuais de conexão, cadastro, listagem, atualização, exclusão, regras de negócio e consulta de CEP.


## Ajuste ViaCEP

A consulta de endereço utiliza a API pública ViaCEP e retorna apenas os campos usados no projeto:

- cep
- logradouro
- complemento
- bairro
- localidade
- uf

Exemplo:

```http
GET http://localhost:8080/enderecos/cep/01001000
```

Retorno esperado:

```json
{
  "cep": "01001-000",
  "logradouro": "Praça da Sé",
  "complemento": "lado ímpar",
  "bairro": "Sé",
  "localidade": "São Paulo",
  "uf": "SP"
}
```

Também existe um endpoint para retornar uma versão resumida:

```http
GET http://localhost:8080/enderecos/cep/01001000/formatado
```

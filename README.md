# NuvemTech - Sprint 4 Java

Projeto de Domain Driven Design Using Java desenvolvido com Quarkus, JDBC, Oracle Database e API RESTful.

## Objetivo

A solução gerencia o fluxo de atendimento social da NuvemTech/Turma do Bem, permitindo cadastrar beneficiários, dentistas, integrantes, casos, diagnósticos, pedidos de encaminhamento, patrocinadores, doações, evidências, mensagens e histórico de status.

## Tecnologias

- Java 17+
- Quarkus
- JDBC
- Oracle Database
- API RESTful

## Arquitetura

Fluxo usado no projeto:

```
Resource -> Service -> BO -> DAO -> Banco de Dados
```

- `Resource`: recebe as chamadas REST
- `Service`: centraliza as chamadas usadas pela API
- `BO`: valida e aplica as regras de negócio
- `DAO`: executa os comandos SQL no Oracle
- `Entity`: representa os dados do sistema

## Como rodar

Configure seu usuário e senha Oracle em:

```
src/main/java/br/com/nuvemtech/conexoes/ConexaoFactory.java
```

Depois execute:

```bash
mvnw.cmd quarkus:dev
```

Para ver se a API subiu:

```
http://localhost:8080/
```

Para testar os endpoints pelo Swagger:

```
http://localhost:8080/q/swagger-ui
```

## Acesso Online

- **Front-end (Vercel):** [nuvem-do-bem-react.vercel.app](https://nuvem-do-bem-react.vercel.app)
- **API REST (Render):** [javasprint.onrender.com](https://javasprint.onrender.com)

> A API no Render pode demorar até 30 segundos na primeira requisição (plano gratuito hiberna após inatividade).

## Endpoints

### Geral

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | / | Tela inicial com lista de endpoints |

### Beneficiários

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /beneficiarios | Lista beneficiários |
| GET | /beneficiarios/{id} | Busca beneficiário por ID |
| POST | /beneficiarios | Insere beneficiário |
| POST | /beneficiarios/cadastrar | Cadastra beneficiário com senha |
| POST | /beneficiarios/login | Login do beneficiário |
| PUT | /beneficiarios/{id} | Atualiza beneficiário |
| DELETE | /beneficiarios/{id} | Remove beneficiário |

### Dentistas

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /dentistas | Lista dentistas |
| GET | /dentistas/{id} | Busca dentista por ID |
| POST | /dentistas | Insere dentista |
| POST | /dentistas/cadastrar | Cadastra dentista com senha |
| POST | /dentistas/login | Login do dentista |
| PUT | /dentistas/{id} | Atualiza dentista |
| DELETE | /dentistas/{id} | Remove dentista |

### Integrantes TDB

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /integrantes | Lista integrantes |
| GET | /integrantes/{id} | Busca integrante por ID |
| POST | /integrantes | Insere integrante |
| POST | /integrantes/cadastrar | Cadastra integrante com senha |
| POST | /integrantes/login | Login do integrante |
| PUT | /integrantes/{id} | Atualiza integrante |
| DELETE | /integrantes/{id} | Remove integrante |

### Patrocinadores

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /patrocinadores | Lista patrocinadores |
| GET | /patrocinadores/{id} | Busca patrocinador por ID |
| POST | /patrocinadores | Insere patrocinador |
| POST | /patrocinadores/cadastrar | Cadastra patrocinador com senha |
| POST | /patrocinadores/login | Login do patrocinador |
| PUT | /patrocinadores/{id} | Atualiza patrocinador |
| DELETE | /patrocinadores/{id} | Remove patrocinador |

### Casos

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /casos | Lista casos |
| GET | /casos/{id} | Busca caso por ID |
| POST | /casos | Abre caso |
| PUT | /casos/{id} | Atualiza caso |
| PUT | /casos/{idCaso}/enviar-pedido | Envia caso para dentista |
| PUT | /casos/{idCaso}/fechar | Fecha caso se existir diagnóstico |
| DELETE | /casos/{id} | Remove caso |

### Diagnósticos

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /diagnosticos | Lista diagnósticos |
| GET | /diagnosticos/{id} | Busca diagnóstico por ID |
| POST | /diagnosticos | Registra diagnóstico |
| PUT | /diagnosticos/{id} | Atualiza diagnóstico |
| DELETE | /diagnosticos/{id} | Remove diagnóstico |

### Pedidos de Encaminhamento

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /pedidos-encaminhamento | Lista pedidos |
| GET | /pedidos-encaminhamento/{id} | Busca pedido por ID |
| POST | /pedidos-encaminhamento | Cadastra pedido |
| PUT | /pedidos-encaminhamento/{id} | Atualiza pedido |
| PUT | /pedidos-encaminhamento/{id}/aceitar | Aceita pedido |
| PUT | /pedidos-encaminhamento/{id}/recusar | Recusa pedido |
| DELETE | /pedidos-encaminhamento/{id} | Remove pedido |

### Doações

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /doacoes | Lista doações |
| GET | /doacoes/{id} | Busca doação por ID |
| POST | /doacoes | Cadastra doação |
| PUT | /doacoes/{id} | Atualiza doação |
| DELETE | /doacoes/{id} | Remove doação |

### Evidências

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /evidencias | Lista evidências |
| GET | /evidencias/{id} | Busca evidência por ID |
| POST | /evidencias | Cadastra evidência |
| PUT | /evidencias/{id} | Atualiza evidência |
| DELETE | /evidencias/{id} | Remove evidência |

### Mensagens

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /mensagens | Lista mensagens |
| GET | /mensagens/{id} | Busca mensagem por ID |
| POST | /mensagens | Cadastra mensagem |
| PUT | /mensagens/{id} | Atualiza mensagem |
| DELETE | /mensagens/{id} | Remove mensagem |

### Histórico de Status

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /historicos-status | Lista histórico |
| GET | /historicos-status/{id} | Busca histórico por ID |
| POST | /historicos-status | Cadastra histórico |
| PUT | /historicos-status/{id} | Atualiza histórico |
| DELETE | /historicos-status/{id} | Remove histórico |

### Endereços (ViaCEP)

| Método | Endpoint | Função |
|--------|----------|--------|
| GET | /enderecos/cep/{cep} | Consulta endereço pelo CEP |
| GET | /enderecos/cep/{cep}/formatado | Retorna endereço formatado |

## Exemplos de JSON

### Beneficiário

```json
{
  "nome": "Pedro Rodrigues",
  "email": "pedro@email.com",
  "cpf": "12345678901",
  "dataNascimento": "2004-05-10",
  "telefone": "11999999999",
  "endereco": "Rua Exemplo, 100",
  "senha": "123456"
}
```

### Dentista

```json
{
  "nome": "Dra. Ana Souza",
  "email": "ana@email.com",
  "cro": "CROSP12345",
  "especialidade": "Ortodontia",
  "telefone": "11988887777",
  "senha": "123456"
}
```

### Patrocinador

```json
{
  "nome": "Empresa XYZ",
  "email": "contato@xyz.com",
  "cpfCnpj": "47274841882000",
  "telefone": "11947595716",
  "senha": "123456"
}
```

### Caso

```json
{
  "beneficiario": { "idBeneficiario": 1 },
  "integrante": { "idIntegrante": 1 }
}
```

### Diagnóstico

```json
{
  "descricao": "Cárie severa",
  "procedimento": "Extração",
  "dataDiagnostico": "2026-05-24",
  "caso": { "idCaso": 1 },
  "dentista": { "idDentista": 1 }
}
```

### Doação

```json
{
  "tipo": "Equipamento",
  "descricaoEquipamento": "Cadeira odontológica",
  "dataDoacao": "2026-05-24",
  "patrocinador": { "idPatrocinador": 1 }
}
```

### Mensagem

```json
{
  "texto": "Paciente agendado.",
  "remetente": "integrante",
  "caso": { "idCaso": 1 }
}
```

### Endereço (resposta ViaCEP)

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

## Classes de teste

Classes simples com `JOptionPane` para demonstração manual, em:

```
src/main/java/br/com/nuvemtech/main
```

- `TesteConexao` - testa a conexão com o banco
- `TesteBeneficiario` - cadastro, listagem, atualização e exclusão
- `TesteDentista` - cadastro e listagem
- `TesteCasoLogica` - regras de negócio de caso
- `TesteViaCep` - consulta de endereço por CEP

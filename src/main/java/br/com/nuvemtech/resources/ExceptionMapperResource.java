package br.com.nuvemtech.resources;

import br.com.nuvemtech.exceptions.NotFoundException;
import br.com.nuvemtech.exceptions.RegraNegocioException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class ExceptionMapperResource implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof NotFoundException || exception instanceof jakarta.ws.rs.NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ApiError("Endpoint ou registro não encontrado", 404)).build();
        }
        if (exception instanceof RegraNegocioException || exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiError(exception.getMessage(), 400)).build();
        }
        if (exception instanceof SQLException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiError("Erro ao acessar o banco de dados: " + exception.getMessage(), 500)).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiError("Erro interno: " + exception.getMessage(), 500)).build();
    }
}

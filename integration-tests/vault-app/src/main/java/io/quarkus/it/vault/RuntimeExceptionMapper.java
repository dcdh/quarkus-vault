package io.quarkus.it.vault;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(final RuntimeException exception) {
        return Response.serverError()
                .entity("Something wrong happened " + exception.getMessage() + " - " + exception.getClass().getSimpleName()
                        + (exception.getCause() != null ? ": " + exception.getCause().getMessage() : ""))
                .build();
    }
}

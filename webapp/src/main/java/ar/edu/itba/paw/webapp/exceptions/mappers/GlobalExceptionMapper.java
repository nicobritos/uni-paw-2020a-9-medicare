package ar.edu.itba.paw.webapp.exceptions.mappers;

import ar.edu.itba.paw.webapp.exceptions.APIErrorFactory;
import ar.edu.itba.paw.webapp.media_types.ErrorMIME;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        // TODO: Log
        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(APIErrorFactory.buildError(Status.INTERNAL_SERVER_ERROR).build())
                .type(ErrorMIME.ERROR)
                .build();
    }
}
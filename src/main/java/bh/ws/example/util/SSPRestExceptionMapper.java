package bh.ws.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SSPRestExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSPRestExceptionMapper.class);

    @Override
    public Response toResponse(final Exception exception) {
        LOGGER.error("Java exception handling by RS", exception);

        Response.Status status = mapErrorStatusCode(exception);
        String message = prepareMessage(exception);

        return Response.status(status)
                .entity(message)
                .type("text/plain")
                .build();
    }

    private Response.Status mapErrorStatusCode(final Exception exception) {
        //TODO: map business errors to HTTP errors
        if (exception instanceof ConstraintViolationException) {
            return Response.Status.BAD_REQUEST;
        }
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

    private String prepareMessage(final Exception exception) {
        //TODO: build error message from the exception. We can have two kind of messages.
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolation = (ConstraintViolationException) exception;
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<?> cv : constraintViolation.getConstraintViolations()) {
                sb.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
            }
            return sb.toString();
        } else {
            return exception.getMessage();
        }
    }
}
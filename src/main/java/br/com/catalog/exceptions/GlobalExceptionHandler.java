// package br.com.catalog.exceptions;

// import java.util.List;
// import java.util.stream.Collectors;

// import jakarta.validation.ConstraintViolationException;
// import jakarta.ws.rs.NotFoundException;
// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.ext.ExceptionMapper;
// import jakarta.ws.rs.ext.Provider;

// public class GlobalExceptionHandler {

//     @Provider
//     public static class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
//         @Override
//         public Response toResponse(ConstraintViolationException exception) {
//             List<ErrorMessageDTO> errors = exception.getConstraintViolations().stream()
//                     .map(violation -> {
//                         String path = violation.getPropertyPath().toString();
//                         String field = path.substring(path.lastIndexOf('.') + 1);
//                         String message = violation.getMessage();
//                         return new ErrorMessageDTO(message, field);
//                     })
//                     .collect(Collectors.toList());

//             return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
//         }
//     }

//     @Provider
//     public static class BusinessRuleExceptionHandler implements ExceptionMapper<BusinessRuleException> {
//         @Override
//         public Response toResponse(BusinessRuleException exception) {
//             return Response.status(Response.Status.BAD_REQUEST).entity(exception.getErrors()).build();
//         }
//     }

//     @Provider
//     public static class NotFoundExceptionHandler implements ExceptionMapper<NotFoundException> {
//         @Override
//         public Response toResponse(NotFoundException exception) {
//             ErrorMessageDTO error = new ErrorMessageDTO(exception.getMessage(), null);
//             return Response.status(Response.Status.NOT_FOUND).entity(error).build();
//         }
//     }

//     @Provider
//     public static class GenericExceptionHandler implements ExceptionMapper<Throwable> {
//         @Override
//         public Response toResponse(Throwable exception) {
//             ErrorMessageDTO error = new ErrorMessageDTO("Ocorreu um erro interno no servidor.", "server");
//             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
//         }
//     }
// }

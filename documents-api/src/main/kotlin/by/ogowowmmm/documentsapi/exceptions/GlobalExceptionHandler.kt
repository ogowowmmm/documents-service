package by.ogowowmmm.documentsapi.exceptions

import by.ogowowmmm.documents.service.exceptions.BusinessException
import by.ogowowmmm.documents.service.exceptions.ErrorResponse
import by.ogowowmmm.documents.service.exceptions.ErrorResponseFactory
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(private val errorResponseFactory: ErrorResponseFactory) {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ResponseEntity<ErrorResponse> {
        if (exception.cause != null) {
            log.error("Internal error: {}", exception.errorCode.code, exception.cause)
        }

        return ResponseEntity.status(exception.errorCode.httpStatus)
            .body(errorResponseFactory.build(exception))
    }
}
package by.ogowowmmm.documents.service.exceptions

abstract class AbstractException(
    val errorCode: ErrorCode,
    cause: Throwable? = null
) : RuntimeException(errorCode.messageKey, cause)
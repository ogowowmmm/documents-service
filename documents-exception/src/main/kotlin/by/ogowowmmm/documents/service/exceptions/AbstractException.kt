package by.ogowowmmm.documents.service.exceptions

abstract class AbstractException(
    val errorCode: ErrorCode,
    cause: Exception? = null
) : RuntimeException(errorCode.messageKey, cause)
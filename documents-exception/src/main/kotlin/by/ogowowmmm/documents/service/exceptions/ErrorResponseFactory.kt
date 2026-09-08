package by.ogowowmmm.documents.service.exceptions

class ErrorResponseFactory(
    private val properties: ExceptionMessagesProperties,
    private val application: String
) {

    fun build(exception: AbstractException): ErrorResponse {
        val messageKey = exception.errorCode.messageKey
        val messages = properties.messages
            .mapNotNull { (locale, map) -> map[messageKey]?.let { locale to it } }
            .toMap()
        return ErrorResponse(
            application = application,
            errorCode = exception.errorCode.code,
            messages = messages,
        )
    }
}
package by.ogowowmmm.documents.service.exceptions

data class ErrorResponse(
    val application: String,
    val errorCode: String,
    val messages: Map<String, String>,
)
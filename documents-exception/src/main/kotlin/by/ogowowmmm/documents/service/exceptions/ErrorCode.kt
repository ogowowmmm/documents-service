package by.ogowowmmm.documents.service.exceptions

interface ErrorCode {
    val messageKey: String
    val httpStatus: Int
    val code: String
}
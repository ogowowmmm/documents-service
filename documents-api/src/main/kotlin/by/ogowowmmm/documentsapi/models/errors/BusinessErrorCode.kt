package by.ogowowmmm.documentsapi.models.errors

import by.ogowowmmm.documents.service.exceptions.ErrorCode

enum class BusinessErrorCode(
    override val messageKey: String,
    override val httpStatus: Int = 400,
) : ErrorCode {
    DOCUMENT_NOT_FOUND("error.document.not.found", 404),
    DOCUMENT_ALREADY_EXISTS("error.document.already.exists"),
    DOCUMENT_REPLACING_ERROR("error.document.replacing.error"),
    ;

    override val code: String
        get() = name
}
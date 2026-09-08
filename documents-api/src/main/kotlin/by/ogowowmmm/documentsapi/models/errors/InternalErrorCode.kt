package by.ogowowmmm.documentsapi.models.errors

import by.ogowowmmm.documents.service.exceptions.ErrorCode

enum class InternalErrorCode(
    override val messageKey: String,
    override val httpStatus: Int = 500,
) : ErrorCode {
    DOCUMENT_DELETION_ERROR("error.document.deletion.error"),
    DOCUMENT_DOWNLOADING_ERROR("error.document.downloading.error"),
    DOCUMENT_UPLOAD_ERROR("error.document.upload.error"),
    ;

    override val code: String
        get() = name
}
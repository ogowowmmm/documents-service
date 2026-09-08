package by.ogowowmmm.documents.service.exceptions

class BusinessException(errorCode: ErrorCode) : AbstractException(errorCode) {

    companion object {
        fun of(errorCode: ErrorCode): BusinessException = BusinessException(errorCode)
    }
}
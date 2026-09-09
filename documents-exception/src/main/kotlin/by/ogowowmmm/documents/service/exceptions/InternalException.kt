package by.ogowowmmm.documents.service.exceptions

class InternalException(errorCode: ErrorCode, cause: Throwable? = null) :
    AbstractException(errorCode, cause) {

    companion object {
        fun of(errorCode: ErrorCode, cause: Throwable? = null): InternalException =
            InternalException(errorCode, cause)
    }
}
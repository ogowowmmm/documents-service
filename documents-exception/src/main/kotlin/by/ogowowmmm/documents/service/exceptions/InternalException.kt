package by.ogowowmmm.documents.service.exceptions

class InternalException(errorCode: ErrorCode, cause: Exception? = null) :
    AbstractException(errorCode, cause) {

    companion object {
        fun of(errorCode: ErrorCode, cause: Exception? = null): InternalException =
            InternalException(errorCode, cause)
    }
}
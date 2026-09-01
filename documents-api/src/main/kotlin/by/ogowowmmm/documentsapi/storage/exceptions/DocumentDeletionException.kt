package by.ogowowmmm.documentsapi.storage.exceptions

import java.util.*

class DocumentDeletionException(id: UUID, reason: Exception) :
    Exception("Document deletion with id '$id' failed", reason)
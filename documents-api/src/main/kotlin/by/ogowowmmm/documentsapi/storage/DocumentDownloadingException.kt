package by.ogowowmmm.documentsapi.storage

import java.util.*

class DocumentDownloadingException(id: UUID, reason: Exception) :
    Exception("Document with id '${id}' downloading failed", reason)
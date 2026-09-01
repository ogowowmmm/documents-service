package by.ogowowmmm.documentsapi.storage.exceptions

import java.util.*

class DocumentNotFoundException(id: UUID) :
    Exception("Document with id '$id' not found")
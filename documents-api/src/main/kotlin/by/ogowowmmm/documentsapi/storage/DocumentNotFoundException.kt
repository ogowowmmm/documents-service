package by.ogowowmmm.documentsapi.storage

import java.util.*

class DocumentNotFoundException(id: UUID) :
    Exception("Document with id '${id}' not found")
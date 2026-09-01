package by.ogowowmmm.documentsapi.storage.exceptions

import java.util.*

class DocumentAlreadyExistsException(id: UUID) : Exception("Document with id '$id' already exists")
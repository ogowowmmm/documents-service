package by.ogowowmmm.documentsapi.dto

import java.util.*

class Document(
    val documentId: UUID,
    val ownerId: UUID,
    val replaceDocumentId: UUID,
    val metadata: FileMetadata,
    val createdAt: Date,
    val fileId: UUID,
)
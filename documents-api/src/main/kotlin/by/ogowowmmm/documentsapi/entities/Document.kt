package by.ogowowmmm.documentsapi.entities

import by.ogowowmmm.documentsapi.dto.FileMetadata
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.time.Instant

@Document("documents")
data class Document(
    @Id
    val id: UUID,
    val ownerId: UUID,
    val replaceDocumentId: UUID? = null,
    val metadata: FileMetadata,
    val createdAt: Instant,
)
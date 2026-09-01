package by.ogowowmmm.documentsapi.entities

import by.ogowowmmm.documentsapi.dto.FileMetadata
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import kotlin.time.Instant

@Document("documents")
@CompoundIndex(
    name = "owner_idempotency_unique",
    def = "{'ownerId': 1, 'idempotencyKey': 1}",
    unique = true
)
data class Document(
    @Id
    val id: UUID,
    val idempotencyKey: UUID,
    val ownerId: UUID,
    val replaceDocumentId: UUID? = null,
    val metadata: FileMetadata,
    val createdAt: Instant,
)
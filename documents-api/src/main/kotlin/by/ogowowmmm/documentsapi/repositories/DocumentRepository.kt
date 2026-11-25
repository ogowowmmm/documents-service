package by.ogowowmmm.documentsapi.repositories

import by.ogowowmmm.documentsapi.repositories.entities.Document
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface DocumentRepository : CoroutineCrudRepository<Document, UUID> {

    suspend fun existsByOwnerIdAndIdempotencyKey(ownerId: UUID, idempotencyKey: UUID): Boolean
    suspend fun existsByOwnerIdAndId(ownerId: UUID, documentId: UUID): Boolean
}
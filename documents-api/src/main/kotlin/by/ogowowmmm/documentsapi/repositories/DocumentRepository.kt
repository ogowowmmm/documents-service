package by.ogowowmmm.documentsapi.repositories

import by.ogowowmmm.documentsapi.entities.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface DocumentRepository : MongoRepository<Document, UUID> {

    fun existsByOwnerIdAndIdempotencyKey(ownerId: UUID, idempotencyKey: UUID): Boolean
    fun existsByOwnerIdAndId(ownerId: UUID, documentId: UUID): Boolean
}
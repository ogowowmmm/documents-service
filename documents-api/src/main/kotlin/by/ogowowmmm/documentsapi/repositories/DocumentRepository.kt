package by.ogowowmmm.documentsapi.repositories

import by.ogowowmmm.documentsapi.entities.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface DocumentRepository : MongoRepository<Document, UUID>
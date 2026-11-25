package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documents.service.exceptions.BusinessException
import by.ogowowmmm.documentsapi.models.errors.BusinessErrorCode
import by.ogowowmmm.documentsapi.repositories.DocumentRepository
import by.ogowowmmm.documentsapi.repositories.entities.Document
import by.ogowowmmm.documentsapi.repositories.entities.FileMetadata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.util.*
import kotlin.time.Clock

@Service
class DocumentCore(
    private val documentRepository: DocumentRepository,
    private val mongoOperations: ReactiveMongoOperations,
    private val fileStagingService: FileStagingService,
    private val fileStorageService: FileStorageService,
) {


    suspend fun upload(idempotencyKey: UUID, replaceDocumentId: UUID?, file: FilePart, ownerId: UUID): UUID {
        if (documentRepository.existsByOwnerIdAndIdempotencyKey(ownerId, idempotencyKey)) {
            throw BusinessException.of(BusinessErrorCode.DOCUMENT_ALREADY_EXISTS)
        }

        if (replaceDocumentId != null &&
            !documentRepository.existsByOwnerIdAndId(ownerId, replaceDocumentId)
        ) {
            throw BusinessException.of(BusinessErrorCode.DOCUMENT_REPLACING_ERROR)
        }

        val stagedFile = fileStagingService.stage(file)

        try {
            val documentId = UUID.randomUUID()

            val document = Document(
                id = documentId,
                idempotencyKey = idempotencyKey,
                ownerId = ownerId,
                replaceDocumentId = replaceDocumentId,
                metadata = FileMetadata(
                    stagedFile.originalFilename,
                    stagedFile.size,
                    stagedFile.contentType
                ),
                createdAt = Clock.System.now()
            )

            fileStorageService.upload(documentId, stagedFile)
            mongoOperations.insert(document).awaitSingle()

            return documentId
        } finally {
            withContext(Dispatchers.IO) {
                Files.deleteIfExists(stagedFile.path)
            }
        }
    }
}
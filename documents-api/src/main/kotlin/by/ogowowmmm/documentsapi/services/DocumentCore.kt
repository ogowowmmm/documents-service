package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.dto.FileMetadata
import by.ogowowmmm.documentsapi.entities.Document
import by.ogowowmmm.documentsapi.extensions.safeContentType
import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.repositories.DocumentRepository
import by.ogowowmmm.documentsapi.storage.DocumentUploadingException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.time.Clock

@Service
class DocumentCore(
    val documentRepository: DocumentRepository,
    val documentStorage: DocumentStorage,
) {

    fun upload(idempotencyKey: UUID, replaceDocumentId: String?, file: MultipartFile, ownerId: UUID) {
        documentStorage.upload(
            FileUploadData(
                idempotencyKey = idempotencyKey,
                inputStream = file.inputStream,
                size = file.size,
                contentType = file.safeContentType()
            )
        )

        val document = Document(
            id = idempotencyKey,
            ownerId = ownerId,
            replaceDocumentId = replaceDocumentId?.let { UUID.fromString(replaceDocumentId) },
            metadata = FileMetadata(file.name, file.size, file.safeContentType()),
            createdAt = Clock.System.now()
        )

        try {
            documentRepository.save(document)
        } catch (e: Exception) {
            documentStorage.delete(idempotencyKey)
            throw DocumentUploadingException(e)
        }
    }
}
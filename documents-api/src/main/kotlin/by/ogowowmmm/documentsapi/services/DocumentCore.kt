package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.dto.FileMetadata
import by.ogowowmmm.documentsapi.entities.Document
import by.ogowowmmm.documentsapi.extensions.safeContentType
import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.repositories.DocumentRepository
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentAlreadyExistsException
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentReplacingException
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentUploadingException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.time.Clock

@Service
class DocumentCore(
    val documentRepository: DocumentRepository,
    val documentStorage: DocumentStorage,
) {

    fun upload(idempotencyKey: UUID, replaceDocumentId: UUID?, file: MultipartFile, ownerId: UUID): UUID {
        if (documentRepository.existsByOwnerIdAndIdempotencyKey(ownerId, idempotencyKey)) {
            throw DocumentAlreadyExistsException(idempotencyKey)
        }

        if (replaceDocumentId != null && !documentRepository.existsByOwnerIdAndId(ownerId, replaceDocumentId)) {
            throw DocumentReplacingException("Document '$replaceDocumentId' is unavailable for replacement")
        }

        val documentId = documentStorage.upload(
            FileUploadData(
                inputStream = file.inputStream,
                size = file.size,
                contentType = file.safeContentType()
            )
        )

        val document = Document(
            id = documentId,
            idempotencyKey = idempotencyKey,
            ownerId = ownerId,
            replaceDocumentId = replaceDocumentId,
            metadata = FileMetadata(file.name, file.size, file.safeContentType()),
            createdAt = Clock.System.now()
        )

        try {
            documentRepository.insert(document)
        } catch (e: Exception) {
            documentStorage.delete(documentId)
            throw DocumentUploadingException(e)
        }

        return documentId
    }
}
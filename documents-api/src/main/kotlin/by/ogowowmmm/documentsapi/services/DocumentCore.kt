package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documents.service.exceptions.BusinessException
import by.ogowowmmm.documents.service.exceptions.InternalException
import by.ogowowmmm.documentsapi.extensions.safeContentType
import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.models.errors.BusinessErrorCode
import by.ogowowmmm.documentsapi.models.errors.InternalErrorCode
import by.ogowowmmm.documentsapi.repositories.DocumentRepository
import by.ogowowmmm.documentsapi.repositories.entities.Document
import by.ogowowmmm.documentsapi.repositories.entities.FileMetadata
import by.ogowowmmm.documentsapi.storage.Storage
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.time.Clock

@Service
class DocumentCore(
    val documentRepository: DocumentRepository,
    val documentStorage: Storage,
) {

    fun upload(idempotencyKey: UUID, replaceDocumentId: UUID?, file: MultipartFile, ownerId: UUID): UUID {
        if (documentRepository.existsByOwnerIdAndIdempotencyKey(ownerId, idempotencyKey)) {
            throw BusinessException.of(BusinessErrorCode.DOCUMENT_ALREADY_EXISTS)
        }

        if (replaceDocumentId != null && !documentRepository.existsByOwnerIdAndId(ownerId, replaceDocumentId)) {
            throw BusinessException.of(BusinessErrorCode.DOCUMENT_REPLACING_ERROR)
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
            throw InternalException.of(InternalErrorCode.DOCUMENT_UPLOAD_ERROR, e)
        }

        return documentId
    }
}
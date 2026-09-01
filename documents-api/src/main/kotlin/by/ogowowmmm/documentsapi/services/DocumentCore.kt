package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.extensions.safeContentType
import by.ogowowmmm.documentsapi.models.FileUploadData
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class DocumentCore(
    val documentStorage: DocumentStorage,
) {

    fun upload(idempotencyKey: String, file: MultipartFile): UUID =
        documentStorage.upload(
            FileUploadData(
                inputStream = file.inputStream,
                size = file.size,
                contentType = file.safeContentType()
            )
        )
}
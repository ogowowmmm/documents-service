package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.storage.Storage
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DocumentStorage(val storage: Storage) {

    fun upload(fileUploadData: FileUploadData) {
        storage.upload(fileUploadData)
    }

    fun download(uuid: UUID) {
        storage.download(uuid)
    }

    fun delete(uuid: UUID) {
        storage.delete(uuid)
    }
}
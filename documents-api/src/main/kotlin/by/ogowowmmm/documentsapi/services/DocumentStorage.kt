package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.storage.Storage
import org.springframework.stereotype.Service
import java.util.*

@Service
class DocumentStorage(val storage: Storage) {

    fun upload(fileUploadData: FileUploadData): UUID {
        return storage.upload(fileUploadData)
    }
}
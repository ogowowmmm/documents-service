package by.ogowowmmm.documentsapi.storage

import by.ogowowmmm.documentsapi.models.FileUploadData
import java.io.InputStream
import java.util.*

interface Storage {
    fun upload(fileUploadData: FileUploadData) : UUID
    fun download(uuid: UUID): InputStream
    fun delete(uuid: UUID)
}
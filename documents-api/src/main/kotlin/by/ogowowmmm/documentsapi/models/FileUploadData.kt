package by.ogowowmmm.documentsapi.models

import java.io.InputStream

class FileUploadData(
    val inputStream: InputStream,
    val size: Long,
    val contentType: String
)
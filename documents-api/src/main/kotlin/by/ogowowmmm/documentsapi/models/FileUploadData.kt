package by.ogowowmmm.documentsapi.models

import java.io.InputStream
import java.util.UUID

class FileUploadData(
    val idempotencyKey: UUID,
    val inputStream: InputStream,
    val size: Long,
    val contentType: String
)
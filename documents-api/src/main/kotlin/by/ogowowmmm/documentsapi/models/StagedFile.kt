package by.ogowowmmm.documentsapi.models

import java.nio.file.Path

data class StagedFile(
    val path: Path,
    val originalFilename: String,
    val size: Long,
    val contentType: String
)
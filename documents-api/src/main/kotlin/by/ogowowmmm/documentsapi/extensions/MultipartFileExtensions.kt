package by.ogowowmmm.documentsapi.extensions

import org.springframework.web.multipart.MultipartFile

fun MultipartFile.safeContentType(): String = this.contentType ?: "unknown"
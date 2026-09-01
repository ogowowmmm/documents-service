package by.ogowowmmm.documentsapi.storage.minio

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "minio")
class MinioProperties(
    val endpoint: String,
    val accessKey: String,
    val secretKey: String,
    val bucket: String,
)
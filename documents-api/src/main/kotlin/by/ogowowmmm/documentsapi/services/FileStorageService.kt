package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.configurations.properties.MinioProperties
import by.ogowowmmm.documentsapi.models.StagedFile
import kotlinx.coroutines.future.await
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID

@Service
class FileStorageService(
    private val client: S3AsyncClient,
    private val properties: MinioProperties
) {
    suspend fun upload(documentId: UUID, file: StagedFile) {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(properties.bucket)
            .key(documentId.toString())
            .contentType(file.contentType)
            .build()
        client.putObject(
            putObjectRequest,
            AsyncRequestBody.fromFile(file.path)
        ).await()
    }
}
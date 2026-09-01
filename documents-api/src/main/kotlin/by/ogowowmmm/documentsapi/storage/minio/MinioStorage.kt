package by.ogowowmmm.documentsapi.storage.minio

import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.storage.Storage
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentDeletionException
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentDownloadingException
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentNotFoundException
import by.ogowowmmm.documentsapi.storage.exceptions.DocumentUploadingException
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import org.springframework.stereotype.Component
import java.io.InputStream
import java.util.*

@Component
class MinioStorage(
    val client: MinioClient,
    val properties: MinioProperties
) : Storage {

    override fun upload(fileUploadData: FileUploadData): UUID {
        try {
            val uuid = UUID.randomUUID()
            client.putObject(
                PutObjectArgs.builder()
                    .bucket(properties.bucket)
                    .stream(fileUploadData.inputStream, fileUploadData.size, -1)
                    .`object`(uuid.toString())
                    .contentType(fileUploadData.contentType)
                    .build()
            )
            return uuid
        } catch (e: Exception) {
            throw DocumentUploadingException(e)
        }
    }

    override fun download(uuid: UUID): InputStream = try {
        client.getObject(
            GetObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(uuid.toString())
                .build()
        ) ?: throw DocumentNotFoundException(uuid)
    } catch (e: Exception) {
        throw DocumentDownloadingException(uuid, e)
    }

    override fun delete(uuid: UUID) = try {
        client.removeObject(
            RemoveObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(uuid.toString())
                .build()
        )
    } catch (e: Exception) {
        throw DocumentDeletionException(uuid, e)
    }
}
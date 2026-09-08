package by.ogowowmmm.documentsapi.storage.minio

import by.ogowowmmm.documents.service.exceptions.BusinessException
import by.ogowowmmm.documents.service.exceptions.InternalException
import by.ogowowmmm.documentsapi.configurations.properties.MinioProperties
import by.ogowowmmm.documentsapi.models.FileUploadData
import by.ogowowmmm.documentsapi.models.errors.BusinessErrorCode
import by.ogowowmmm.documentsapi.models.errors.InternalErrorCode
import by.ogowowmmm.documentsapi.storage.Storage
import io.minio.GetObjectArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import io.minio.errors.MinioException
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
            throw InternalException.of(InternalErrorCode.DOCUMENT_UPLOAD_ERROR, e)
        }
    }

    override fun download(uuid: UUID): InputStream = try {
        client.getObject(
            GetObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(uuid.toString())
                .build()
        ) ?: throw BusinessException.of(BusinessErrorCode.DOCUMENT_NOT_FOUND)
    } catch (e: MinioException) {
        throw InternalException.of(InternalErrorCode.DOCUMENT_DOWNLOADING_ERROR, e)
    }

    override fun delete(uuid: UUID) = try {
        client.removeObject(
            RemoveObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(uuid.toString())
                .build()
        )
    } catch (e: Exception) {
        throw InternalException.of(InternalErrorCode.DOCUMENT_DELETION_ERROR, e)
    }
}
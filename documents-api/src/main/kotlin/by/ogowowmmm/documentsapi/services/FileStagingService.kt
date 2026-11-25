package by.ogowowmmm.documentsapi.services

import by.ogowowmmm.documentsapi.models.StagedFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class FileStagingService {

    suspend fun stage(file: FilePart): StagedFile =
        withContext(Dispatchers.IO) {
            val path = Files.createTempFile("document-", ".tmp")
            try {
                file.transferTo(path).awaitSingleOrNull()
                StagedFile(
                    path,
                    file.filename(),
                    Files.size(path),
                    file.headers().contentType?.toString() ?: "application/octet-stream"
                )
            } catch (e: Exception) {
                withContext(NonCancellable) {
                    try {
                        Files.deleteIfExists(path)
                    } catch (cleanupException: Exception) {
                        e.addSuppressed(cleanupException)
                    }
                }
                throw e
            }
        }
}
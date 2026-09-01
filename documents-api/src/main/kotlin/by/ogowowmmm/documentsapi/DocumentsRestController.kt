package by.ogowowmmm.documentsapi

import by.ogowowmmm.documentsapi.services.DocumentCore
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/v1")
class DocumentsRestController(val core: DocumentCore) {

    @PostMapping("/documents")
    fun uploadDocument(
        @RequestPart("file") file: MultipartFile,
        @RequestParam("idempotencyKey") idempotencyKey: UUID,
        @RequestParam("replaceDocumentId", required = false) replaceDocumentId: String?,
    ) {
        core.upload(idempotencyKey, replaceDocumentId, file)
    }
}

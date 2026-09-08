package by.ogowowmmm.documentsapi.controller

import by.ogowowmmm.documentsapi.services.DocumentCore
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/api/v1")
class DocumentsRestController(val core: DocumentCore) {

    @PostMapping("/documents")
    fun uploadDocument(
        @RequestPart("file") file: MultipartFile,
        @RequestParam("idempotencyKey") idempotencyKey: UUID,
        @RequestParam("replaceDocumentId", required = false) replaceDocumentId: UUID?,
        @AuthenticationPrincipal jwt: Jwt
    ): UUID {
        return core.upload(idempotencyKey, replaceDocumentId, file, UUID.fromString(jwt.subject))
    }
}
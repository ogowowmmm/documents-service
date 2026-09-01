package by.ogowowmmm.documentsapi

import by.ogowowmmm.documentsapi.services.DocumentCore
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/v1")
class DocumentsRestController(val core: DocumentCore) {

    @PostMapping("/documents")
    fun post(@RequestParam("key") idempotencyKey: String, @RequestPart("file") file: MultipartFile): UUID {
        return core.upload(idempotencyKey, file)
    }
}

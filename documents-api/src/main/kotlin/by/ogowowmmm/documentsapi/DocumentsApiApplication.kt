package by.ogowowmmm.documentsapi

import by.ogowowmmm.documentsapi.configurations.properties.MinioProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MinioProperties::class)
class DocumentsApiApplication

fun main(args: Array<String>) {
    runApplication<DocumentsApiApplication>(*args)
}

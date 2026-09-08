package by.ogowowmmm.documents.service.exceptions

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud")
class ExceptionMessagesProperties(
    val messages: Map<String, Map<String, String>> = emptyMap()
)
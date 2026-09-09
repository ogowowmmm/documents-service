package by.ogowowmmm.documentsapi.configurations

import by.ogowowmmm.documents.service.exceptions.ErrorResponseFactory
import by.ogowowmmm.documents.service.exceptions.ExceptionMessagesProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ExceptionMessagesProperties::class)
class ExceptionConfig {

    @Bean
    fun errorResponseFactory(
        properties: ExceptionMessagesProperties,
        @Value($$"${spring.application.name}") applicationName: String,
    ): ErrorResponseFactory = ErrorResponseFactory(properties, applicationName)
}

package by.ogowowmmm.documentsapi.configurations

import by.ogowowmmm.documentsapi.configurations.properties.MinioProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import java.net.URI

@Configuration
class MinioConfig {

    @Bean
    fun s3AsyncClient(properties: MinioProperties): S3AsyncClient =
        S3AsyncClient.builder()
            .endpointOverride(URI.create(properties.endpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        properties.accessKey,
                        properties.secretKey
                    )
                )
            )
            .region(Region.US_EAST_1)
            .forcePathStyle(true)
            .build()
}

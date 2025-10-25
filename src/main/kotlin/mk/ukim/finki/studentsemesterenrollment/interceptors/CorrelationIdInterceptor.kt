package mk.ukim.finki.studentsemesterenrollment.interceptors

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component
import java.util.*

@Component
class CorrelationIdInterceptor : RequestInterceptor {
    override fun apply(requestTemplate: RequestTemplate) {
        // Check if the request already has a Correlation ID
        if (!requestTemplate.headers().containsKey("X-Correlation-ID")) {
            // If not, generate a new one
            val correlationId = UUID.randomUUID().toString()
            requestTemplate.header("X-Correlation-ID", correlationId)
        }
    }
}
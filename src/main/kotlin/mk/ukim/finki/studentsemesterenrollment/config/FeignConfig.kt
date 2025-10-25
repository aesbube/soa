package mk.ukim.finki.studentsemesterenrollment.config

import mk.ukim.finki.studentsemesterenrollment.interceptors.CorrelationIdInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Bean
    fun correlationIdInterceptor(): CorrelationIdInterceptor {
        return CorrelationIdInterceptor()
    }
}
package mk.ukim.finki.studentsemesterenrollment.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

}

@Bean
inline fun <reified T> loggerFor(): Logger = LoggerFactory.getLogger(T::class.java)
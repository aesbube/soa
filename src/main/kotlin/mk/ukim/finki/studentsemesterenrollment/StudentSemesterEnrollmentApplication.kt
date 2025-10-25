package mk.ukim.finki.studentsemesterenrollment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@EnableFeignClients
class StudentSemesterEnrollmentApplication

fun main(args: Array<String>) {
    runApplication<StudentSemesterEnrollmentApplication>(*args)
}

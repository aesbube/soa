package mk.ukim.finki.studentsemesterenrollment

import mk.ukim.finki.studentsemesterenrollment.web.StudentSemesterEnrollmentRestApi
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(
    controllers = [StudentSemesterEnrollmentRestApi::class],
    excludeAutoConfiguration = [
        SecurityAutoConfiguration::class,
        SecurityFilterAutoConfiguration::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StudentSemesterEnrollmentITest {

    fun `start enrollment`() {

    }
}
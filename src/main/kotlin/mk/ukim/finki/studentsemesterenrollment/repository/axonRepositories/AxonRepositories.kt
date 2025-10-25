package mk.ukim.finki.studentsemesterenrollment.repository.axonRepositories

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import mk.ukim.finki.studentsemesterenrollment.model.StudentRecord
import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.eventhandling.EventBus
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.modelling.command.GenericJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.axonframework.modelling.command.Repository

@Configuration("axonRepositoriesConfiguration")
class AxonRepositoriesConfiguration(@PersistenceContext val entityManager: EntityManager) {
    @Bean("axonStudentSemesterEnrollmentRepository")
    fun studentSemesterEnrolmentGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<StudentSemesterEnrollment> {
        return GenericJpaRepository.builder(StudentSemesterEnrollment::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { StudentSemesterEnrollmentId(it) }
            .build()
    }


    @Bean("axonStudentRecordRepository")
    fun studentRecordJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<StudentRecord> {
        return GenericJpaRepository.builder(StudentRecord::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { StudentId(it) }
            .build()
    }
}

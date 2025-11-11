package mk.ukim.finki.studentsemesterenrollment.handlers

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.client.SemesterClient
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SemesterEventHandler(
    private val semesterClient: SemesterClient,
    private val semesterSnapshotRepository: SemesterSnapshotRepository,
) {

    private val logger = loggerFor<SemesterEventHandler>()

    @EventHandler
    @Transactional
    fun on(event: SemesterCreatedEvent) {
        logger.info("Handling SemesterCreatedEvent: ${event.semesterId}")

        try {
            val semesterDto = semesterClient.getSemesterById(event.semesterId.value)

            val snapshot = SemesterSnapshot(
                id = semesterDto.semesterId,
            cycleSemesterId = CycleSemesterId(semesterDto.semesterId, semesterDto.cycleId),
            state = semesterDto.state,
            enrollmentStartDate = semesterDto.enrollmentStartDate.toLocalDateTime(),
            enrollmentEndDate = semesterDto.enrollmentEndDate.toLocalDateTime(),
            )

            semesterSnapshotRepository.save(snapshot)
            logger.info("Saved semester snapshot for: ${event.semesterId}")

        } catch (e: Exception) {
            logger.error("Failed to process SemesterCreatedEvent", e)
        }
    }
}
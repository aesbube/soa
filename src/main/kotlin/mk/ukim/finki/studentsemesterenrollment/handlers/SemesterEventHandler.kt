package mk.ukim.finki.studentsemesterenrollment.handlers

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.client.SemesterClient
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.repository.findByIdOrNull
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

    @EventHandler
    @Transactional
    fun on(event: SemesterStateUpdatedEvent) {
        logger.info("Handling SemesterUpdatedEvent: ${event.semesterId}")

        try {
            val existing = semesterSnapshotRepository.findByIdOrNull(event.semesterId) ?: throw RuntimeException("Semester with id ${event.semesterId} not found")

            val snapshot = SemesterSnapshot(
                id = existing.id,
                cycleSemesterId = existing.cycleSemesterId,
                state = event.state,
                enrollmentStartDate = existing.enrollmentStartDate,
                enrollmentEndDate = existing.enrollmentEndDate,
            )

            semesterSnapshotRepository.save(snapshot)
            logger.info("Updated semester state for: ${event.semesterId} to state: ${event.state}")

        } catch (e: Exception) {
            logger.error("Failed to process SemesterStateUpdatedEvent", e)
        }
    }
}

data class SemesterStateUpdatedEvent(
    val semesterId: SemesterId,
    val state: SemesterState,
)
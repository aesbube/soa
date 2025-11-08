package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.StudentRecord
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRecordJpaRepository : JpaRepository<StudentRecord, StudentId>
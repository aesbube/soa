package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.SubjectSlot
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SubjectSlotRepository : JpaRepository<SubjectSlot, SubjectSlotId> {
    @Query(
        """
    SELECT * FROM subject_slot 
    WHERE student_id = :studentId
    AND (
        subject_id = :subjectId 
        OR (
            subject_id LIKE '%_%' 
            AND SUBSTRING(subject_id, 1, 5) = SUBSTRING(:subjectId, 1, 5)
        )
    )
    ORDER BY 
        CASE WHEN subject_id = :subjectId THEN 0 ELSE 1 END,
        id
    LIMIT 1
""", nativeQuery = true
    )
    fun findBySubjectIdAndStudentId(subjectId: String, studentId: String): SubjectSlot
}
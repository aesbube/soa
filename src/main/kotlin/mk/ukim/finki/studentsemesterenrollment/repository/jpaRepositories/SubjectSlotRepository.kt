package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.SubjectSlot
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SubjectSlotRepository : JpaRepository<SubjectSlot, SubjectSlotId> {
    @Query("""
    SELECT s FROM SubjectSlot s 
    WHERE s.studentId.index = :studentId
    AND (
        s.subjectId.value = :subjectId
        OR (
            s.placeholder = true
            AND SUBSTRING(s.subjectId.value, 1, 6) = SUBSTRING(:subjectId, 1, 6)
        )
    )
    ORDER BY 
        CASE WHEN s.subjectId.value = :subjectId THEN 0 ELSE 1 END,
        s.id.id
        LIMIT 1
""")
    fun findBySubjectIdAndStudentId(
        @Param("subjectId") subjectId: String,
        @Param("studentId") studentId: String
    ): SubjectSlot?
}
package mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.hibernate.Hibernate

@Table(name = "subject")
@Entity
class SubjectAggregateSnapshot(
    @EmbeddedId
    val id: SubjectCode,
    val name: SubjectName,
    val abbreviation: SubjectAbbreviation,
    val semesterCode: SemesterId,
    val ects: ECTSCredits,
    @Enumerated(EnumType.STRING) val semester: SemesterType,
    val classesPerWeek: ClassesPerWeek,
//    val dependencies: SubjectDependencies
    // TODO: Fix dependencies
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val subject = other as SubjectAggregateSnapshot
        return id == subject.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return this::class.simpleName + "(id = $id, name = $name, abbreviation = $abbreviation, semester = $semester, classesPerWeek = $classesPerWeek, dependencies = orel)"
    }
}

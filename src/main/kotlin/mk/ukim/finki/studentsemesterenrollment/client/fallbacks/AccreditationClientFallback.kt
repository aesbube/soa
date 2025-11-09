package mk.ukim.finki.studentsemesterenrollment.client.fallbacks

import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.springframework.stereotype.Component

@Component
class AccreditationClientFallback : AccreditationClient {
    override fun getStudyProgramSubjects(studyProgram: StudyProgram): List<SubjectCode> {
        val mockSubjects = when (studyProgram.studyProgram) {

            StudyProgram.Type.COMPUTER_SCIENCE.programName -> listOf(
                SubjectCode("F18L1W101"),
                SubjectCode("F18L2W102"),
                SubjectCode("F18L3W103"),
                SubjectCode("F18L3S201"),
                SubjectCode("F18L2S202"),
                SubjectCode("F18L3S203")
            )

            StudyProgram.Type.SOFTWARE_ENGINEERING.programName -> listOf(
                SubjectCode("F18L3W111"),
                SubjectCode("F18L3W112"),
                SubjectCode("F18L2W113"),
                SubjectCode("F18L3S211"),
                SubjectCode("F18L3S212"),
                SubjectCode("F18L1S213")
            )

            StudyProgram.Type.INFORMATION_TECHNOLOGIES.programName -> listOf(
                SubjectCode("F18L3W121"),
                SubjectCode("F18L3W122"),
                SubjectCode("F18L3W123"),
                SubjectCode("F18L3S221"),
                SubjectCode("F18L3S222"),
                SubjectCode("F18L3S223")
            )

            StudyProgram.Type.COMPUTER_ENGINEERING.programName -> listOf(
                SubjectCode("F18L3W131"),
                SubjectCode("F18L3W132"),
                SubjectCode("F18L2W133"),
                SubjectCode("F18L3S231"),
                SubjectCode("F18L3S232"),
                SubjectCode("F18L2S233")
            )

            StudyProgram.Type.CYBER_SECURITY.programName -> listOf(
                SubjectCode("F18L1W141"),
                SubjectCode("F18L3W142"),
                SubjectCode("F18L3W143"),
                SubjectCode("F18L2S241"),
                SubjectCode("F18L3S242"),
                SubjectCode("F18L3S243")
            )

            else -> emptyList()
        }

        return mockSubjects
    }
}
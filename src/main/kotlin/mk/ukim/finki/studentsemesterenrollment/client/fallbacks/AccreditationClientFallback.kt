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
                SubjectCode("F18L1W020"),
                SubjectCode("F18L1W033"),
                SubjectCode("F18L1W007"),
                SubjectCode("F18L1W031"),
                SubjectCode("F18L1W018"),

                SubjectCode("F18L1S032"),
                SubjectCode("F18L1S016"),
                SubjectCode("F18L1S003"),
                SubjectCode("F18L1S034"),
                SubjectCode("F18L1S146"),

                SubjectCode("F18L2W014"),
                SubjectCode("F18L2W006"),
                SubjectCode("F18L2W109"),
                SubjectCode("F18L2W001"),
                SubjectCode("F18L2W140"),

                SubjectCode("F18L2S029"),
                SubjectCode("F18L2S017"),
                SubjectCode("F18L2S030"),
                SubjectCode("F18L2S114"),
                SubjectCode("F18L2S110"),

                SubjectCode("F18L3W035"),
                SubjectCode("F18L3W037"),
                SubjectCode("F18L3W008"),
                SubjectCode("F18L3W024"),
                SubjectCode("F18L3W004"),

                SubjectCode("F18L3S118"),
                SubjectCode("F18L3S036"),
                SubjectCode("F18L3S010"),
                SubjectCode("F18L3S087"),
                SubjectCode("F18L3S039"),

                SubjectCode("F18L3W038"),
                SubjectCode("F18L3W075"),
                SubjectCode("F18L3W021"),
                SubjectCode("F18L3W074"),
                SubjectCode("F18L3W103"),

                SubjectCode("F18L3S168"),
                SubjectCode("F18L3S155"),
                SubjectCode("F18L3S159"),
                SubjectCode("F18L3S022"),
                SubjectCode("F18L3S086")
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
package mk.ukim.finki.studentsemesterenrollment.client.fallbacks

import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.springframework.stereotype.Component

@Component
class AccreditationClientFallback : AccreditationClient {
    private val logger = loggerFor<AccreditationClientFallback>()
    override fun getStudyProgramSubjects(studyProgram: StudyProgram): List<SubjectCode> {

        logger.debug("FALLBACK TRIGGERED for study program: ${studyProgram.studyProgram}")
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
                SubjectCode("F18L1S__1"), //elective

                SubjectCode("F18L2W014"),
                SubjectCode("F18L2W006"),
                SubjectCode("F18L2W001"),
                SubjectCode("F18L2W__1"),
                SubjectCode("F18L2W__2"),

                SubjectCode("F18L2S029"),
                SubjectCode("F18L2S017"),
                SubjectCode("F18L2S030"),
                SubjectCode("F18L2S__1"),
                SubjectCode("F18L2S__2"),

                SubjectCode("F18L3W035"),
                SubjectCode("F18L3W037"),
                SubjectCode("F18L3W__1"),
                SubjectCode("F18L3W__2"),
                SubjectCode("F18L3W004"),

                SubjectCode("F18L3S__1"),
                SubjectCode("F18L3S036"),
                SubjectCode("F18L3S010"),
                SubjectCode("F18L3S__2"),
                SubjectCode("F18L3S039"),

                SubjectCode("F18L3W038"),
                SubjectCode("F18L3W__3"),
                SubjectCode("F18L3W__4"), // elective
                SubjectCode("F18L3W__5"),
                SubjectCode("F18L3W__6"),

                SubjectCode("F18L3S168"),
                SubjectCode("F18L3S__3"),
                SubjectCode("F18L3S__4"),
                SubjectCode("F18L3S__5"),
                SubjectCode("F18L3S__6")
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
package mk.ukim.finki.studentsemesterenrollment.web

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgramType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mock/api/accreditations")
class MockAccreditationController {

    @GetMapping("/study-program-subjects/{studyProgram}")
    fun getStudyProgramSubjects(@PathVariable studyProgram: StudyProgramType): ResponseEntity<List<SubjectCode>> {
        val mockSubjects = when (studyProgram) {

            StudyProgramType.COMPUTER_SCIENCE -> listOf(
                SubjectCode("F18L1W101"),
                SubjectCode("F18L2W102"),
                SubjectCode("F18L3W103"),
                SubjectCode("F18L3S201"),
                SubjectCode("F18L2S202"),
                SubjectCode("F18L3S203")
            )

            StudyProgramType.SOFTWARE_ENGINEERING -> listOf(
                SubjectCode("F18L3W111"),
                SubjectCode("F18L3W112"),
                SubjectCode("F18L2W113"),
                SubjectCode("F18L3S211"),
                SubjectCode("F18L3S212"),
                SubjectCode("F18L1S213")
            )

            StudyProgramType.INFORMATION_TECHNOLOGIES -> listOf(
                SubjectCode("F18L3W121"),
                SubjectCode("F18L3W122"),
                SubjectCode("F18L3W123"),
                SubjectCode("F18L3S221"),
                SubjectCode("F18L3S222"),
                SubjectCode("F18L3S223")
            )

            StudyProgramType.COMPUTER_ENGINEERING -> listOf(
                SubjectCode("F18L3W131"),
                SubjectCode("F18L3W132"),
                SubjectCode("F18L2W133"),
                SubjectCode("F18L3S231"),
                SubjectCode("F18L3S232"),
                SubjectCode("F18L2S233")
            )

            StudyProgramType.CYBER_SECURITY -> listOf(
                SubjectCode("F18L1W141"),
                SubjectCode("F18L3W142"),
                SubjectCode("F18L3W143"),
                SubjectCode("F18L2S241"),
                SubjectCode("F18L3S242"),
                SubjectCode("F18L3S243")
            )
        }

        return ResponseEntity.ok(mockSubjects)
    }
}

package mk.ukim.finki.studentsemesterenrollment.client

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "accreditation-client", url = "http://localhost:8080/mock")
interface AccreditationClient {

    @GetMapping("/api/accreditations/study-program-subjects/{studyProgram}")
    fun getStudyProgramSubjects(@PathVariable studyProgram: StudyProgram): ResponseEntity<List<SubjectCode>>

}
package mk.ukim.finki.studentsemesterenrollment.web.request

data class ProvisionallyEnrollStudentOnSubjectRequest(
    val studentIndex: String,
    val semesterId: String,
    val cycleId: String,
    val subjectCode: String
)

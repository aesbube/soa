package mk.ukim.finki.studentsemesterenrollment.web.request

data class StartEnrollmentRequest(
    val studentIndex: String,
    val cycleId: String,
    val semesterId: String
)

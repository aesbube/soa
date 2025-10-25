package mk.ukim.finki.studentsemesterenrollment.valueObjects

data class StudyProgram(val studyProgram: String) {
    init {
        require(studyProgram.length in 4..254) {
            throw IllegalArgumentException("Invalid study program length: $studyProgram. It should be between 3 and 255 characters")
        }
        // it should be only with latin or cyrillic letters, numbers, and single spaces
        require(studyProgram.matches(Regex("^[a-zA-Zа-шА-Ш0-9 ]+$"))) {
            throw IllegalArgumentException("Invalid study program: $studyProgram. It should contain only latin or cyrillic letters, numbers, and single spaces")
        }
    }
}

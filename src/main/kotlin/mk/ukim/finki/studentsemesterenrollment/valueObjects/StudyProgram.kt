package mk.ukim.finki.studentsemesterenrollment.valueObjects

data class StudyProgram(val studyProgram: String) {
    init {
        require(studyProgram.length in 4..254) {
            throw IllegalArgumentException("Invalid study program length: $studyProgram. It should be between 3 and 255 characters. Found: '$studyProgram'")
        }
        // it should be only with latin or cyrillic letters, numbers, and single spaces
        require(studyProgram.matches(Regex("^[\\p{IsCyrillic}a-zA-Z0-9 ]+$"))) {
            throw IllegalArgumentException("Invalid study program: $studyProgram. It should contain only latin or cyrillic letters, numbers, and single spaces. Found: '$studyProgram'")
        }
    }
    enum class Type(val programName: String) {
        SOFTWARE_ENGINEERING("Софтверско инженерство и информациски системи"),
        INFORMATION_TECHNOLOGIES("Примена на информациски технологии"),
        COMPUTER_ENGINEERING("Компјутерско инженерство"),
        COMPUTER_SCIENCE("Компјутерски науки"),
        CYBER_SECURITY("Интернет мрежи и безбедност");

        fun toStudyProgram(): StudyProgram = StudyProgram(programName)
    }
}
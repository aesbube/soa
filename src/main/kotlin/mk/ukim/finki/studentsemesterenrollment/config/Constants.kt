package mk.ukim.finki.studentsemesterenrollment.config

import mk.ukim.finki.studentsemesterenrollment.client.SemesterDto
import mk.ukim.finki.studentsemesterenrollment.model.dto.ClassesPerWeekData
import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import java.time.ZonedDateTime

object Constants {
    val semesters = listOf(
        SemesterDto(
            semesterId = SemesterId("2021-22-W"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2021-09-25T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2021-09-25T00:00:00.000Z").plusDays(21)

        ),
        SemesterDto(
            semesterId = SemesterId("2021-22-S"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2022-02-01T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2022-02-01T00:00:00.000Z").plusDays(21)

        ),

        SemesterDto(
            semesterId = SemesterId("2022-23-W"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2022-09-25T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2022-09-25T00:00:00.000Z").plusDays(21)

        ),
        SemesterDto(
            semesterId = SemesterId("2022-23-S"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2023-02-01T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2023-02-01T00:00:00.000Z").plusDays(21)

        ),

        SemesterDto(
            semesterId = SemesterId("2023-24-W"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2023-09-25T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2023-09-25T00:00:00.000Z").plusDays(21)

        ),
        SemesterDto(
            semesterId = SemesterId("2023-24-S"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2024-02-01T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2024-02-01T00:00:00.000Z").plusDays(21)

        ),

        SemesterDto(
            semesterId = SemesterId("2024-25-W"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2024-09-25T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2024-09-25T00:00:00.000Z").plusDays(21)

        ),
        SemesterDto(
            semesterId = SemesterId("2024-25-S"),
            cycleId = StudyCycle.UNDERGRADUATE,
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.parse("2025-02-01T00:00:00.000Z"),
            enrollmentEndDate = ZonedDateTime.parse("2025-02-01T00:00:00.000Z").plusDays(21)
        )
    )
    val subjects = listOf(
        SubjectEventData(
            id = "F18L1W004",
            name = "Спорт и здравје",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 0,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S002",
            name = "Архитектура и организација на компјутери ПИТ Верзија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 0,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L5W001",
            name = "Вовед во роботика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 0,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L8W001",
            name = "Напредна роботика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 0,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W134",
            name = "Мултимедиски мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 3
            )
        ),
        SubjectEventData(
            id = "F18L3W160",
            name = "Софтверски дефинирани мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W074",
            name = "Администрација на бази податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W115",
            name = "Компјутерски звук, музика и говор",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S118",
            name = "Континуирана интеграција и испорака",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W011",
            name = "Дискретна математика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W136",
            name = "Напреден веб дизајн",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W007",
            name = "Вовед во компјутерските науки",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W018",
            name = "Професионални вештини",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 4,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W005",
            name = "Бизнис и менаџмент",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W001",
            name = "Алгоритми и податочни структури",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W014",
            name = "Компјутерски мрежи и безбедност",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W021",
            name = "Тимски проект",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 0,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W008",
            name = "Вовед во науката за податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W004",
            name = "Бази на податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W024",
            name = "Веб програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S095",
            name = "Дигитално процесирање на слика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S003",
            name = "Архитектура и организација на компјутери",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S023",
            name = "Бизнис статистика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S026",
            name = "Маркетинг",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S029",
            name = "Софтверско инженерство",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S012",
            name = "Интегрирани системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S025",
            name = "Електронска и мобилна трговија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S010",
            name = "Дизајн на интеракцијата човек-компјутер",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S168",
            name = "Дипломска работа",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W020",
            name = "Структурно програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W006",
            name = "Веројатност и статистика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S016",
            name = "Објектно-ориентирано програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S013",
            name = "Калкулус",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S019",
            name = "Софтверски квалитет и тестирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W046",
            name = "Компјутерски мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S063",
            name = "Дизајн на компјутерски мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W067",
            name = "Основи на теоријата на информации",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W065",
            name = "Мрежна безбедност",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W060",
            name = "Администрација на системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W068",
            name = "Пресметување во облак",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W064",
            name = "Дистрибуирани системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S045",
            name = "Компјутерски архитектури",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S066",
            name = "Основи на сајбер безбедноста",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S061",
            name = "Безжични и мобилни системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S059",
            name = "Администрација на мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S062",
            name = "Виртуелизација",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W031",
            name = "Дискретни структури 1",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W033",
            name = "Калкулус 1",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S039",
            name = "Формални јазици и автомати",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W038",
            name = "Програмски парадигми",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W037",
            name = "Паралелно и дистрибуирано процесирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S032",
            name = "Дискретни структури 2",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S034",
            name = "Калкулус 2",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S030",
            name = "Вештачка интелигенција",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W035",
            name = "Линеарна алгебра и примени",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S036",
            name = "Машинско учење",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W041",
            name = "Дизајн на дигитални кола",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W049",
            name = "Физика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 2,
                auditoriumClasses = 3,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S042",
            name = "Електрични кола",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W048",
            name = "Софтвер за вградливи системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W043",
            name = "Информациска безбедност",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S047",
            name = "Процесирање на сигналите",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S040",
            name = "Вградливи микропроцесорски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S052",
            name = "ИТ системи за учење",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W050",
            name = "Дизајн на образовен софтвер",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1W070",
            name = "Педагогија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S083",
            name = "Виртуелна реалност",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W055",
            name = "Мултимедијални технологии",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W058",
            name = "Самостоен проект",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S051",
            name = "ИКТ во образованието",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S071",
            name = "Психологија на училишна возраст",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W109",
            name = "Интернет програмирање на клиентска страна",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S057",
            name = "Работа со надарени ученици",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S056",
            name = "Персонализирано учење",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S054",
            name = "Методика на информатиката",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S069",
            name = "Македонски јазик",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S146",
            name = "Основи на Веб дизајн",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S113",
            name = "Компјутерска анимација",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W081",
            name = "Визуелизација",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S114",
            name = "Компјутерска графика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W167",
            name = "Шаблони за дизајн на кориснички интерфејси",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W152",
            name = "Програмирање на видео игри",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W108",
            name = "Интернет на нештата",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S135",
            name = "Мултимедиски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W165",
            name = "Управување со техничка поддршка",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S101",
            name = "Етичко хакирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S120",
            name = "Креативни вештини за решавање проблеми",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W133",
            name = "Мрежна и мобилна форензика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W075",
            name = "Анализа и дизајн на ИС",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S124",
            name = "Медиуми и комуникации",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S028",
            name = "Претприемништво",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W103",
            name = "Имплементација на софтверски системи со слободен и отворен код",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S164",
            name = "Теорија на информации со дигитални комуникации",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S127",
            name = "Мобилни апликации",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W117",
            name = "Компјутерски поддржано производство",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S091",
            name = "Географски информациски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S116",
            name = "Компјутерски компоненти",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S099",
            name = "Е-влада",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S100",
            name = "Економија за ИКТ инженери",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W131",
            name = "Моделирање и симулација",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S130",
            name = "Моделирање и менаџирање на бизнис процеси",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S125",
            name = "Мерење и анализа на интернет сообраќај",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W129",
            name = "Мобилни платформи и програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W079",
            name = "Веб базирани системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W140",
            name = "Напредно програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 4,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S017",
            name = "Оперативни системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W154",
            name = "Рударење на масивни податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 2,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S082",
            name = "Визуелно програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W126",
            name = "Методологија на истражувањето во ИКТ",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W137",
            name = "Напредна интеракција човек компјутер",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S132",
            name = "Модерни трендови во роботика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W044",
            name = "Компјутерска електроника",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W098",
            name = "Дистрибуирано складирање на податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S107",
            name = "Интелигентни системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S093",
            name = "Дигитална форензика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S090",
            name = "Вовед во случајни процеси",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 2,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W145",
            name = "Оптички мрежи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S119",
            name = "Концепти на информатичко општество",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S149",
            name = "Паралелно програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S122",
            name = "Криптографија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W104",
            name = "Инженерска математика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S077",
            name = "Безжични мултимедиски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S141",
            name = "Неструктурирани бази на податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L1S015",
            name = "Објектно ориентирана анализа и дизајн",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S087",
            name = "Вовед во мрежна наука",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W089",
            name = "Вовед во препознавање на облици",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S111",
            name = "Инфраструктурно програмирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S155",
            name = "Сервисно ориентирани архитектури",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S110",
            name = "Интернет технологии",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W105",
            name = "Иновации во ИКТ",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S094",
            name = "Дигитални библиотеки",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W161",
            name = "Социјални мрежи и медиуми",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S121",
            name = "Блоковски вериги и критовалути",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S139",
            name = "Напредни теми од криптографија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S106",
            name = "Интелигентни информациски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W142",
            name = "Обработка на природните јазици",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S097",
            name = "Дизајн на алгоритми",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W096",
            name = "Дигитизација",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2W147",
            name = "Основи на комуникациски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S084",
            name = "Вовед во екоинформатиката",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W144",
            name = "Операциони истражувања",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S151",
            name = "Пресметковна биологија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S112",
            name = "Програмски јазици и компајлери",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S102",
            name = "ИКТ за развој",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S163",
            name = "Статистичко моделирање",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S080",
            name = "Веб пребарувачки системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W092",
            name = "Дигитална постпродукција",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S138",
            name = "Напредни бази на податоци",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S157",
            name = "Складови на податоци и аналитичка обработка",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W148",
            name = "Основи на роботиката",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S153",
            name = "Процесна роботика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W072",
            name = "Автономна роботика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W128",
            name = "Мобилни информациски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S162",
            name = "Споделување и пресметување во толпа",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W053",
            name = "Компјутерска етика",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 0,
                auditoriumClasses = 4,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W156",
            name = "Системи за поддршка при одлучувањето",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W076",
            name = "Вовед во анализа на временските серии",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W088",
            name = "Вовед во паметни градови",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S150",
            name = "Податочно рударство",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S159",
            name = "Софтверски дефинирана безбедност",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L2S002",
            name = "Анализа на софтверските барања",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W009",
            name = "Дизајн и архитектура на софтвер",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S022",
            name = "Управување со ИКТ проекти",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W027",
            name = "Менаџмент информациски системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S086",
            name = "Вовед во когнитивни науки",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3W123",
            name = "Машинска визија",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "WINTER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S166",
            name = "Учење на далечина",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S078",
            name = "Биолошки инспирирано пресметување",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S073",
            name = "Агентно-базирани системи",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        ),
        SubjectEventData(
            id = "F18L3S158",
            name = "Современи компјутерски архитектури",
            abbreviation = "NULL",
            semesterCode = "2025-26-W",
            ects = 6,
            semester = "SUMMER",
            classesPerWeek = ClassesPerWeekData(
                lectures = 3,
                auditoriumClasses = 2,
                labClasses = 0
            )
        )
    )
}
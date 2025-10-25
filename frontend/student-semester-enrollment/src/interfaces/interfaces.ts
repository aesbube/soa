// treba povrzuvanje so cas za lichni podatoci
export interface DashboardData {
    message: string;
}

export interface Subject {
    code: string;
    name: string;
    credits: number;
}

export interface PassedSubject {
    code: string;
    name: string;
    credits: number;
    grade: number;
}

export interface Semester {
    semesterCode: string; // YYYY-YY-W/S
    year: number;
    term: 'Spring' | 'Fall';
}

export interface EnrollSemesterRequest {
    semesterCode: string; // YYYY-YY-W/S
}

export interface EnrollSubjectsRequest {
    semesterCode: string; // YYYY-YY-W/S
    subjects: string[]; // array of subject codes
}

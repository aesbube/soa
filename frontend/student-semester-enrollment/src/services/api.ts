import type {
    DashboardData,
    Subject,
    Semester,
    EnrollSemesterRequest,
    EnrollSubjectsRequest, PassedSubject
} from '../interfaces/interfaces';

const API_BASE = '/api';

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        headers: {
            'Content-Type': 'application/json',
            ...(options.headers || {})
        },
        ...options
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
}

export const api = {
    getSubjectsBySemester: (semesterCode: string) => request<Subject[]>('/subjects?semesterCode=' + semesterCode),

    getAllSemesters: () => request<Semester[]>('/semesters'),

    getDashboard: () => request<DashboardData>('/dashboard'),

    getEnrolledSubjectsForSemester: (semesterCode: string) => request<Subject[]>('/enrolled-subjects?semesterCode=' + semesterCode),

    getEnrolledSemesters: () => request<Semester[]>('/enrolled-semesters'),

    getPassedSubjects:() => request<PassedSubject[]>('/passed-subjects'),

    enrollSemester: (payload: EnrollSemesterRequest) =>
        request<void>('/enroll-semester', {
            method: 'POST',
            body: JSON.stringify(payload)
        }),

    enrollSubjects: (payload: EnrollSubjectsRequest) =>
        request<void>('/enroll-subjects', {
            method: 'POST',
            body: JSON.stringify(payload)
        })
};

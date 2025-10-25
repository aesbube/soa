import { useEffect, useState } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { api } from '../services/api.ts';
import type { Semester, Subject } from '../interfaces/interfaces.ts';

interface Props {
    selectedSemester: Semester | null;
}

export default function EnrolledSubjects({ selectedSemester }: Props) {
    const [subjects, setSubjects] = useState<Subject[]>([]);
    useEffect(() => {
        if (!selectedSemester) {
            setSubjects([]);
            return;
        }
        api.getEnrolledSubjectsForSemester(selectedSemester.semesterCode)
            .then(setSubjects)
            .catch(() => {
                setSubjects([
                    { code: "CS101", name: "Intro to CS", credits: 6 },
                    { code: "MA201", name: "Linear Algebra", credits: 5 },
                ]);
            });
    }, [selectedSemester]);

    return (
        <>
            <h2>Enrolled Subjects</h2>
            <DataTable value={subjects}>
                <Column field="code" header="Code" />
                <Column field="name" header="Name" />
                <Column field="credits" header="Credits" />
            </DataTable>
        </>
    );
}

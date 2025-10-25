import { useEffect, useState } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { api } from '../services/api.ts';
import type { PassedSubject } from '../interfaces/interfaces.ts';

export default function PassedSubjects() {
    const [subjects, setSubjects] = useState<PassedSubject[]>([]);

    useEffect(() => {
        api.getPassedSubjects()
            .then(setSubjects)
            .catch(() => {
                setSubjects([
                    { code: 'CS101', name: 'Intro to CS', credits: 6 , grade: 9 },
                    { code: 'MA201', name: 'Linear Algebra', credits: 5 , grade: 10}
                ]);
            });
    }, []);

    return (
        <>
            <h2>Passed Subjects</h2>
            <DataTable value={subjects}>
                <Column field="code" header="Code" />
                <Column field="name" header="Name" />
                <Column field="credits" header="Credits" />
                <Column field="grade" header="Grade" />
            </DataTable>
        </>
    );
}

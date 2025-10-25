import { useEffect, useState } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { api } from '../services/api.ts';
import type {Semester} from '../interfaces/interfaces.ts';

export default function EnrolledSemesters() {
    const [semesters, setSemesters] = useState<Semester[]>([]);

    useEffect(() => {
        api.getEnrolledSemesters()
            .then(setSemesters)
            .catch(() => {
                setSemesters([
                    { semesterCode: '1', year: 2025, term: 'Spring' },
                    { semesterCode: '2', year: 2025, term: 'Fall' }
                ]);
            });
    }, []);

    return (
        <>
            <h2>Enrolled Semesters</h2>
            <DataTable value={semesters}>
                <Column field="id" header="ID" />
                <Column field="year" header="Year" />
                <Column field="term" header="Term" />
            </DataTable>
        </>
    );
}

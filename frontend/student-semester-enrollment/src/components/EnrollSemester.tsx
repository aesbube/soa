import {useEffect, useState} from 'react';
import { api } from '../services/api.ts';
import { Button } from 'primereact/button';
import type { EnrollSemesterRequest, Semester } from '../interfaces/interfaces.ts';
import { Dropdown } from "primereact/dropdown";

export default function EnrollSemester() {
    const [form, setForm] = useState<EnrollSemesterRequest>({ semesterCode: '' });
    const [allSemesters, setAllSemesters] = useState<Semester[]>([]);

    useEffect(() => {
        api.getAllSemesters()
            .then((data) => setAllSemesters(data))
            .catch(() => console.log('Failed to load semesters'));
    }, []);

    const handleSemestersChange = (e: { value: string }) => {
        setForm({ semesterCode: e.value });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        api.enrollSemester(form)
            .then(() => console.log('Semester enrolled successfully'))
            .catch((err) => console.log('Error: ' + err.message));
    };

    return (
        <form onSubmit={handleSubmit}>
            <h3>Enroll a Semester</h3>
            <Dropdown
                value={form.semesterCode}
                options={allSemesters}
                onChange={handleSemestersChange}
                placeholder="Select Semester"
                className="w-full"
            />
            <div className="mt-3">
                <Button label="Enroll" icon="pi pi-check" type="submit" />
            </div>
        </form>
    );
}

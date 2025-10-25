import { useEffect, useState } from "react";
import { api } from "../services/api.ts";
import { MultiSelect } from "primereact/multiselect";
import { Dropdown } from "primereact/dropdown";
import { Button } from "primereact/button";
import type { EnrollSubjectsRequest, Subject, Semester } from "../interfaces/interfaces.ts";

interface Props {
    selectedSemester: Semester | null;
    setSelectedSemester: (semester: Semester | null) => void;
}

export default function EnrollSubjects({ selectedSemester, setSelectedSemester }: Props) {
    const [form, setForm] = useState<EnrollSubjectsRequest>({ semesterCode: '', subjects: [] });
    const [allSubjects, setAllSubjects] = useState<Subject[]>([]);
    const [allSemesters, setAllSemesters] = useState<Semester[]>([]);

    useEffect(() => {
        api.getAllSemesters()
            .then(setAllSemesters)
            .catch(() => console.log("Failed to load semesters"));
    }, []);

    useEffect(() => {
        if (!selectedSemester) {
            setAllSubjects([]);
            setForm({ semesterCode: '', subjects: [] });
            return;
        }
        api.getSubjectsBySemester(selectedSemester.semesterCode)
            .then(setAllSubjects)
            .catch(() => console.log("Failed to load subjects"));
    }, [selectedSemester]);

    const handleSemesterChange = (e: { value: Semester }) => {
        setSelectedSemester(e.value);
    };

    const handleSubjectsChange = (e: { value: string[] }) => {
        setForm((prev) => ({
            ...prev,
            subjects: e.value,
            semesterCode: selectedSemester ? selectedSemester.semesterCode : '',
        }));
    };


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (!selectedSemester) {
            alert("Please select a semester first.");
            return;
        }
        api.enrollSubjects({ ...form, semesterCode: selectedSemester.semesterCode })
            .then(() => console.log("Subjects enrolled successfully"))
            .catch((err) => console.log("Error: " + err.message));
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Enroll Subjects</h2>
            <Dropdown
                value={selectedSemester}
                options={allSemesters}
                onChange={handleSemesterChange}
                optionLabel="name"
                placeholder="Select Semester"
                className="w-full mb-3"
            />
            <MultiSelect
                value={form.subjects}
                options={allSubjects}
                onChange={handleSubjectsChange}
                placeholder={selectedSemester ? "Select Subjects" : "Select a semester first"}
                display="chip"
                className="w-full"
                disabled={!selectedSemester}
                optionLabel="name"
                optionValue="code"
            />
            <div className="mt-3">
                <Button label="Enroll" icon="pi pi-check" type="submit" disabled={!selectedSemester} />
            </div>
        </form>
    );
}

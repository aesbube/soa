import EnrollSubjects from "../components/EnrollSubjects.tsx";
import EnrolledSubjects from "../components/EnrolledSubjects.tsx";
import { useState } from "react";
import type {Semester} from "../interfaces/interfaces.ts";

export default function Subjects() {
    const [selectedSemester, setSelectedSemester] = useState<Semester | null>(null);

    return (
        <>
            <EnrollSubjects selectedSemester={selectedSemester} setSelectedSemester={setSelectedSemester} />
            <EnrolledSubjects selectedSemester={selectedSemester} />
        </>
    );
}
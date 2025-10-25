import { Routes, Route, useNavigate } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import { Menubar } from 'primereact/menubar';
import { useState } from 'react';
import { Sidebar } from 'primereact/sidebar';
import { Button } from 'primereact/button';
import PassedSubjects from "./components/PassedSubjects.tsx";
import Semesters from "./pages/Semesters.tsx";
import Subjects from "./pages/Subjects.tsx";

export default function App() {
    const navigate = useNavigate();
    const [visible, setVisible] = useState(false);

    const menuItems = [
        { label: 'Dashboard', icon: 'pi pi-home', command: () => navigate('/') },
        { label: 'Passed Subjects', icon: 'pi pi-check', command: () => navigate('/passed-subjects') },
        { label: 'Subjects', icon: 'pi pi-book', command: () => navigate('/subjects') },
        { label: 'Semesters', icon: 'pi pi-calendar', command: () => navigate('/semesters') },
    ];

    const menubarStart = (
        <Button icon="pi pi-bars" onClick={() => setVisible(true)} text rounded />
    );

    return (
        <div className="app-layout">
            {/* Top bar */}
            <Menubar start={menubarStart} model={[]} />

            {/* Sidebar */}
            <Sidebar visible={visible} onHide={() => setVisible(false)} position="left">
                <h3>Student Portal</h3>
                <ul className="sidebar-menu">
                    {menuItems.map((item, idx) => (
                        <li key={idx} onClick={() => { item.command?.(); setVisible(false); }}>
                            <i className={item.icon} style={{ marginRight: '8px' }}></i>
                            {item.label}
                        </li>
                    ))}
                </ul>
            </Sidebar>

            {/* Page content */}
            <div className="page-content p-4">
                <Routes>
                    <Route path="/" element={<Dashboard />} />
                    <Route path="/passed-subjects" element={<PassedSubjects />} />
                    <Route path="/subjects" element={<Subjects />} />
                    <Route path="/semesters" element={<Semesters />} />
                </Routes>
            </div>
        </div>
    );
}

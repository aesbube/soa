import { useEffect, useState } from 'react';
import { api } from '../services/api';
import type {DashboardData} from '../interfaces/interfaces';

export default function Dashboard() {
    const [data, setData] = useState<DashboardData | null>(null);

    useEffect(() => {
        api.getDashboard()
            .then(setData)
            .catch(() => setData({ message: 'Welcome to your dashboard!' }));
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            <p>{data?.message}</p>
        </div>
    );
}

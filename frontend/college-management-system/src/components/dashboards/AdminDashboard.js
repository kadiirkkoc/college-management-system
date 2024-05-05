import React from 'react';
import { useNavigate } from 'react-router-dom';

function AdminDashboard() {
    const navigate = useNavigate();

    const handleNavigate = (path) => {
        navigate(path);
    };

    return (
        <div className="dashboard-container">
            <h1>Admin Dashboard</h1>
            <p>Welcome to the Admin Dashboard. {} Here you can manage faculty, departments, instructors, lessons, and students.</p>
            <button onClick={() => handleNavigate('/faculty-dashboard')}>Manage Faculty</button>
            <button onClick={() => handleNavigate('/department-dashboard')}>Manage Department</button>
        </div>
    );
}


export default AdminDashboard;

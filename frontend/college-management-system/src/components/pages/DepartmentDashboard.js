import React, { useState, useEffect } from 'react';

function DepartmentDashboard() {
    const [departments, setDepartments] = useState([]);
    const [formData, setFormData] = useState({ name: '', location: '' });
    const [editId, setEditId] = useState(null);

    useEffect(() => {
        fetchDepartments();
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const fetchDepartments = async () => {
        try {
            const response = await fetch('http://localhost:8087/api/department');
            const data = await response.json();
            if (response.ok) {
                setDepartments(data);
            } else {
                throw new Error('Failed to fetch departments');
            }
        } catch (error) {
            console.error('Fetch error:', error);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (editId === null) {
            await addDepartment();
        } else {
            await updateDepartment(editId);
        }
    };

    const addDepartment = async () => {
        try {
            const response = await fetch('http://localhost:8087/api/department', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                fetchDepartments();
            } else {
                throw new Error('Failed to add new department');
            }
        } catch (error) {
            console.error('Error adding department:', error);
        }
    };

    const updateDepartment = async (id) => {
        try {
            const response = await fetch(`http://localhost:8087/api/department/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                fetchDepartments();
                setEditId(null);
                setFormData({ name: '', location: '' });
            } else {
                throw new Error('Failed to update department');
            }
        } catch (error) {
            console.error('Error updating department:', error);
        }
    };

    const deleteDepartment = async (id) => {
        try {
            const response = await fetch(`http://localhost:8087/api/department/${id}`, {
                method: 'DELETE'
            });
            if (response.ok) {
                fetchDepartments();
            } else {
                throw new Error('Failed to delete department');
            }
        } catch (error) {
            console.error('Error deleting department:', error);
        }
    };

    return (
        <div className="department-page">
            <form onSubmit={handleSubmit} className="department-form">
                <input type="text" name="name" value={formData.name} onChange={handleInputChange} placeholder="Department Name" className="department-input" />
                <input type="text" name="location" value={formData.location} onChange={handleInputChange} placeholder="Location" className="department-input" />
                <button type="submit" className="department-submit">{editId ? 'Update Department' : 'Add Department'}</button>
            </form>
            {departments.map(department => (
                <div key={department.id} className="department-card">
                    <div className="department-header">
                        <h2>{department.name}</h2>
                        <h3>{department.location}</h3>
                        <button onClick={() => { setEditId(department.id); setFormData({ name: department.name, location: department.location }); }} className="department-edit">Edit</button>
                        <button onClick={() => deleteDepartment(department.id)} className="department-delete">Delete</button>
                    </div>
                </div>
            ))}
        </div>
    );
}

export default DepartmentDashboard;

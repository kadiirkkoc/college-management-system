    import React, { useState, useEffect } from 'react';
    import '../../style/styles.css'; 

    
    function DepartmentDashboard() {
        const [departments, setDepartments] = useState([]);
        const [formData, setFormData] = useState({ name: '', facultyId: '' });
        const [editMode, setEditMode] = useState({ id: null, isEditing: false });

        useEffect(() => {
            fetchDepartments();
        }, []);

        const handleInputChange = (event) => {
            const { name, value } = event.target;
            setFormData(prev => ({ ...prev, [name]: value }));
        };
        
        const fetchDepartments = async () => {
            try {
                const response = await fetch('http://localhost:8087/department' ,{
                    method: 'GET',
                    headers: {
                                'Authorization': `Bearer ${localStorage.getItem('token')}`
                        }
                });
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
            if (editMode.id) {
                await updateDepartment(editMode.id);
            } else {
                await addDepartment();
            }
        };
        

        const handleEdit = (department) => {
            if (editMode.isEditing && editMode.id === department.id) {
                // Save the changes
                updateDepartment(department .id);
                setEditMode({ id: null, isEditing: false });
            } else {
                // Enable edit mode and set form data
                setFormData({ name: department.name, facultyId: formData.facultyId });
                setEditMode({ id: department.id, isEditing: true });
            }
        };

        const addDepartment = async () => {
            try {
                const response = await fetch('http://localhost:8087/department', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${localStorage.getItem('token')}` // Ensure token is correctly retrieved and valid
                    },
                    body: JSON.stringify(formData)
                });
                if (response.ok) {
                    fetchDepartments();
                    setFormData({ name: '', facultyId: '' }); 
                } else {
                    throw new Error('Failed to add new department');
                }
            } catch (error) {
                console.error('Error adding department:', error);
            }
        };
        

        const updateDepartment = async (id) => {
            try {
                const response = await fetch(`http://localhost:8087/department/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(formData)
                });
                if (response.ok) {
                    fetchDepartments();
                    setEditMode({ id: null, isEditing: false });  // Make sure this is correctly resetting the state
                    setFormData({ name: '', facultyId: '' });  // Clear form data
                } else {
                    throw new Error('Failed to update department');
                }
            } catch (error) {
                console.error('Error updating department:', error);
            }
        };
        

        const deleteDepartment = async (id) => {
            try {
                const response = await fetch(`http://localhost:8087/department/${id}`, {
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
                    <input type="text" name="facultyId" value={formData.facultyId} onChange={handleInputChange} placeholder="Faculty" className="department-input" />
                    <button type="submit" className="department-submit">{editMode.id ? 'Update Department' : 'Add Department'}</button>
                </form>
                {departments.map(department => (
    <div key={department.id} className="department-card">
        <div className="department-header">
            {editMode.isEditing && editMode.id === department.id ? (
                <>
                    <input
                        type="text"
                        value={formData.name}
                        name="name"
                        onChange={handleInputChange}
                        className="department-input"
                    />
                    <input
                        type="text"
                        value={formData.facultyId}
                        name="facultyId"
                        onChange={handleInputChange}
                        className="department-input"
                    />
                </>
            ) : (
                <>
                    <h2>{department.name}</h2>
                    <h3>{department.facultyId}</h3>
                </>
            )}
            <button onClick={() => handleEdit(department)} className="department-edit">
                {editMode.id === department.id  ? 'Save' : 'Edit'}
            </button>
            <button onClick={() => deleteDepartment(department.id)} className="department-delete">Delete</button>
        </div>
    </div>
))}

            </div>
        );
    }

    export default DepartmentDashboard;

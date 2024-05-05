import React, { useState, useEffect } from 'react';
import '../../style/styles.css'; 
function FacultyDashboard() {
    const [faculties, setFaculties] = useState([]);
    const [formData, setFormData] = useState({ name: '', campus: '' });
    const [editMode, setEditMode] = useState({ id: null, isEditing: false });

    useEffect(() => {
        fetchFaculties();
    }, []);

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const fetchFaculties = async () => {
        try {
            const response = await fetch('http://localhost:8087/faculty' , {
                method: 'GET',
                headers: {
                            'Authorization': `Bearer ${localStorage.getItem('token')}`
                    }
            });
            const data = await response.json();
            if (response.ok) {
                setFaculties(data);
            } else {
                throw new Error('Failed to fetch faculties');
            }
        } catch (error) {
            console.error('Fetch error:', error);
        }
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (editMode.id) {
            await updateFaculty(editMode.id);
        } else {
            await addFaculty();
        }
    };
    
    
    const handleEdit = (faculty) => {
        if (editMode.isEditing && editMode.id === faculty.id) {
            // Save the changes
            updateFaculty(faculty.id);
            setEditMode({ id: null, isEditing: false });
        } else {
            // Enable edit mode and set form data
            setFormData({ name: faculty.name, campus: faculty.campus });
            setEditMode({ id: faculty.id, isEditing: true });
        }
    };
    
    

    const addFaculty = async () => {
        try {
            const response = await fetch('http://localhost:8087/faculty', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                await fetchFaculties();
                setFormData({ name: '', campus: '' }); 
            } else {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to add new faculty');
            }
        } catch (error) {
            console.error('Error adding faculty:', error);
        }
    };

    const updateFaculty = async (id) => {
        try {
            const response = await fetch(`http://localhost:8087/faculty/${id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(formData)
            });
            if (response.ok) {
                fetchFaculties();
                setEditMode({ id: null, isEditing: false });
                setFormData({ name: '', campus: '' });
            } else {
                const errorData = await response.text();  
                throw new Error(errorData || 'Failed to update faculty');
            }
        } catch (error) {
            console.error('Error updating faculty:', error);
        }
    };
    
        

    const deleteFaculty = async (id) => {
        if (!id) {
            console.error('Delete operation aborted: No ID provided');
            return; 
        }
    
        console.log("Attempting to delete faculty with ID:", id);  
    
        try {
            const response = await fetch(`http://localhost:8087/faculty/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`  
                }
            });
            if (response.ok) {
                fetchFaculties();  
            } else {
                throw new Error('Failed to delete faculty');
            }
        } catch (error) {
            console.error('Error deleting faculty:', error);
        }
    };
    
    

    return (
        <div className="faculty-page">
            <form onSubmit={handleSubmit} className="faculty-form">
                <input type="text" name="name" value={formData.name} onChange={handleInputChange} placeholder="Faculty Name" className="faculty-input" />
                <input type="text" name="campus" value={formData.campus} onChange={handleInputChange} placeholder="Campus" className="faculty-input" />
                <button type="submit" className="faculty-submit">
                        {editMode.id ? 'Update Faculty' : 'Add Faculty'}
                </button>
            </form>
            {faculties.map(faculty => (
    <div key={faculty.id} className="faculty-card">
        <div className="faculty-header">
            {editMode.isEditing && editMode.id === faculty.id ? (
                <>
                    <input 
                        type="text"
                        value={formData.name}
                        name="name"
                        onChange={handleInputChange}
                        className="faculty-input"
                    />
                    <input 
                        type="text"
                        value={formData.campus}
                        name="campus"
                        onChange={handleInputChange}
                        className="faculty-input"
                    />
                </>
            ) : (
                <>
                    <h2>{faculty.name}</h2>
                    <h3>{faculty.campus}</h3>
                </>
            )}
            <button onClick={() => handleEdit(faculty)} className="faculty-edit">{editMode.id === faculty.id ? 'Save' : 'Edit'}</button>
            <button onClick={() => deleteFaculty(faculty.id)} className="faculty-delete">Delete</button>
        </div>
    </div>
))}

        </div>
    );
}

export default FacultyDashboard;

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import '../../style/styles.css'; 

function SignIn() {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });

    const [error, setError] = useState('');

    const navigate = useNavigate();

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8087/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            const data = await response.json();
            if (response.ok) {
                console.log('Login successful:', data);
                localStorage.setItem('token', data.token);  
                localStorage.setItem('userRole', data.userRole);  
    
                switch (data.userRole) {
                    case 'ADMIN':
                        navigate('/admin');
                        break;
                    case 'INSTRUCTOR':
                        navigate('/instructor');
                        break;
                    case 'STUDENT':
                        navigate('/student');
                        break;
                    default:
                        setError('Unauthorized access');
                        break;
                }
            } else {
                throw new Error(data.message || 'Failed to log in');
            }
        } catch (error) {
            console.error('Login error:', error);
            setError('Error: ' + error.message); 
        }
    };
    

    return (
        <div className="signin-container">
            <form onSubmit={handleSubmit} className="signin-form">
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Email"
                    className="email-input"
                />
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Password"
                    className="password-input"
                />
                <button type="submit" className="signin-button">Sign In</button>
            </form>
            {error && <p className="error-message">{error}</p>} {error.message}
        </div>
    );
}

export default SignIn;

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 
import '../../style/styles.css';

function SignUp() {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        userRole: 'ADMIN'
    });

    const [message, setMessage] = useState('');
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
        setMessage('');
        try {
            const response = await fetch('http://localhost:8087/auth/registration', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            const data = await response.json();
            if (response.ok) {
                setMessage('Registration successful! Welcome, ' + data.firstName + '!');
                console.log('Registration successful:', data);
                navigate('/signin');
            } else {
                throw new Error(data.message || 'Failed to register');
            }
        } catch (error) {
            console.error('Registration error:', error);
            setMessage('Error: ' + error.message);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit} className="signup-form">
                <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="First Name"
                    className="input-field"
                />
                <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Last Name"
                    className="input-field"
                />
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Email"
                    className="input-field"
                />
                <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Username"
                    className="input-field"
                />
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Password"
                    className="input-field"
                />
                <button type="submit" className="submit-button">Sign Up</button>
            </form>
            {message && <p className="message">{message}</p>} 
        </div>
    );
}

export default SignUp;

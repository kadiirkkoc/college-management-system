import React, { useState } from 'react';
import './/styles.css';  // Ensure the styles.css is correctly linked

function SignIn() {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });

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
            const response = await fetch('http://localhost:8087/auth/login', {  // Update your endpoint accordingly
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });
            const data = await response.json();
            if (response.ok) {
                console.log('Login successful:', data);
                // Handle successful login here
            } else {
                throw new Error(data.message || 'Failed to login');
            }
        } catch (error) {
            console.error('Login error:', error);
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
        </div>
    );
}

export default SignIn;

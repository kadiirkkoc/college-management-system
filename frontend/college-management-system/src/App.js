import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignIn from './components/login/SignIn';
import SignUp from './components/login/SignUp';
import AdminDashboard from './components/dashboards/AdminDashboard';
import StudentDashboard from './components/dashboards/StudentDashboard';
import InstructorDashboard from './components/dashboards/InstructorDashboard';
import FacultyDashboard from './components/pages/FacultyDashboard';



function App() {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          <Route path="/signup" element={<SignUp />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/admin-dashboard" element={<AdminDashboard />} />
          <Route path="/faculty-dashboard" element={<FacultyDashboard />} />
          <Route path="/student-dashboard" element={<StudentDashboard />} />
          <Route path="/instructor-dashboard" element={<InstructorDashboard />} />
          
        </Routes>
      </div>
    </Router>
  );
}

export default App;

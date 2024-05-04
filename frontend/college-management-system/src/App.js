import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import SignIn from './components/login/SignIn';
import SignUp from './components/login/SignUp';
import AdminMainPage from './components/main-pages/AdminMainPage';
import InstructorMainPage from './components/main-pages/InstructorMainPage';
import StudentMainPage from './components/main-pages/StudentMainPage';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Routes>
          <Route path="/signup" element={<SignUp />} />
          <Route path="/signin" element={<SignIn />} />
          <Route path="/admin" element={<AdminMainPage />} />
          <Route path="/instructor" element={<InstructorMainPage />} />
          <Route path="/student" element={<StudentMainPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

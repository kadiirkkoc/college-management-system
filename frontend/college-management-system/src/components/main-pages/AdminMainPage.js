import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Sidebar from '../Sidebar';
import Header from '../Header';
import FacultyPage from '../pages/FacultyPage';
import DepartmentPage from '../pages/DepartmentPage';
import InstructorPage from '../pages/InstructorPage';
import LessonPage from '../pages/LessonPage';
import StudentPage from '../pages/StudentPage';
import '../../style/styles.css';

function AdminMainPage() {
  return (
    <div className="main-layout">
      <Header />
      <Sidebar />
      <div className="content">
        <Routes>
          <Route path="faculty" element={<FacultyPage />} />
          <Route path="department" element={<DepartmentPage />} />
          <Route path="instructor" element={<InstructorPage />} />
          <Route path="lesson" element={<LessonPage />} />
          <Route path="student" element={<StudentPage />} />
        </Routes>
      </div>
    </div>
  );
}

export default AdminMainPage;

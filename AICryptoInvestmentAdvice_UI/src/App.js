import './App.css';
import React from "react";
import { Route, Routes } from "react-router-dom";
import HomePage  from './pages/HomePage';
import UserHistory from './pages/UserHistory';
export default function App() {
  return (
    <Routes>
      <Route exact path="/" element={<HomePage/>}/>
      <Route path="/userhistory/:username" element={<UserHistory/>}/>
    </Routes>
  );
}


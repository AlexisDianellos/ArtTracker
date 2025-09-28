import './App.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import ArtGallery from './components/ArtGallery';
import AuthHandler from './components/AuthHandler';
import React from 'react';
import Navbar from './components/Navbar';
import { Provider } from 'react-redux';
import { store } from './store';  

function App() {
  return (
    <div>
        <Provider store={store}>
        <Router>
          <Navbar />
        <Routes>
        <Route path="/auth" element={<AuthHandler />} />
        <Route path="/art" element={<ArtGallery />} />
        </Routes>
        </Router>
        </Provider>
    </div>
  );
}

export default App;

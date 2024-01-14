import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import './App.css';
import Search from './components/Search/Search';
import TrackingForm from './components/TrackingForm/TrackingForm';

function App() {
  

  return (
      <Router>
        <div className="container">
          <div className="left-panel">
            <TrackingForm />
          </div>
          <div className="right-panel">
            <Search />
          </div>
        </div>
      </Router>
  );
}

export default App;

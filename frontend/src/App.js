// App.js
import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import TrackingForm from './components/TrackingForm';
import PackageList from './components/PackageList';
import './App.css';

function App() {
  const trackPackage = ({ trackingNumber, carrier }) => {
    // W tym miejscu można by przekazać dane do serwera, aby dodać nową paczkę
    console.log(`Nadanie paczki: ${trackingNumber}, ${carrier}`);
  };

  return (
      <Router>
        <div className="container">
          <div className="left-panel">
            <TrackingForm onTrack={trackPackage} />
          </div>
          <div className="right-panel">
            <PackageList />
          </div>
        </div>
      </Router>
  );
}

export default App;

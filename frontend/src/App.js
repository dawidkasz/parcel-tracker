import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import './App.css';
import Search from './components/Search/Search';
import TrackingForm from './components/TrackingForm/TrackingForm';

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
            <Search />
          </div>
        </div>
      </Router>
  );
}

export default App;

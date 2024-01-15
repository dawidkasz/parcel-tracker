import React, { useState } from 'react';
import './TrackingForm.css';
import { handleTrack } from '../../functions';

const TrackingForm = () => {
    const [trackingNumber, setTrackingNumber] = useState('');
    const [carrier, setCarrier] = useState('');
    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    return (
        <div className="tracking-form">
            <h2>Nadaj Paczkę</h2>
            {successMessage && <div className="success-message">{successMessage}</div>}
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            <label>Numer Przesyłki:</label>
            <input
                type="text"
                value={trackingNumber}
                onChange={(e) => setTrackingNumber(e.target.value)}
            />
            <label>Przewoźnik:</label>
            <div className="carrier-container">
                <div
                    className={`carrier-option ${carrier === 'INPOST' ? 'selected' : ''}`}
                    onClick={() => setCarrier('INPOST')}
                >
                    <img src={"/inpost.jpg"} alt="InPost" />
                    <p>InPost</p>
                </div>

                <div
                    className={`carrier-option ${carrier === 'IN_MEMORY' ? 'selected' : ''}`}
                    onClick={() => setCarrier('IN_MEMORY')}
                >
                    <img src={"/in_memory.png"} alt="Parcel" />
                    <p>In Memory</p>
                </div>
            </div>
            <button
                onClick={() =>
                    handleTrack(trackingNumber, carrier, setErrorMessage, setSuccessMessage)
                }
            >
                Nadaj Paczkę
            </button>
        </div>
    );
};

export default TrackingForm;

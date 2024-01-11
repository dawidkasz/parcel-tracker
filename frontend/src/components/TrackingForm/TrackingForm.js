// components/TrackingForm.js
import React, { useState } from 'react';
import './TrackingForm.css';
import axios from 'axios';

const TrackingForm = ({ onTrack }) => {
    const [trackingNumber, setTrackingNumber] = useState('');
    const [carrier, setCarrier] = useState('');

    const handleTrack = async () => {
        try {
            const response = await axios.post('http://localhost:9090/tracking/start', {
                id: trackingNumber,
                carrierName: carrier,
            });

            console.log('Pomyślnie dodano paczkę!', response.data);
            onTrack({ trackingNumber, carrier });
        } catch (error) {
            console.error('Błąd podczas dodawania paczki.', error);
        }
    };

    return (
        <div className="tracking-form">
            <h2>Nadaj Paczkę</h2>
            <label>Numer Przesyłki:</label>
            <input
                type="text"
                value={trackingNumber}
                onChange={(e) => setTrackingNumber(e.target.value)}
            />
            <label>Przewoźnik:</label>
            <select value={carrier} onChange={(e) => setCarrier(e.target.value)}>
                <option value="">Wybierz przewoźnika</option>
                <option value="ship24">Ship24</option>
                <option value="inpost">InPost</option>
                <option value="fedex">FedEx</option>
                <option value="random">Random</option>
            </select>
            <button onClick={handleTrack}>Nadaj Paczkę</button>
        </div>
    );
};

export default TrackingForm;

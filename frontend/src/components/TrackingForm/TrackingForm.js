import React, { useState } from 'react';
import './TrackingForm.css';
import { handleTrack } from '../../functions';

const TrackingForm = () => {
    const [trackingNumber, setTrackingNumber] = useState('');
    const [carrier, setCarrier] = useState('');

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
                <option value="INPOST">InPost</option>
                <option value="IN_MEMORY">In Memory</option>
            </select>
            <button onClick={() => handleTrack(trackingNumber, carrier)}>Nadaj Paczkę</button>
        </div>
    );
};

export default TrackingForm;

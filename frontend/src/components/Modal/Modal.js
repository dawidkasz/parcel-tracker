import React, { useEffect, useState } from 'react';
import './Modal.css';
import ReportList from '../Lists/ReportList';

const simulatedResultsReports = [
    {
        id: 1,
        query: 'aqq',
    },
    {
        id: 2,
        query: 'aqq',
    },
    {
        id: 3,
        query: 'aqq',
    },
    {
        id: 4,
        query: 'aqq',
    },
    {
        id: 5,
        query: 'aqq',
    },
    {
        id: 6,
        query: 'aqq',
    }
]

const Modal = ({ isVisible, onClose }) => {

    const [reports, setReports] = useState([])

    useEffect(() => {
        // Tutaj request o dostępne raporty
        setReports(simulatedResultsReports);
    }, []);

    return (
        isVisible && (
            <div className="modal-overlay" onClick={onClose}>
                <div
                    className="modal"
                    onClick={(e) => {
                        e.stopPropagation();
                    }}
                >
                    <h3>Raporty</h3>
                    <div className="modal-content">
                        <ReportList reports={reports} />
                    </div>
                </div>
            </div >
        )
    );
};

export default Modal;

import React, { useState, useEffect } from 'react';
import './ReportsModal.css';
import { handleDownloadReport, handleGetReports } from '../../functions';

const ReportsModal = ({ isVisible, onClose }) => {
    const [reports, setReports] = useState([]);

    useEffect(() => {
        const fetchReports = async () => {
            const fetchedReports = await handleGetReports();
            setReports(fetchedReports || []);
        };

        if (isVisible) {
            fetchReports();
        }
    }, [isVisible]);

    console.log("Reports modal")

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
                        <ul className="package-list-container">
                            {reports[0] ? reports.map((rep) => (
                                <li key={rep.id} onClick={() => handleDownloadReport(rep.id)}>
                                    <strong>Numer raportu:</strong> {rep.id}
                                    <strong>Query raportu:</strong> {rep.query}
                                    <strong>Czas stworzenia:</strong> {rep.creationTime}
                                </li>
                            )) : <strong className='no-reports'>Brak raportów</strong>}
                        </ul>
                    </div>
                </div>
            </div >
        )
    );
};

export default ReportsModal;

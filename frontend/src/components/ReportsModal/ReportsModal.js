import React from 'react';
import './ReportsModal.css';
import { handleDownloadReport } from '../../functions';

const ReportsModal = ({ isVisible, onClose, reports }) => {

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
                                <li key={rep} onClick={() => handleDownloadReport(rep)}>
                                    <strong>Numer raportu:</strong> {rep}
                                </li>
                            )): <strong className='no-reports'>Brak raportów</strong>}
                        </ul>
                    </div>
                </div>
            </div >
        )
    );
};

export default ReportsModal;

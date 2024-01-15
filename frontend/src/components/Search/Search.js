import React, { useState } from 'react';
import './Search.css';
import PackageList from '../PackageList.js/PackageList';
import ReportsModal from '../ReportsModal/ReportsModal';
import { handleGenerateReport, handleSearch } from '../../functions';


const Search = () => {
    const [packages, setPackages] = useState([]);
    const [reports, setReports] = useState([])
    const [searchTerm, setSearchTerm] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);
    const [successMessage, setSuccessMessage] = useState(null);
    const [errorMessage, setErrorMessage] = useState(null);

    const handleShowReports = () => {
        setIsModalVisible(true);
    };

    const handleCloseModal = () => {
        setIsModalVisible(false);
    };

    return (
        <div className="search">
            <h2>Wyszukaj Paczki</h2>
            {successMessage && <div className="success-message">{successMessage}</div>}
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            <label>Wyszukaj:</label>
            <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={() => handleSearch(searchTerm, setPackages, setSuccessMessage, setErrorMessage)}>Szukaj</button>
            <button onClick={() => handleGenerateReport(searchTerm, reports, setReports, setSuccessMessage, setErrorMessage)}>Wygeneruj raport</button>
            <button onClick={handleShowReports}>Pokaż raporty</button>

            <PackageList packages={packages} />

            <ReportsModal isVisible={isModalVisible} onClose={handleCloseModal} reports={reports} />
        </div>
    );
};

export default Search;

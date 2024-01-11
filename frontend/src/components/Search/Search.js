import React, { useState } from 'react';
import './Search.css';
import PackageList from '../Lists/PackageList';
import Modal from '../Modal/Modal';


const simulatedResultsPackages = [
    {
        id: 1,
        carrier: 'DHL',
        statuses: [
            { status: 'Pending', timestamp: '2022-01-10T10:00:00' },
            { status: 'In transit', timestamp: '2022-01-10T12:00:00' },
        ],
    },
    {
        id: 2,
        carrier: 'UPS',
        statuses: [
            { status: 'Delivered', timestamp: '2022-01-11T14:00:00' },
        ],
    },
    {
        id: 3,
        carrier: 'InPost',
        statuses: [
            { status: 'Pending', timestamp: '2022-01-10T10:00:00' },
            { status: 'In transit', timestamp: '2022-01-10T12:00:00' },
        ],
    },
    {
        id: 4,
        carrier: 'InPost',
        statuses: [
            { status: 'Delivered', timestamp: '2022-01-11T14:00:00' },
        ],
    },
    {
        id: 5,
        carrier: 'DHL',
        statuses: [
            { status: 'Pending', timestamp: '2022-01-10T10:00:00' },
            { status: 'In transit', timestamp: '2022-01-10T12:00:00' },
        ],
    },
    {
        id: 6,
        carrier: 'UPS',
        statuses: [
            { status: 'Delivered', timestamp: '2022-01-11T14:00:00' },
        ],
    },
    {
        id: 7,
        carrier: 'InPost',
        statuses: [
            { status: 'Pending', timestamp: '2022-01-10T10:00:00' },
            { status: 'In transit', timestamp: '2022-01-10T12:00:00' },
        ],
    },
    {
        id: 8,
        carrier: 'InPost',
        statuses: [
            { status: 'Delivered', timestamp: '2022-01-11T14:00:00' },
        ],
    },
];

const Search = () => {
    const [packages, setPackages] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isModalVisible, setIsModalVisible] = useState(false);

    const handleSearch = () => {
        // Tutaj request do findera
        setPackages(simulatedResultsPackages);
    };

    const handleGenerateReport = () => {
        // Tutaj generowanie nowego raportu
        console.log('Generowanie raportu...');
    };

    const handleShowReports = () => {
        setIsModalVisible(true);
    };

    const handleCloseModal = () => {
        setIsModalVisible(false);
    };

    return (
        <div className="search">
            <h2>Wyszukaj Paczki</h2>
            <label>Wyszukaj:</label>
            <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={handleSearch}>Szukaj</button>
            {packages.length > 0 && (
                <button onClick={handleGenerateReport}>Wygeneruj raport</button>
            )}
            <button onClick={handleShowReports}>Pokaż raporty</button>

            <PackageList packages={packages} />

            <Modal isVisible={isModalVisible} onClose={handleCloseModal} />
        </div>
    );
};

export default Search;

// components/PackageList.js
import React, { useState } from 'react';
import './PackageList.css';

const PackageList = () => {
    const [packages, setPackages] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedPackage, setSelectedPackage] = useState(null);

    const handleSearch = () => {
        const simulatedResults = [
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
        ];
        setPackages(simulatedResults);
    };

    const handlePackageClick = (pkg) => {
        setSelectedPackage(selectedPackage === pkg ? null : pkg);
    };

    return (
        <div className="package-list">
            <h2>Wyszukaj Paczki</h2>
            <label>Wyszukaj:</label>
            <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={handleSearch}>Szukaj</button>
            <ul>
                {packages.map((pkg) => (
                    <li key={pkg.id} onClick={() => handlePackageClick(pkg)}>
                        <strong>Numer paczki:</strong> {pkg.id}
                        <br />
                        <strong>Firma przewoźnicza:</strong> {pkg.carrier}
                        {selectedPackage && selectedPackage.id === pkg.id && (
                            <ul>
                                <strong>Statusy:</strong>
                                {pkg.statuses.map((status) => (
                                    <li key={status.timestamp}>
                                        {status.status} - {status.timestamp}
                                    </li>
                                ))}
                            </ul>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PackageList;

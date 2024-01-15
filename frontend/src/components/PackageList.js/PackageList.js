import React, { useState } from 'react';
import './PackageList.css'; // Styl dla PackageList

const PackageList = ({ packages }) => {
    const [selectedPackage, setSelectedPackage] = useState(null);

    const handlePackageClick = (pkg) => {
        setSelectedPackage(selectedPackage === pkg ? null : pkg);
    };

    return (
        <ul className="package-list-container">
            {packages.map((pkg) => (
                <li key={pkg.id} onClick={() => handlePackageClick(pkg)}>
                    <strong>Numer paczki:</strong> {pkg.id}
                    <br />
                    <strong>Firma przewoźnicza:</strong> {pkg.carrier}
                    {selectedPackage && selectedPackage.id === pkg.id && (
                        <ul className="status-list">
                            {pkg.history.map((status) => (
                                <li key={status.timestamp}>
                                    <p>{status.status}</p>
                                    <p>{status.timestamp}</p>
                                    <p>{status.description}</p>
                                </li>
                            ))}
                        </ul>
                    )}
                </li>
            ))}
        </ul>
    );
};

export default PackageList;

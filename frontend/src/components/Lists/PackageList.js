import React, { useState } from 'react'

const PackageList = ({ packages }) => {

    const [selectedPackage, setSelectedPackage] = useState(null);


    const handlePackageClick = (pkg) => {
        setSelectedPackage(selectedPackage === pkg ? null : pkg);
    };

    return (
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
    )
}

export default PackageList
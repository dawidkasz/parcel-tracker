import axios from "axios";

export const handleTrack = async (trackingNumber, carrier) => {
    
    if (trackingNumber === "" || carrier === "") {
        console.log("Brakuje paczki, albo przewoźnika.")
        return;
    }

    try {
        const response = await axios.post('https://mszawerdops.bieda.it/api/track/tracking/start', {
            id: trackingNumber,
            carrierName: carrier,
        });

        console.log('Pomyślnie dodano paczkę!', response.data);

    } catch (error) {
        console.error('Błąd podczas dodawania paczki.', error);
    }
};
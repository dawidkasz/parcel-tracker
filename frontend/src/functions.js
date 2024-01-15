import axios from "axios";

export const handleTrack = async (trackingNumber, carrier, setErrorMessage, setSuccessMessage) => {

    if (trackingNumber === "" || carrier === "") {
        setErrorMessage("Brakuje paczki, albo przewoźnika!");
        setSuccessMessage(null);
        return;
    }

    try {
        const apiUrl = 'https://mszawerdops.bieda.it/api/track/tracking/start';
        const data = {
            id: trackingNumber,
            carrier: carrier,
        };

        const response = await axios.post(apiUrl, data);

        setErrorMessage(null);
        setSuccessMessage("Sukces!");

        console.log('Pomyślnie dodano paczkę!', response.data);

    } catch (error) {
        setErrorMessage("Coś poszło nie tak...");
        setSuccessMessage(null);
        console.error('Błąd podczas dodawania paczki.', error);
    }
};


export const handleSearch = async (query, setPackages, setSuccessMessage, setErrorMessage) => {

    const apiUrl = 'https://mszawerdops.bieda.it/api/find/parcel/search';

    setErrorMessage(null);
    setSuccessMessage(null);

    try {
        const response = await axios.get(`${apiUrl}?q=${encodeURIComponent(query)}`);
        setPackages(response.data);
    } catch (error) {
        console.error('Błąd podczas wyszukiwania.', error);
    }
};

export const handleGenerateReport = async (query, reports, setReports, setSuccessMessage, setErrorMessage) => {

    try {
        const apiUrl = 'https://mszawerdops.bieda.it/api/report/report';
        const data = {
            query: query
        };

        const response = await axios.post(apiUrl, data);
        setReports([...reports, response.data]);
        setSuccessMessage("Pomyślnie wygenerowano");
        setErrorMessage(null)
    } catch (error) {
        setErrorMessage("Coś poszło nie tak...");
        setSuccessMessage(null)
        console.error('Błąd podczas dodawania paczki.', error);
    }
};

export const handleDownloadReport = async (name) => {
    console.log("handling report download");
    const apiUrl = 'https://mszawerdops.bieda.it/api/report';

    window.location.replace(`${apiUrl}${name}`);
};

export const handleGetReports = async () => {
    try {
        const response = await axios.get('https://mszawerdops.bieda.it/api/report/reports');
        return response.data;
    } catch (error) {
        console.error('Błąd podczas pobierania.', error);
    }
};
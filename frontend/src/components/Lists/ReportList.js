import React from 'react'

const ReportList = ({ reports }) => {
    return (
        <ul>
            {reports.map((report) => (
                <li key={report.id}>
                    <strong>Numer reportu:</strong> {report.id}
                    <br />
                    <strong>Query:</strong> {report.query}
                </li>
            ))}
        </ul>
    )
}

export default ReportList
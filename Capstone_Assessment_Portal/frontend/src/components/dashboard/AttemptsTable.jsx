/**
 * Reusable Recent Attempts Table
 */

import "../../styles/dashboard/ResultsTable.css";

function AttemptsTable({ attempts }) {
    return (
        <div className="results-table-container">

            <h2 className="table-title">
                Recent Quiz Attempts
            </h2>

            <table className="results-table">

                <thead>
                    <tr>
                        <th>Student</th>
                        <th>Quiz</th>
                        <th>Score</th>
                        <th>Date</th>
                    </tr>
                </thead>

                <tbody>

                    {
                        attempts.map((attempt) => (
                            <tr
                                key={`${attempt.student}-${attempt.quiz}`}
                            >
                                <td>{attempt.student}</td>
                                <td>{attempt.quiz}</td>
                                <td>{attempt.score}</td>
                                <td>{attempt.date}</td>
                            </tr>
                        ))
                    }

                </tbody>

            </table>

        </div>
    );
}

export default AttemptsTable;
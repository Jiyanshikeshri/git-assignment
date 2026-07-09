/**
 * Reusable Results Table
 * Displays recent quiz results for students
 */

import "../../styles/dashboard/ResultsTable.css";

function ResultsTable({ results }) {
    return (
        <div className="results-table-container">
            <h2 className="table-title">
                Recent Results
            </h2>

            <table className="results-table">
                <thead>
                    <tr>
                        <th>Quiz</th>
                        <th>Category</th>
                        <th>Score</th>
                        <th>Status</th>
                    </tr>
                </thead>

                <tbody>
                    {
                        results.map((result, index) => (
                            <tr key={index}>
                                <td>{result.quiz}</td>
                                <td>{result.category}</td>
                                <td>{result.score}</td>
                                <td>
                                    <span
                                        className={
                                            result.status === "Passed"
                                                ? "status-passed"
                                                : "status-failed"
                                        }
                                    >
                                        {result.status}
                                    </span>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    );
}

export default ResultsTable;
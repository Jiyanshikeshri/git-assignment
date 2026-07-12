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
                            key={`${attempt.student_name}-${attempt.quiz_title}-${attempt.submitted_at}`}
                        >
                            <td>
                                {attempt.student_name}
                            </td>
                            <td>
                                {attempt.quiz_title}
                            </td>
                            <td>
                                {attempt.score}
                            </td>
                            <td>
                                {
                                    new Date(
                                        attempt.submitted_at + "Z",
                                    ).toLocaleString(
                                        "en-IN",
                                        {
                                            day: "2-digit",
                                            month: "2-digit",
                                            year: "numeric",
                                            hour: "numeric",
                                            minute: "2-digit",
                                            hour12: true,
                                        },
                                    )
                                }
                            </td>
                        </tr>
                    ))
                }
                </tbody>
            </table>
        </div>
    );
}

export default AttemptsTable;
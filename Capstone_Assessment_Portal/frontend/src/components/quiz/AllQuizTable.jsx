/**
 * Read-only Quiz Table
 * Displays all quizzes in the system
 */

import "../../styles/quiz/AllQuizTable.css";

function AllQuizTable({
    quizzes,

}) {
    return (
        <div className="quiz-table-container">
            <table className="quiz-table">
                <thead>
                    <tr>
                        <th>
                            Quiz Title
                        </th>
                        <th>
                            Duration
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {
                        quizzes.length > 0
                            ? (
                                quizzes.map((quiz) => (
                                    <tr key={quiz.id}>
                                        <td>
                                            {quiz.title}
                                        </td>
                                        <td>
                                            {quiz.duration} mins
                                        </td>
                                    </tr>
                                ))
                            )
                            : (
                                <tr>
                                    <td
                                        colSpan="2"
                                        className="no-data"
                                    >
                                        No quizzes found.
                                    </td>
                                </tr>
                            )
                    }
                </tbody>
            </table>
        </div>
    );
}

export default AllQuizTable;
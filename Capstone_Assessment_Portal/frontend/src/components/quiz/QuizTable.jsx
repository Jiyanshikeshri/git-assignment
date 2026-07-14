/**
 * Reusable Quiz Table
 * Displays all quizzes for the selected category
 */

import {
    FaEdit,
    FaTrash,
    FaList,
} from "react-icons/fa";

import {
    useNavigate,
    useParams,
} from "react-router-dom";

import "../../styles/quiz/QuizTable.css";

function QuizTable({
    quizzes,
    onEdit,
    onDelete,

}) {

    const navigate = useNavigate();
    const { categoryId } = useParams();

    return (
        <div className="quiz-table-container">
            <table className="quiz-table">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Duration (mins)</th>
                        <th>Actions</th>
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
                                            {quiz.description}
                                        </td>
                                        <td>
                                            {quiz.duration}
                                        </td>
                                        <td className="quiz-actions">
                                            <button
                                                className="question-btn"
                                                onClick={() =>
                                                    navigate(
                                                        `/admin/categories/${categoryId}/quizzes/${quiz.id}/questions`,
                                                    )
                                                }
                                            >
                                                <FaList />
                                                Questions
                                            </button>

                                            <button
                                                className="edit-btn"
                                                onClick={() => onEdit(quiz)}
                                            >
                                                <FaEdit />
                                                Edit
                                            </button>

                                            <button
                                                className="delete-btn"
                                                onClick={() => onDelete(quiz)}
                                            >
                                                <FaTrash />
                                                Delete
                                            </button>

                                        </td>
                                    </tr>
                                ))
                            )
                            : (
                                <tr>
                                    <td
                                        colSpan="4"
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

export default QuizTable;
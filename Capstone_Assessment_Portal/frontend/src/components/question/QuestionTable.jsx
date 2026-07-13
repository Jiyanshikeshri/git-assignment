/**
 * Question Table Component
 * Displays all questions belonging to a quiz
 */

import {
    FaEdit,
    FaTrash,
} from "react-icons/fa";

import "../../styles/question/QuestionTable.css";

function QuestionTable({

    questions,
    onEdit,
    onDelete,

}) {

    return (
        <div className="question-table-container">
            {
                questions.length === 0 ? (
                    <p className="no-data">
                        No questions found.
                    </p>
                ) : (
                    <table className="question-table">
                        <thead>
                            <tr>
                                <th>
                                    Question
                                </th>
                                <th>
                                    Type
                                </th>
                                <th>
                                    Difficulty
                                </th>
                                <th>
                                    Actions
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                questions.map((question) => (
                                    <tr
                                        key={question.id}
                                    >
                                        <td>
                                            {question.question_text}
                                        </td>
                                        <td>
                                            {question.question_type}
                                        </td>
                                        <td>
                                            {question.difficulty}
                                        </td>
                                        <td>
                                            <div className="question-actions">
                                                <button
                                                    className="edit-btn"
                                                    onClick={() =>
                                                        onEdit(question)
                                                    }
                                                >
                                                    <FaEdit />
                                                    Edit
                                                </button>
                                                <button
                                                    className="delete-btn"
                                                    onClick={() =>
                                                        onDelete(question)
                                                    }
                                                >
                                                    <FaTrash />
                                                    Delete
                                                </button>
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            }
                        </tbody>
                    </table>
                )
            }
        </div>
    );
}
export default QuestionTable;
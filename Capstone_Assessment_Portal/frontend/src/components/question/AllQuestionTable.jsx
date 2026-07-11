/**
 * Read Only Question Table
 * Displays all questions without actions
 */

import "../../styles/question/QuestionTable.css";

function AllQuestionTable({

    questions,

}) {

    return (

        <div className="question-table-container">

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

                    </tr>

                </thead>

                <tbody>

                    {

                        questions.length > 0

                            ? (

                                questions.map((question) => (

                                    <tr key={question.id}>

                                        <td>
                                            {question.question_text}
                                        </td>

                                        <td>
                                            {question.question_type}
                                        </td>

                                        <td>
                                            {question.difficulty}
                                        </td>

                                    </tr>

                                ))

                            )

                            : (

                                <tr>

                                    <td
                                        colSpan="3"
                                        className="no-data"
                                    >

                                        No questions found.

                                    </td>

                                </tr>

                            )

                    }

                </tbody>

            </table>

        </div>

    );

}

export default AllQuestionTable;
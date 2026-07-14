/**
 * Student Result Breakdown Page
 */

import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";

import DashboardLayout from "../../pages/layouts/DashboardLayout";

import {
    getResultBreakdown,
} from "../../services/resultService";

import "../../styles/student/StudentResultBreakdown.css"

function StudentResultBreakdown() {

    const { resultId } = useParams();
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadResult();
    }, []);

    const loadResult = async () => {
        try {
            const data =
                await getResultBreakdown(
                    resultId,
                );
            setResult(data);
        }
        catch (error) {
            console.error(error);
        }
        finally {
            setLoading(false);
        }
    };

    if (loading) {
        return (
            <DashboardLayout>
                <h2>
                    Loading Result...
                </h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>
            <h1>
                Result Breakdown
            </h1>
            <hr />
            <h3>
                Score :
                {" "}
                {result.score}
                /
                {result.total_questions}
            </h3>
            <h3>
                Percentage :
                {" "}
                {result.percentage}%
            </h3>
            <h3>
                Status :
                {" "}
                {result.result_status}
            </h3>
            <hr />
            <h2>Question Review</h2>

            {
                result.questions.map((question, index) => (

                    <div
                        key={question.question_id}
                        className="question-review-card"
                    >

                        <h3>
                            Question {index + 1}
                        </h3>

                        <p>
                            <strong>Question:</strong>{" "}
                            {question.question_text}
                        </p>

                        <p>
                            <strong>Your Answer:</strong>{" "}
                            {question.selected_answer ?? "Not Answered"}
                        </p>

                        <p>
                            <strong>Correct Answer:</strong>{" "}
                            {question.correct_answer}
                        </p>

                        <p
                            style={{
                                color: question.is_correct
                                    ? "green"
                                    : "red",
                                fontWeight: "bold",
                            }}
                        >
                            {
                                question.is_correct
                                    ? "Correct"
                                    : "Incorrect"
                            }
                        </p>

                    </div>

                ))
            }
        </DashboardLayout>

    );

}

export default StudentResultBreakdown;
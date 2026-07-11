/**
 * Admin Question Management Page
 * Displays all questions of the selected quiz
 */

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuestionTable from "../../components/question/QuestionTable";

import { getQuestionsByQuiz } from "../../services/questionService";

import "../../styles/question/QuestionManagement.css";

function QuestionManagement() {

    const { quizId } = useParams();

    const [questions, setQuestions] = useState([]);

    const fetchQuestions = async () => {

        try {

            const data = await getQuestionsByQuiz(
                quizId,
            );

            setQuestions(data);

        }

        catch (error) {

            console.error(
                "Failed to fetch questions:",
                error,
            );

        }

    };

    useEffect(() => {

        fetchQuestions();

    }, [quizId]);

    return (

        <DashboardLayout>

            <div className="question-page">

                <div className="question-header">

                    <div>

                        <h1>
                            Question Management
                        </h1>

                        <p className="question-description">
                            Total Questions : {questions.length}
                        </p>

                    </div>

                    <button className="add-question-btn">
                        + Add Question
                    </button>

                </div>

                <QuestionTable
                    questions={questions}
                    onEdit={() => {}}
                    onDelete={() => {}}
                />

            </div>

        </DashboardLayout>

    );

}

export default QuestionManagement;
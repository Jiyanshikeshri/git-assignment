/**
 * Admin Question List Page
 * Displays all questions available in the system
 */

import { useEffect, useState } from "react";

import DashboardLayout from "../../layouts/DashboardLayout";
import AllQuestionTable from "../../components/question/AllQuestionTable";

import { getAllQuestions } from "../../services/questionService";

import "../../styles/question/QuestionManagement.css";

function QuestionList() {

    const [questions, setQuestions] = useState([]);

    const fetchQuestions = async () => {

        try {

            const data = await getAllQuestions();

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

    }, []);

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

                </div>

                <AllQuestionTable
                    questions={questions}
                />
            </div>
        </DashboardLayout>

    );

}

export default QuestionList;
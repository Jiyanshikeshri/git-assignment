/**
 * Admin Question List Page
 * Displays all questions available in the system
 */

import { useEffect, useState } from "react";

import DashboardLayout from "../../layouts/DashboardLayout";
import AllQuestionTable from "../../components/question/AllQuestionTable";
import Pagination from "../../components/common/Pagination";

import { getAllQuestions } from "../../services/questionService";

import "../../styles/question/QuestionManagement.css";

function QuestionList() {

    const [questions, setQuestions] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const fetchQuestions = async () => {

        try {

            const data = await getAllQuestions();

            setQuestions(data);
            setCurrentPage(1);

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

    const indexOfLastItem =
        currentPage * itemsPerPage;

    const indexOfFirstItem =
        indexOfLastItem - itemsPerPage;

    const currentQuestions =
        questions.slice(
            indexOfFirstItem,
            indexOfLastItem,
        );

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
                    questions={currentQuestions}
                />
                <Pagination
                    currentPage={currentPage}
                    totalItems={questions.length}
                    itemsPerPage={itemsPerPage}
                    onPageChange={setCurrentPage}
                />
            </div>
        </DashboardLayout>

    );

}

export default QuestionList;
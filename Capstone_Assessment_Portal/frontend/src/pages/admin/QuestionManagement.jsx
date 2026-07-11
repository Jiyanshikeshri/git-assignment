/**
 * Admin Question Management Page
 * Displays all questions of the selected quiz
 */

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuestionTable from "../../components/question/QuestionTable";
import QuestionFormModal from "../../components/question/QuestionFormModal";

import { getQuestionsByQuiz } from "../../services/questionService";

import "../../styles/question/QuestionManagement.css";

function QuestionManagement() {

    const { quizId } = useParams();
    const [questions, setQuestions] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

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

    const handleOpenModal = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleQuestionCreated = () => {
        fetchQuestions();
        handleCloseModal();
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

                    <button 
                        className="add-question-btn"
                        onClick={handleOpenModal}>
                        + Add Question
                    </button>

                </div>

                <QuestionTable
                    questions={questions}
                    onEdit={() => {}}
                    onDelete={() => {}}
                />

                <QuestionFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onQuestionCreated={handleQuestionCreated}
                    quizId={quizId}
                />
            </div>

        </DashboardLayout>

    );

}

export default QuestionManagement;
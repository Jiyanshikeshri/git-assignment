import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuizTable from "../../components/quiz/QuizTable";
import QuizFormModal from "../../components/quiz/QuizFormModal";

import { getQuizzesByCategory } from "../../services/quizService";

import "../../styles/quiz/QuizManagement.css";

function QuizManagement() {
    const { categoryId } = useParams();
    const [quizzes, setQuizzes] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    const fetchQuizzes = async () => {
        try {
            const data = await getQuizzesByCategory(
                categoryId,
            );
            setQuizzes(data);
        }
        catch (error) {
            console.error(
                "Failed to fetch quizzes:",
                error,
            );
        }
    };

    useEffect(() => {                       
        fetchQuizzes();
    }, [categoryId]);

    const handleOpenModal = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleQuizCreated = async () => {
        await fetchQuizzes();
        handleCloseModal();
    };

    return (
        <DashboardLayout>
            <div className="quiz-page">
                <div className="quiz-header">
                    <div>
                        <h1>
                            Quiz Management
                        </h1>
                        <p className="quiz-description">
                            Total Quizzes : {quizzes.length}
                        </p>
                    </div>
                    <button
                        className="add-quiz-btn"
                        onClick={handleOpenModal}
                    >
                        + Add Quiz
                    </button>
                </div>
                <QuizTable
                    quizzes={quizzes}
                    onEdit={() => {}}
                    onDelete={() => {}}
                />
                <QuizFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onQuizCreated={handleQuizCreated}
                    categoryId={categoryId}
                />
            </div>
        </DashboardLayout>
    );
}

export default QuizManagement;
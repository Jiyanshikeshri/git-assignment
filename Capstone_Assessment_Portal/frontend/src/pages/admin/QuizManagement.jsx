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
    const [selectedQuiz, setSelectedQuiz] = useState(null);

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
        setSelectedQuiz(null);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setSelectedQuiz(null);
        setIsModalOpen(false);
    };

    const handleQuizCreated = async () => {
        await fetchQuizzes();
        handleCloseModal();
    };

    const handleEditQuiz = (quiz) => {
        setSelectedQuiz(quiz);
        setIsModalOpen(true);
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
                    onEdit={handleEditQuiz}
                    onDelete={() => {}}
                />
                <QuizFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onQuizCreated={handleQuizCreated}
                    categoryId={categoryId}
                    selectedQuiz={selectedQuiz}
                />
            </div>
        </DashboardLayout>
    );
}

export default QuizManagement;
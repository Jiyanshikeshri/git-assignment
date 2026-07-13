import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuizTable from "../../components/quiz/QuizTable";
import QuizFormModal from "../../components/quiz/QuizFormModal";
import DeleteQuizModal from "../../components/quiz/DeleteQuizModal";
import Pagination from "../../components/common/Pagination";

import { getQuizzesByCategory } from "../../services/quizService";
import { deleteQuiz } from "../../services/quizService";

import "../../styles/quiz/QuizManagement.css";

function QuizManagement() {
    const { categoryId } = useParams();
    const [quizzes, setQuizzes] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedQuiz, setSelectedQuiz] = useState(null);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const fetchQuizzes = async () => {
        try {
            const data = await getQuizzesByCategory(
                categoryId,
            );
            setQuizzes(data);
            setCurrentPage(1);
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

    const handleDeleteQuiz = (quiz) => {
        setSelectedQuiz(quiz);
        setIsDeleteModalOpen(true);
    };

    const handleConfirmDelete = async () => {
        try {
            await deleteQuiz(
                selectedQuiz.id,
            );
            setIsDeleteModalOpen(false);
            setSelectedQuiz(null);
            await fetchQuizzes();
        }
        catch (error) {
            console.error(
                "Failed to delete quiz:",
                error,
            );
        }   
    };

    const handleCloseDeleteModal = () => {
        setIsDeleteModalOpen(false);
        setSelectedQuiz(null);
    };

    const indexOfLastItem =
        currentPage * itemsPerPage;

    const indexOfFirstItem =
        indexOfLastItem - itemsPerPage;

    const currentQuizzes =
        quizzes.slice(
            indexOfFirstItem,
            indexOfLastItem,
        );

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
                    quizzes={currentQuizzes}
                    onEdit={handleEditQuiz}
                    onDelete={handleDeleteQuiz}
                />
                <Pagination
                    currentPage={currentPage}
                    totalItems={quizzes.length}
                    itemsPerPage={itemsPerPage}
                    onPageChange={setCurrentPage}
                />
                <QuizFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onQuizCreated={handleQuizCreated}
                    categoryId={categoryId}
                    selectedQuiz={selectedQuiz}
                />
                <DeleteQuizModal
                    isOpen={isDeleteModalOpen}
                    onClose={handleCloseDeleteModal}
                    onConfirm={handleConfirmDelete}
                />
            </div>
        </DashboardLayout>
    );
}

export default QuizManagement;
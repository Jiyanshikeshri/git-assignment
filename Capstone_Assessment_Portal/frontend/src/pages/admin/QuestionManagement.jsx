/**
 * Admin Question Management Page
 * Displays all questions of the selected quiz
 */

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuestionTable from "../../components/question/QuestionTable";
import QuestionFormModal from "../../components/question/QuestionFormModal";
import Pagination from "../../components/common/Pagination";

import { getQuestionsByQuiz, deleteQuestion } from "../../services/questionService";

import "../../styles/question/QuestionManagement.css";

function QuestionManagement() {

    const { quizId } = useParams();
    const [questions, setQuestions] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedQuestion, setSelectedQuestion] = useState(null);
    const [deleteModalOpen, setDeleteModalOpen] = useState(false);
    const [questionToDelete, setQuestionToDelete] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;

    const fetchQuestions = async () => {

        try {

            const data = await getQuestionsByQuiz(
                quizId,
            );

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

    const handleOpenModal = () => {
        setSelectedQuestion(null);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleEditQuestion = (question) => {
        setSelectedQuestion(question);
        setIsModalOpen(true);
    };

    const handleDeleteQuestion = (question) => {
        setQuestionToDelete(question);
        setDeleteModalOpen(true);
    };

    const handleConfirmDelete = async () => {
        if (!questionToDelete) {
            return;
        }
        try {
            await deleteQuestion(
                questionToDelete.id,
            );
            fetchQuestions();
        }
        catch (error) {
            console.error(
                "Failed to delete question:",
                error,
            );
        }
        finally {
            setDeleteModalOpen(false);
            setQuestionToDelete(null);
        }
    };

    const handleCancelDelete = () => {
        setDeleteModalOpen(false);
        setQuestionToDelete(null);
    };

    const handleQuestionCreated = () => {
        fetchQuestions();
        handleCloseModal();
    };

    const indexOfLastItem =
        currentPage * itemsPerPage;

    const indexOfFirstItem =
        indexOfLastItem - itemsPerPage;

    const currentQuestions =
        questions.slice(
            indexOfFirstItem,
            indexOfLastItem,
        );

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
                    questions={currentQuestions}
                    onEdit={handleEditQuestion}
                    onDelete={handleDeleteQuestion}
                />

                <Pagination
                    currentPage={currentPage}
                    totalItems={questions.length}
                    itemsPerPage={itemsPerPage}
                    onPageChange={setCurrentPage}
                />

                <QuestionFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onQuestionCreated={handleQuestionCreated}
                    quizId={quizId}
                    selectedQuestion={selectedQuestion}
                />
            </div>

            {
                deleteModalOpen &&
                questionToDelete && (
                    <div className="modal-overlay">
                        <div className="modal-content delete-modal">
                            <h2>
                                Delete Question
                            </h2>
                            <p>
                                Are you sure you want to delete this question?
                            </p>
                            <div className="modal-actions">
                                <button
                                    className="cancel-btn"
                                    onClick={handleCancelDelete}
                                >
                                    Cancel
                                </button>
                                <button
                                    className="delete-btn"
                                    onClick={handleConfirmDelete}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    </div>
                )
            }

        </DashboardLayout>

    );

}

export default QuestionManagement;
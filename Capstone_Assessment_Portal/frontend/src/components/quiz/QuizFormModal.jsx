/**
 * Reusable Quiz Form Modal
 * Used for creating and updating quizzes
 */

import { useEffect, useState } from "react";
import "../../styles/quiz/QuizFormModal.css";

import { createQuiz, updateQuiz } from "../../services/quizService";

function QuizFormModal({
    isOpen,
    onClose,
    onQuizCreated,
    categoryId,
    selectedQuiz,
}){

    const [formData, setFormData] = useState({
        title: "",
        description: "",
        duration: "",
    });

    const [errors, setErrors] = useState({});
    const [serverError, setServerError] = useState("");
    const [isSubmitting, setIsSubmitting] = useState(false);    

    const validateForm = () => {
        const validationErrors = {};
        if (!formData.title.trim()) {
            validationErrors.title =
                "Quiz title is required.";
        }
        else if (formData.title.trim().length < 3) {
            validationErrors.title =
                "Quiz title must be at least 3 characters.";
        }
        else if (formData.title.trim().length > 100) {
            validationErrors.title =
                "Quiz title cannot exceed 100 characters.";
        }

        if (!formData.description.trim()) {
            validationErrors.description =
                "Description is required.";
        }
        else if (formData.description.trim().length < 5) {
            validationErrors.description =
                "Description must be at least 5 characters.";
        }

        if (!formData.duration) {
            validationErrors.duration =
                "Duration is required.";
        }
        else if (Number(formData.duration) <= 0) {
            validationErrors.duration =
                "Duration must be greater than 0.";
        }

        setErrors(validationErrors);

        return Object.keys(validationErrors).length === 0;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!validateForm()) {
            return;
        }
        try {
            setIsSubmitting(true);
            setServerError("");

            const payload = {
                title: formData.title.trim(),
                description: formData.description.trim(),
                duration: Number(formData.duration),
                category_id: categoryId,
            };

            if (selectedQuiz) {
                await updateQuiz(
                    selectedQuiz.id,
                    payload,
                );
            }
            else {
                await createQuiz(payload);
            }
            setFormData({
                title: "",
                description: "",
                duration: "",
            });
            setErrors({});
            onQuizCreated();
        }
        catch (error) {
            setServerError(
                error.response?.data?.detail ||
                "Unable to create quiz."
            );
        }
        finally {
            setIsSubmitting(false);
        }
    };

    useEffect(() => {
        if (selectedQuiz) {
            setFormData({
                title: selectedQuiz.title,
                description: selectedQuiz.description,
                duration: selectedQuiz.duration,
            });
        }
        else {
            setFormData({
                title: "",
                description: "",
                duration: "",
            });
        }
        setErrors({});
        setServerError("");
    }, [selectedQuiz, isOpen]);

    if (!isOpen) {
        return null;
    }

    return (
        <div
            className="quiz-modal-overlay"
            onClick={onClose}
        >
            <div
                className="quiz-modal"
                onClick={(e) => e.stopPropagation()}
            >
                <div className="quiz-modal-header">
                    <h2>
                        {
                            selectedQuiz
                                ? "Edit Quiz"
                                : "Add Quiz"
                        }
                    </h2>
                    <button
                        className="close-btn"
                        onClick={onClose}
                    >
                    </button>
                </div>
                <form className="quiz-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Title</label>
                        <input
                            type="text"
                            placeholder="Enter quiz title"
                            value={formData.title}
                            onChange={(e) => {
                                setFormData({
                                    ...formData,
                                    title: e.target.value,
                                });
                                setErrors({
                                    ...errors,
                                    title: "",
                                });
                            }}
                        />
                        {
                            errors.title && (
                                <p className="form-error">
                                    {errors.title}
                                </p>
                            )
                        }
                    </div>
                    <div className="form-group">
                        <label>Description</label>
                        <textarea
                            placeholder="Enter description"
                            value={formData.description}
                            onChange={(e) => {
                                setFormData({
                                    ...formData,
                                    description: e.target.value,
                                });
                                setErrors({
                                    ...errors,
                                    description: "",
                                });
                            }}
                        />
                        {
                            errors.description && (
                                <p className="form-error">
                                    {errors.description}
                                </p>
                            )
                        }
                    </div>
                    <div className="form-group">
                        <label>Duration (mins)</label>
                        <input
                            type="number"
                            placeholder="30"
                            value={formData.duration}
                            onChange={(e) => {
                                setFormData({
                                    ...formData,
                                    duration: e.target.value,
                                });
                                setErrors({
                                    ...errors,
                                    duration: "",
                                });
                            }}
                        />
                        {
                            errors.duration && (
                                <p className="form-error">
                                    {errors.duration}
                                </p>
                            )
                        }
                    </div>
                    <div className="modal-actions">
                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={onClose}
                            disabled={isSubmitting}
                        >
                            Cancel
                        </button>
                        {
                            serverError && (
                                <p className="server-error">
                                    {serverError}
                                </p>
                            )
                        }
                        <button
                            type="submit"
                            className="save-btn"
                            disabled={isSubmitting}
                        >
                            {
                                isSubmitting
                                    ? "Saving..."
                                    : selectedQuiz
                                        ? "Update Quiz"
                                        : "Save Quiz"
                            }
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default QuizFormModal;
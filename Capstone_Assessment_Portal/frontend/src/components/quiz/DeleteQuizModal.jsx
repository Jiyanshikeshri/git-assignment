/**
 * Reusable Delete Quiz Confirmation Modal
 */

import "../../styles/quiz/DeleteQuizModal.css";

function DeleteQuizModal({
    isOpen,
    onClose,
    onConfirm,
}) {
    if (!isOpen) {
        return null;
    }
    return (
        <div className="modal-overlay">
            <div className="delete-modal">
                <h2>
                    Delete Quiz
                </h2>
                <p>
                    Are you sure you want to delete this quiz?
                </p>
                <div className="delete-modal-actions">
                    <button
                        className="cancel-btn"
                        onClick={onClose}
                    >
                        Cancel
                    </button>
                    <button
                        className="delete-btn"
                        onClick={onConfirm}
                    >
                        Delete
                    </button>
                </div>
            </div>
        </div>
    );
}

export default DeleteQuizModal;
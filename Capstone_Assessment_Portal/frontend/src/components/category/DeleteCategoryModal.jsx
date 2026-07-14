/**
 * Reusable Delete Category Confirmation Modal
 */

import "../../styles/category/DeleteCategoryModal.css";

function DeleteCategoryModal({

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
                    Delete Category
                </h2>

                <p>
                    Are you sure you want to delete this category?
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

export default DeleteCategoryModal;
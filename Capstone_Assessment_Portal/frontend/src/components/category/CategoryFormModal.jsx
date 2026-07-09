/**
 * Reusable Category Form Modal
 * Used for creating and updating categories
 */

import "../../styles/category/CategoryFormModal.css";

function CategoryFormModal({

    isOpen,
    onClose,

}) {

    if (!isOpen) {
        return null;
    }

    return (

        <div className="modal-overlay">
            <div className="category-modal">
                <h2>
                    Add Category
                </h2>
                <form>
                    <div className="form-group">
                        <label>
                            Category Name
                        </label>
                        <input
                            type="text"
                            placeholder="Enter category name"
                        />
                    </div>
                    <div className="modal-actions">
                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={onClose}
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="save-btn"
                        >
                            Save
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );

}

export default CategoryFormModal;
/**
 * Reusable Category Form Modal
 * Used for creating and updating categories
 */

import { useState } from "react";

import "../../styles/category/CategoryFormModal.css";

function CategoryFormModal({

    isOpen,
    onClose,

}) {

    const [categoryName, setCategoryName] = useState("");
    const [errors, setErrors] = useState({});

    /**
     * Validates category form fields
     */
    const validateForm = () => {

        const validationErrors = {};

        if (!categoryName.trim()) {

            validationErrors.categoryName =
                "Category name is required.";

        }
        else if (categoryName.trim().length < 3) {

            validationErrors.categoryName =
                "Category name must be at least 3 characters.";

        }
        else if (categoryName.trim().length > 50) {

            validationErrors.categoryName =
                "Category name cannot exceed 50 characters.";

        }

        setErrors(validationErrors);

        return Object.keys(validationErrors).length === 0;
    };

    /**
     * Handles category form submission.
     */
    const handleSubmit = (event) => {
        event.preventDefault();
        if (!validateForm()) {
            return;
        }
        console.log({
            categoryName,
        });
    };

    const handleCancel = () => {
        setCategoryName("");
        setErrors({});
        onClose();
    };

    if (!isOpen) {
        return null;
    }

    return (

        <div className="modal-overlay">
            <div className="category-modal">
                <h2>
                    Add Category
                </h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>
                            Category Name
                        </label>
                        <input
                            type="text"
                            placeholder="Enter category name"
                            value={categoryName}
                            onChange={(event) => {
                                setCategoryName(event.target.value);
                                setErrors({
                                    ...errors,
                                    categoryName: "",
                                });
                            }}
                        />
                        {
                            errors.categoryName && (
                                <p className="form-error">
                                    {errors.categoryName}
                                </p>
                            )
                        }
                    </div>
                    
                    <div className="modal-actions">
                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={handleCancel}
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
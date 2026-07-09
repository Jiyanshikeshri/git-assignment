/**
 * Reusable Category Table
 * Displays all available categories.
 */

import "../../styles/category/CategoryTable.css";
import {
    FaEdit,
    FaTrash,
} from "react-icons/fa";

function CategoryTable({
    categories,
    onEdit,
    onDelete,
}) {
    return (
        <div className="category-table-container">
            <table className="category-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>
                    {
                        categories.length > 0
                            ? (
                                categories.map((category) => (
                                    <tr key={category.id || category._id}>
                                        <td>
                                            {category.name}
                                        </td>
                                        <td className="category-actions">
                                            <button
                                                className="edit-btn" 
                                                onClick={() => onEdit(category)}
                                            >
                                                <FaEdit />
                                                Edit
                                            </button>

                                            <button
                                                className="delete-btn"
                                                onClick={() => onDelete(category)}
                                            >
                                                <FaTrash />
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            )
                            : (
                                <tr>
                                    <td
                                        colSpan="3"
                                        className="no-data"
                                    >
                                        No categories found.
                                    </td>
                                </tr>
                            )
                    }

                </tbody>
            </table>
        </div>
    );
}

export default CategoryTable;
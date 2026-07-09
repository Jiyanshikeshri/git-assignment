/**
 * Admin Category Management Page
 * Displays all categories and allows CRUD operations
 */

import DashboardLayout from "../../layouts/DashboardLayout";

import "../../styles/category/CategoryManagement.css";

function CategoryManagement() {

    return (
        <DashboardLayout>

            <div className="category-page">

                <div className="category-header">

                    <h1>
                        Category Management
                    </h1>

                    <button className="add-category-btn">
                        + Add Category
                    </button>

                </div>

                <p className="category-description">
                    Manage all quiz categories from one place.
                </p>

            </div>

        </DashboardLayout>
    );
}

export default CategoryManagement;
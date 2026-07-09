/**
 * Admin Category Management Page
 * Displays all categories and allows CRUD operations
 */

import { useEffect, useState } from "react";
import DashboardLayout from "../../layouts/DashboardLayout";
import CategoryTable from "../../components/category/CategoryTable";

import { getCategories } from "../../services/categoryService";

import "../../styles/category/CategoryManagement.css";

function CategoryManagement() {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const data = await getCategories();
            setCategories(data);
        }
        catch (error) {
            console.error(
                "Failed to fetch categories:",
                error,
            );
        }
    };

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

                <CategoryTable
                    categories={categories}
                />

            </div>

        </DashboardLayout>
    );
}

export default CategoryManagement;
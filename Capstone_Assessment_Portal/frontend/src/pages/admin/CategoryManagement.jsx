/**
 * Admin Category Management Page
 * Displays all categories and allows CRUD operations
 */

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import CategoryTable from "../../components/category/CategoryTable";
import CategoryFormModal from "../../components/category/CategoryFormModal";
import DeleteCategoryModal from "../../components/category/DeleteCategoryModal";

import { getCategories } from "../../services/categoryService";
import { deleteCategory } from "../../services/categoryService";

import "../../styles/category/CategoryManagement.css";

function CategoryManagement() {
    const [categories, setCategories] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedCategory, setSelectedCategory] = useState(null);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

    const navigate = useNavigate();

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

    const handleOpenModal = () => {
        setSelectedCategory(null);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setSelectedCategory(null);
        setIsModalOpen(false);
    };

    const handleCategoryCreated = async () => {
        await fetchCategories();
        handleCloseModal();
    };

    const handleEditCategory = (category) => {
        setSelectedCategory(category);
        setIsModalOpen(true);
    };

    const handleDeleteCategory = (category) => {
        setSelectedCategory(category);
        setIsDeleteModalOpen(true);
    };

    const handleConfirmDelete = async () => {
        try {
            await deleteCategory(
                selectedCategory.id
            );
            setIsDeleteModalOpen(false);
            setSelectedCategory(null);
            fetchCategories();
        }
        catch (error) {
            console.error(
                "Failed to delete category:",
                error,
            );
        }
    };

    const handleCloseDeleteModal = () => {
        setIsDeleteModalOpen(false);
        setSelectedCategory(null);
    };

    const handleViewQuizzes = (category) => {
        navigate(
            `/admin/categories/${category.id}/quizzes`
        );
    };

    return (
        <DashboardLayout>

            <div className="category-page">

                <div className="category-header">

                    <h1>
                        Category Management
                    </h1>

                    <button className="add-category-btn" onClick={handleOpenModal}>
                        + Add Category
                    </button>

                </div>

                <p className="category-description">
                    Manage all quiz categories from one place.
                </p>

                <CategoryTable
                    categories={categories}
                    onEdit={handleEditCategory}
                    onDelete={handleDeleteCategory}
                    onViewQuizzes={handleViewQuizzes}
                />

                <CategoryFormModal
                    isOpen={isModalOpen}
                    onClose={handleCloseModal}
                    onCategoryCreated={handleCategoryCreated}
                    selectedCategory={selectedCategory}
                />

                <DeleteCategoryModal
                    isOpen={isDeleteModalOpen}
                    onClose={handleCloseDeleteModal}
                    onConfirm={handleConfirmDelete}
                />

            </div>

        </DashboardLayout>
    );
}

export default CategoryManagement;
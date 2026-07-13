/**
 * Student Categories Page
 * Displays all available quiz categories for students
 */

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import DashboardLayout from "../../pages/layouts/DashboardLayout";
import CategoryCard from "../../components/student/CategoryCard";

import { getCategories } from "../../services/categoryService";

import "../../styles/student/StudentCategories.css";

function StudentCategories() {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

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
        finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleViewQuizzes = (category) => {
        navigate(
            `/student/categories/${category.id}/quizzes`
        );
    };

    return (
        <DashboardLayout>
            <div className="student-categories-page">
                <div className="student-categories-header">
                    <h1>
                        Categories
                    </h1>
                    <p>
                        Choose a category to explore available quizzes.
                    </p>
                    {
                        loading ? (
                            <p>
                                Loading categories...
                            </p>
                        ) : (
                            <div className="categories-grid">
                                {
                                    categories.map((category) => (
                                        <CategoryCard
                                            key={category.id}
                                            category={category}
                                            onViewQuizzes={handleViewQuizzes}
                                        />
                                    ))
                                }
                            </div>
                        )
                    }
                </div>
            </div>
        </DashboardLayout>
    );
}

export default StudentCategories;
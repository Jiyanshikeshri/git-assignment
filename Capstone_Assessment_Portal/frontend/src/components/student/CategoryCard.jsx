/**
 * Reusable Category Card
 * Displays a single quiz category for students.
 */

import { FaFolderOpen, FaArrowRight } from "react-icons/fa";

import "../../styles/student/CategoryCard.css";

function CategoryCard({

    category,
    onViewQuizzes,

}) {

    return (
        <div className="category-card">
            <div className="category-card-icon">
                <FaFolderOpen />
            </div>
            <div className="category-card-content">
                <h3>
                    {category.name}
                </h3>
                <p>
                    Explore quizzes available in this category.
                </p>
            </div>
            <button
                className="view-quizzes-btn"
                onClick={() => onViewQuizzes(category)}
            >
                View Quizzes
                <FaArrowRight />
            </button>

        </div>

    );

}

export default CategoryCard;
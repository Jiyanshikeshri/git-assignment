/**
 * Student Quiz List Page
 * Displays all quizzes of the selected category
 */

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import StudentQuizCard from "../../components/quiz/StudentQuizCard";

import { getQuizzesByCategory } from "../../services/quizService";

import "../../styles/quiz/StudentQuizList.css";

function StudentQuizList() {
    const { categoryId } = useParams();
    const [quizzes, setQuizzes] = useState([]);
    const fetchQuizzes = async () => {

        try {
            const data = await getQuizzesByCategory(
                categoryId,
            );
            setQuizzes(data);
        }
        catch (error) {
            console.error(
                "Failed to fetch quizzes:",
                error,
            );
        }
    };

    useEffect(() => {
        fetchQuizzes();
    }, [categoryId]);

    return (
        <DashboardLayout>
            <div className="student-quiz-page">
                <h1>
                    Available Quizzes
                </h1>
                <p className="student-quiz-description">
                    Select a quiz to begin your assessment.
                </p>
                <div className="student-quiz-grid">
                    {
                        quizzes.map((quiz) => (
                            <StudentQuizCard
                                key={quiz.id}
                                quiz={quiz}
                            />
                        ))
                    }
                </div>
            </div>
        </DashboardLayout>
    );
}
export default StudentQuizList;
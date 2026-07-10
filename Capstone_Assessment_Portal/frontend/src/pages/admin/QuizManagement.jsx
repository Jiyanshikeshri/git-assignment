import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import QuizTable from "../../components/quiz/QuizTable";

import { getQuizzesByCategory } from "../../services/quizService";

function QuizManagement() {
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
            <div className="quiz-page">
                <h1>
                    Quiz Management
                </h1>
                <p>
                    Total Quizzes : {quizzes.length}
                </p>
                <QuizTable
                    quizzes={quizzes}
                    onEdit={() => {}}
                    onDelete={() => {}}
                />
            </div>
        </DashboardLayout>
    );
}

export default QuizManagement;
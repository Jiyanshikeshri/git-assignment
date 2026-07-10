import { useEffect, useState } from "react";

import DashboardLayout from "../../layouts/DashboardLayout";
import AllQuizTable from "../../components/quiz/AllQuizTable";

import { getAllQuizzes } from "../../services/quizService";

function AllQuizzes() {
    const [quizzes, setQuizzes] = useState([]);
    const fetchQuizzes = async () => {

        try {
            const data = await getAllQuizzes();
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
    }, []);

    return (

        <DashboardLayout>
            <div className="quiz-page">
                <h1>
                    Quiz Management
                </h1>
                <p>
                    Browse all quizzes available in the assessment portal.
                </p>
                <AllQuizTable
                    quizzes={quizzes}
                />
            </div>
        </DashboardLayout>
    );
}

export default AllQuizzes;
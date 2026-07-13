import { useEffect, useState } from "react";

import DashboardLayout from "../../pages/layouts/DashboardLayout";
import AllQuizTable from "../../components/quiz/AllQuizTable";
import Pagination from "../../components/common/Pagination";

import { getAllQuizzes } from "../../services/quizService";

function AllQuizzes() {
    const [quizzes, setQuizzes] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const fetchQuizzes = async () => {

        try {
            const data = await getAllQuizzes();
            setQuizzes(data);
            setCurrentPage(1);
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

    const indexOfLastItem =
        currentPage * itemsPerPage;

    const indexOfFirstItem =
        indexOfLastItem - itemsPerPage;

    const currentQuizzes =
        quizzes.slice(
            indexOfFirstItem,
            indexOfLastItem,
        );

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
                    quizzes={currentQuizzes}
                />
                <Pagination
                    currentPage={currentPage}
                    totalItems={quizzes.length}
                    itemsPerPage={itemsPerPage}
                    onPageChange={setCurrentPage}
                />
            </div>
        </DashboardLayout>
    );
}

export default AllQuizzes;
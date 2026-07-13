/**
 * Admin Results Page
 * Displays all student quiz results
 */

import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";
import Pagination from "../../components/common/Pagination";

import { getAllResults } from "../../services/resultService";

import "../../styles/admin/AdminResults.css";

function AdminResults() {

    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(true);

    const [selectedCategory, setSelectedCategory] = useState("All");
    const [selectedQuiz, setSelectedQuiz] = useState("All");
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const navigate = useNavigate();

    useEffect(() => {
        fetchResults();
    }, []);

    const fetchResults = async () => {
        try {
            const data = await getAllResults();
            setResults(data);
            setCurrentPage(1);
        }
        catch (error) {
            console.error(
                "Failed to fetch results:",
                error,
            );
        }
        finally {
            setLoading(false);
        }
    };

    const categories = useMemo(() => {
        return [
            "All",
            ...new Set(
                results.map(
                    (result) => result.category_name,
                ),
            ),
        ];
    }, [results]);

    const quizzes = useMemo(() => {
        return [
            "All",
            ...new Set(
                results.map(
                    (result) => result.quiz_title,
                ),
            ),
        ];
    }, [results]);

    const filteredResults = results.filter((result) => {
        const categoryMatch =
            selectedCategory === "All" ||
            result.category_name === selectedCategory;
        const quizMatch =
            selectedQuiz === "All" ||
            result.quiz_title === selectedQuiz;
        return (
            categoryMatch &&
            quizMatch
        );
    });

    const indexOfLastItem =
        currentPage * itemsPerPage;

    const indexOfFirstItem =
        indexOfLastItem - itemsPerPage;

    const currentResults =
        filteredResults.slice(
            indexOfFirstItem,
            indexOfLastItem,
        );

    if (loading) {
        return (
            <DashboardLayout>
                <h2>
                    Loading Results...
                </h2>
            </DashboardLayout>
        );
    }

    return (
        <DashboardLayout>
            <div className="admin-results-page">
                <h1>
                    Student Results
                </h1>
                <div className="result-filters">
                    <select
                        value={selectedCategory}
                        onChange={(e) => {
                            setSelectedCategory(
                                e.target.value,
                            );
                            setCurrentPage(1);
                        }}
                    >
                        <option value="All">
                            Filter by Category
                        </option>
                        {
                            categories
                                .filter(category => category !== "All")
                                .map(category => (
                                    <option
                                        key={category}
                                        value={category}
                                    >
                                        {category}
                                    </option>
                                )
                            )      
                        }
                    </select>
                    <select
                        value={selectedQuiz}
                        onChange={(e) => {
                            setSelectedQuiz(
                                e.target.value,
                            );
                            setCurrentPage(1);
                        }}
                    >
                        <option value="All">
                            Filter by Quiz
                        </option>
                        {
                            quizzes
                                .filter(quiz => quiz !== "All")
                                .map((quiz) => (
                                    <option
                                        key={quiz}
                                        value={quiz}
                                    >
                                        {quiz}
                                    </option>
                                )
                            )
                        }
                    </select>
                </div>

                <table className="admin-results-table">
                    <thead>
                        <tr>
                            <th>Student</th>
                            <th>Email</th>
                            <th>Quiz</th>
                            <th>Category</th>
                            <th>Score</th>
                            <th>Percentage</th>
                            <th>Status</th>
                            <th>Submitted</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            currentResults.map((result) => (
                                <tr key={result.id}>
                                    <td>
                                        {result.student_name}
                                    </td>
                                    <td>
                                        {result.student_email}
                                    </td>
                                    <td>
                                        {result.quiz_title}
                                    </td>
                                    <td>
                                        {result.category_name}
                                    </td>
                                    <td>
                                        {result.score}
                                    </td>
                                    <td>
                                        {result.percentage}%
                                    </td>
                                    <td>
                                        <span
                                            className={
                                                result.result_status === "PASS"
                                                    ? "pass-badge"
                                                    : "fail-badge"
                                            }
                                        >
                                            {result.result_status}
                                        </span>
                                    </td>
                                    <td>
                                        {
                                            new Date(
                                                result.submitted_at + "Z",
                                            ).toLocaleString(
                                                "en-IN",
                                                {
                                                    day: "2-digit",
                                                    month: "2-digit",
                                                    year: "numeric",
                                                    hour: "numeric",
                                                    minute: "2-digit",
                                                    hour12: true,
                                                },
                                            )
                                        }
                                    </td>
                                    <td>
                                        <button
                                            className="view-btn"
                                            onClick={() =>
                                                navigate(
                                                    `/admin/results/${result.id}`,
                                                )
                                            }
                                        >
                                            View Details
                                        </button>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
                <Pagination
                    currentPage={currentPage}
                    totalItems={filteredResults.length}
                    itemsPerPage={itemsPerPage}
                    onPageChange={setCurrentPage}
                />
            </div>
        </DashboardLayout>
    );
}

export default AdminResults;
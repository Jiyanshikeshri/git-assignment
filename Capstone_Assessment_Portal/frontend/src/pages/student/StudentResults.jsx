/**
 * Student Result Page
 */

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";

import { getResultHistory } from "../../services/resultService";

import "../../styles/student/StudentResults.css"

function StudentResults() {

    const [results, setResults] = useState([]);
    const [loading, setLoading] = useState(true);

    const navigate = useNavigate();

    useEffect(() => {
        loadResults();
    }, []);

    const loadResults = async () => {

        try {

            const data = await getResultHistory();

            setResults(data);

        }
        catch (error) {

            console.error(
                "Failed to load results:",
                error,
            );

        }
        finally {

            setLoading(false);

        }

    };

    if (loading) {

        return (

            <DashboardLayout>

                <h2>Loading Results...</h2>

            </DashboardLayout>

        );

    }

    return (

        <DashboardLayout>
            <div className="results-container">

                <h1 className="results-title">My Results</h1>

                {
                    results.length === 0 ?

                        (

                            <p>No Results Found</p>

                        )

                        :

                        (

                            <table className="results-table">

                                <thead>

                                    <tr>

                                        <th>Quiz</th>
                                        <th>Score</th>
                                        <th>Percentage</th>
                                        <th>Status</th>
                                        <th>Submitted</th>
                                        <th>Action</th>

                                    </tr>

                                </thead>

                                <tbody>

                                    {
                                        results.map((result) => (

                                            <tr key={result.id}>

                                                <td>{result.quiz_title}</td>

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
                                                        new Date(result.submitted_at + "Z")
                                                            .toLocaleString("en-IN", {
                                                                day: "2-digit",
                                                                month: "2-digit",
                                                                year: "numeric",
                                                                hour: "numeric",
                                                                minute: "2-digit",
                                                                hour12: true,
                                                            })
                                                    }
                                                </td>

                                                <td>

                                                    <button
                                                        className="view-btn"
                                                        onClick={() =>
                                                            navigate(
                                                                `/student/results/${result.id}`,
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

                        )

                }
            </div>
        </DashboardLayout>
    );

}

export default StudentResults;
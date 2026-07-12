/**
 * Student Quiz Attempt Page
 */

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    useParams,
    useLocation,
} from "react-router-dom";

import DashboardLayout from "../../layouts/DashboardLayout";

import {
    startAttempt,
    getAttempt,
    saveAnswer,
    submitAttempt,
} from "../../services/attemptService";

import "../../styles/student/StudentQuizAttempt.css"

function StudentQuizAttempt() {

    const { quizId } = useParams();
    const location = useLocation();

    const [attemptId, setAttemptId] = useState("");
    const [questions, setQuestions] = useState([]);
    const [currentQuestion, setCurrentQuestion] = useState(0);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const [remainingTime, setRemainingTime] = useState(0);
    const [showSubmitModal, setShowSubmitModal] = useState(false);

    const handleAutoSubmit = async () => {
        try {
            await submitAttempt(
                attemptId,
            );
            navigate(
                "/student/results",
            );
        }
        catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        loadQuiz();
    }, []);

    useEffect(() => {
        if (
            remainingTime <= 0 ||
            !attemptId
        ) {
            return;
        }
        const timer = setInterval(() => {
            setRemainingTime(
                (prev) => {
                    if (prev <= 1) {
                        clearInterval(timer);
                        handleAutoSubmit();
                        return 0;
                    }
                    return prev - 1;
                },
            );
        }, 1000);
        return () => clearInterval(timer);
    }, [
        remainingTime,
        attemptId,
    ]);

    const handleSubmit = async () => {
        try {
            await submitAttempt(
                attemptId,
            );
            navigate(
                "/student/results",
            );
        }
        catch (error) {
            console.error(error);
        }
    };

    const loadQuiz = async () => {
        try {
            const attempt = await startAttempt(
                quizId,
            );
            setAttemptId(
                attempt.attempt_id,
            );
            const data = await getAttempt(
                attempt.attempt_id,
            );
            setQuestions(
                data.questions,
            );
            setRemainingTime(
                data.remaining_time,
            );
        }
        catch (error) {
            console.error(
                "Failed to load quiz:",
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
                <h2>
                    Loading Quiz...
                </h2>
            </DashboardLayout>
        );

    }

    if (questions.length === 0) {
        return (
            <DashboardLayout>
                <h2>
                    No Questions Found
                </h2>
            </DashboardLayout>
        );
    }

    const question = questions[currentQuestion];

    const handleAnswerSelect = async (
        answer,
    ) => {
        try {
            await saveAnswer(
                attemptId,
                question.question_id,
                answer,
            );
            const updatedQuestions = [...questions];
            updatedQuestions[currentQuestion].selected_answer = answer;
            setQuestions(
                updatedQuestions,
            );
        }
        catch (error) {
            console.error(error);
        }
    };

    const handleNext = () => {
        if (
            currentQuestion < questions.length - 1
        ) {
            setCurrentQuestion(
                currentQuestion + 1,
            );
        }
    };

    const handlePrevious = () => {
        if (
            currentQuestion > 0
        ) {
            setCurrentQuestion(
                currentQuestion - 1,
            );
        }
    };

    return (
        <DashboardLayout>
            <div className="quiz-header">
                <h1>{location.state?.title}</h1>

                <div className="timer-box">
                    ⏱ {Math.floor(remainingTime / 60)}:
                    {String(remainingTime % 60).padStart(2, "0")}
                </div>
            </div>
            <p className="question-count">
                Question {currentQuestion + 1} / {questions.length}
            </p>

            <div className="progress-container">
                <div
                    className="progress-bar"
                    style={{
                        width: `${
                            ((currentQuestion + 1) /
                                questions.length) *
                            100
                        }%`,
                    }}
                ></div>
            </div>

            <h2>
                {question.question_text}
            </h2>

            {
                question.options.map((option, index) => (

                    <div
                        key={index}
                        className={
                            question.selected_answer === option
                                ? "option-card selected"
                                : "option-card"
                        }
                        style={{
                            marginBottom: "12px",
                        }}
                    >

                        <label className="option-label">

                            <input
                                type="radio"
                                name="answer"
                                checked={
                                    question.selected_answer === option
                                }
                                onChange={() =>
                                    handleAnswerSelect(option)
                                }
                            />

                            {" "}
                            {option}

                        </label>

                    </div>

                ))
            }

            <div className="quiz-buttons">

                <button
                    onClick={handlePrevious}
                    disabled={currentQuestion === 0}
                >
                    Previous
                </button>

                {
                    currentQuestion === questions.length - 1 ? (

                        <button
                            onClick={() =>
                                setShowSubmitModal(true)
                            }
                        >
                            Submit Quiz
                        </button>

                    ) : (

                        <button
                            onClick={handleNext}
                        >
                            Next
                        </button>

                    )
                }

            </div>

            {
                showSubmitModal && (
                    <div className="modal-overlay">
                        <div className="submit-modal">
                            <h2>
                                Submit Quiz?
                            </h2>
                            <p>
                                Are you sure you want to submit your quiz?
                                <br />
                                You will not be able to change your answers afterwards.
                            </p>
                            <div className="modal-buttons">
                                <button
                                    className="cancel-btn"
                                    onClick={() =>
                                        setShowSubmitModal(false)
                                    }
                                >
                                    Cancel
                                </button>

                                <button
                                    className="submit-btn"
                                    onClick={handleSubmit}
                                >
                                    Submit
                                </button>
                            </div>
                        </div>
                    </div>
                )
            }
        </DashboardLayout>
    );
}

export default StudentQuizAttempt;
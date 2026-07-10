/**
 * Student Quiz Card
 * Displays a quiz with a Start Quiz button
 */

import { useNavigate } from "react-router-dom";
import { FaClock, FaArrowRight } from "react-icons/fa";

import "../../styles/quiz/StudentQuizCard.css";

function StudentQuizCard({
    quiz,
}) {

    const navigate = useNavigate();
    const handleStartQuiz = () => {
        navigate(
            `/student/quizzes/${quiz.id}`,
        );
    };

    return (
        <div className="student-quiz-card">
            <h2>
                {quiz.title}
            </h2>
            <p>
                {quiz.description}
            </p>
            <div className="quiz-duration">
                <FaClock />
                <span>
                    {quiz.duration} Minutes
                </span>
            </div>
            <button
                className="start-quiz-btn"
                onClick={handleStartQuiz}
            >

                Start Quiz
                <FaArrowRight />
            </button>
        </div>
    );
}

export default StudentQuizCard;
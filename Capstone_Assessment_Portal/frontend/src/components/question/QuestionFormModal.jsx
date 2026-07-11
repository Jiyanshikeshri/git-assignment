/**
 * Reusable Question Form Modal
 * Used for creating and updating questions
 */

import { useState } from "react";

import { createQuestion } from "../../services/questionService";

import "../../styles/question/QuestionFormModal.css";

function QuestionFormModal({

    isOpen,
    onClose,
    quizId,
    onQuestionCreated,

}) {

    const [loading, setLoading] = useState(false);
    const [errors, setErrors] = useState({});
    const [serverError, setServerError] = useState("");
    const [formData, setFormData] = useState({
        question_text: "",
        question_type: "MCQ",

        options: [
            "",
            "",
            "",
            "",
        ],

        correct_answer: "",
        difficulty: "EASY",
        tags: "",
    });

    const resetForm = () => {
        setFormData({
            question_text: "",
            question_type: "MCQ",
            options: [
                "",
                "",
                "",
                "",
            ],
            correct_answer: "",
            difficulty: "EASY",
            tags: "",
        });

        setErrors({});
        setServerError("");

    };

    /**
     * Validate question form
     */
    const validateForm = () => {
        const validationErrors = {};
        if (!formData.question_text.trim()) {
            validationErrors.question_text =
                "Question is required.";

        }

        else if (
            formData.question_text.trim().length < 5
        ) {
            validationErrors.question_text =
                "Question must be at least 5 characters.";
        }

        if (
            formData.question_type === "MCQ"
        ) {
            formData.options.forEach(
                (option, index) => {
                    if (!option.trim()) {
                        validationErrors[
                            `option${index}`
                        ] =
                            `Option ${index + 1} is required.`;
                    }
                },
            );
        }

        if (!formData.correct_answer) {
            validationErrors.correct_answer =
                "Please select the correct answer.";
        }
        setErrors(validationErrors);
        return (
            Object.keys(validationErrors)
                .length === 0
        );
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!validateForm()) {
            return;
        }
        setLoading(true);
        setServerError("");
        try {
            const payload = {
                quiz_id: quizId,
                question_text:
                    formData.question_text.trim(),
                question_type:
                    formData.question_type,
                options:
                    formData.question_type ===
                    "MCQ"
                        ? formData.options
                        : [
                            "True",
                            "False",
                        ],
                correct_answer:
                    formData.correct_answer,
                difficulty:
                    formData.difficulty,
                tags:
                    formData.tags
                        ? formData.tags
                            .split(",")
                            .map(
                                (tag) =>
                                    tag.trim(),
                            )
                        : [],

            };

            await createQuestion(payload);
            resetForm();
            onQuestionCreated();
        }

        catch (error) {
            setServerError(
                error.response?.data?.detail ||
                "Failed to create question.",
            );

        }
        finally {
            setLoading(false);
        }
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div
            className="question-modal-overlay"
            onClick={() => {
                resetForm();
                onClose();
            }}

        >
            <div
                className="question-modal"
                onClick={(event) =>
                    event.stopPropagation()
                }
            >
                <div className="question-modal-header">
                    <h2>
                        Add Question
                    </h2>
                </div>
                <form
                    className="question-form"
                    onSubmit={handleSubmit}
                >
                    <div className="form-group">
                        <label>
                            Question
                        </label>
                        <textarea
                            placeholder="Enter question"
                            value={
                                formData.question_text
                            }
                            onChange={(event) => {
                                setFormData({
                                    ...formData,
                                    question_text:
                                        event.target.value,
                                });

                                setErrors({
                                    ...errors,
                                    question_text: "",
                                });
                            }}
                        />
                        {
                            errors.question_text && (
                                <p className="form-error">
                                    {
                                        errors.question_text
                                    }
                                </p>
                            )
                        }
                    </div>
                    <div className="form-group">
                        <label>
                            Question Type
                        </label>
                        <select
                            value={
                                formData.question_type
                            }
                            onChange={(event) => {
                                const type =
                                    event.target.value;

                                setFormData({
                                    ...formData,
                                    question_type: type,
                                    options:
                                        type === "MCQ"

                                            ? [
                                                "",
                                                "",
                                                "",
                                                "",
                                            ]

                                            : [
                                                "True",
                                                "False",
                                            ],
                                    correct_answer: "",
                                });
                            }}
                        >
                            <option value="MCQ">
                                MCQ
                            </option>
                            <option value="TRUE_FALSE">
                                TRUE / FALSE
                            </option>
                        </select>
                    </div>

                                        {
                        formData.question_type ===
                        "MCQ" && (
                            <div className="form-group">
                                <label>
                                    Options
                                </label>
                                <div className="options-grid">
                                    {
                                        formData.options.map(

                                            (
                                                option,
                                                index,
                                            ) => (

                                                <div key={index}>

                                                    <input
                                                        type="text"
                                                        placeholder={`Option ${index + 1}`}
                                                        value={option}
                                                        onChange={(event) => {

                                                            const updatedOptions = [
                                                                ...formData.options,
                                                            ];

                                                            updatedOptions[index] =
                                                                event.target.value;

                                                            setFormData({
                                                                ...formData,
                                                                options:
                                                                    updatedOptions,
                                                            });

                                                            setErrors({
                                                                ...errors,
                                                                [`option${index}`]: "",
                                                            });
                                                        }}
                                                    />

                                                    {
                                                        errors[`option${index}`] && (
                                                            <p className="form-error">
                                                                {errors[`option${index}`]}
                                                            </p>
                                                        )
                                                    }

                                                </div>
                                            ),
                                        )
                                    }
                                </div>
                            </div>
                        )
                    }
                    <div className="form-group">
                        <label>
                            Correct Answer
                        </label>

                        <select
                            value={formData.correct_answer}
                            onChange={(event) => {
                                setFormData({
                                    ...formData,
                                    correct_answer:
                                        event.target.value,

                                });

                                setErrors({
                                    ...errors,
                                    correct_answer: "",
                                });
                            }}
                        >

                            <option value="">
                                Select Correct Answer
                            </option>
                            {
                                formData.question_type ===
                                "MCQ"
                                    ? formData.options.map(
                                        (
                                            option,
                                            index,
                                        ) => (

                                            <option
                                                key={index}
                                                value={option}
                                            >

                                                {
                                                    option ||
                                                    `Option ${index + 1}`
                                                }

                                            </option>

                                        ),
                                    )
                                    : (
                                        <>
                                            <option value="True">
                                                True
                                            </option>
                                            <option value="False">
                                                False
                                            </option>
                                        </>
                                    )
                            }
                        </select>
                        {
                            errors.correct_answer && (
                                <p className="form-error">
                                    {errors.correct_answer}
                                </p>
                            )
                        }

                    </div>

                    <div className="form-group">

                        <label>
                            Difficulty
                        </label>

                        <select
                            value={formData.difficulty}
                            onChange={(event) =>
                                setFormData({

                                    ...formData,

                                    difficulty:
                                        event.target.value,

                                })
                            }
                        >

                            <option value="EASY">
                                EASY
                            </option>

                            <option value="MEDIUM">
                                MEDIUM
                            </option>

                            <option value="HARD">
                                HARD
                            </option>

                        </select>
                    </div>
                    <div className="form-group">
                        <label>
                            Tags
                        </label>

                        <input
                            type="text"
                            placeholder="arrays, loops, basics"
                            value={formData.tags}
                            onChange={(event) =>
                                setFormData({

                                    ...formData,

                                    tags:
                                        event.target.value,

                                })
                            }
                        />

                    </div>
                    {
                        serverError && (
                            <p className="server-error">
                                {serverError}
                            </p>
                        )
                    }

                    <div className="modal-actions">
                        <button
                            type="button"
                            className="cancel-btn"
                            onClick={() => {
                                resetForm();
                                onClose();
                            }}
                            disabled={loading}
                        >
                            Cancel
                        </button>
                        <button
                            type="submit"
                            className="save-btn"
                            disabled={loading}
                        >
                            {
                                loading
                                    ? "Saving..."
                                    : "Save Question"
                            }
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default QuestionFormModal;
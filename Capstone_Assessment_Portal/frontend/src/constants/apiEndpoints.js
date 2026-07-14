export const API_ENDPOINTS = {
    AUTH: {
        REGISTER: "/auth/register",
        LOGIN: "/auth/login",
        REFRESH: "/auth/refresh",
        PUBLIC_KEY: "/auth/public-key",
    },
    CATEGORIES: "/categories",
    DASHBOARD: {
        ADMIN: "/dashboard/admin",
        STUDENT: "/dashboard/student",
    },
    ATTEMPTS: {
        START: "/attempts/start",
        BASE: "/attempts",
    },
    QUESTIONS: {
        BASE: "/questions",
        QUIZ: "/questions/quiz",
    },
    QUIZZES: {
        BASE: "/quizzes",
        CATEGORY: "/quizzes/category",
    },
    RESULTS: {
        BASE: "/results",
        LATEST: "/results/latest",
        HISTORY: "/results/history",
    },
};
/**
 * Global Authentication Context for the Assessment Portal
 */

import { createContext, useContext, useState } from "react";
import {
    saveTokens,
    removeTokens,
    getAccessToken,
    getRefreshToken,
} from "../utils/auth";

/**
 * Global Authentication Context
 */
const AuthContext = createContext();

/**
 * Authentication Provider
 */
export function AuthProvider({ children }) {

    const [user, setUser] = useState(null);

    const [accessToken, setAccessToken] = useState(
        getAccessToken()
    );

    const [refreshToken, setRefreshToken] = useState(
        getRefreshToken()
    );

    /**
     * Login Function
     */
    const login = ({
        accessToken,
        refreshToken,
        user,
    }) => {

        saveTokens(
            accessToken,
            refreshToken
        );

        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setUser(user);
    };

    /**
     * Logout Function
     */
    const logout = () => {

        removeTokens();

        setAccessToken(null);
        setRefreshToken(null);
        setUser(null);
    };

    /**
     * Context Value
     */
    const value = {
        user,
        accessToken,
        refreshToken,
        login,
        logout,
        isAuthenticated: !!accessToken,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}

/**
 * Custom Hook
 */
export function useAuth() {

    return useContext(AuthContext);
}
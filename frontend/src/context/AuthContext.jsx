import { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/api';

const AuthContext = createContext(null);

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        checkAuth();
    }, []);

    const checkAuth = async () => {
        const token = localStorage.getItem('accessToken');
        if (token) {
            try {
                const response = await authService.validate();
                if (response.data.valid) {
                    setUser(response.data.claims);
                } else {
                    logout();
                }
            } catch (error) {
                logout();
            }
        }
        setLoading(false);
    };

    const login = async (username, password) => {
        try {
            const response = await authService.login({ username, password });
            const data = response.data;

            if (data.AuthenticationResult) {
                const { IdToken, AccessToken, RefreshToken } = data.AuthenticationResult;
                localStorage.setItem('accessToken', AccessToken);
                localStorage.setItem('idToken', IdToken);
                localStorage.setItem('refreshToken', RefreshToken);
                localStorage.setItem('username', username);

                // Decode token to get user info
                const payload = JSON.parse(atob(IdToken.split('.')[1]));
                setUser(payload);
                return { success: true };
            }
            return { success: false, error: 'Credenciais inválidas' };
        } catch (error) {
            return {
                success: false,
                error: error.response?.data?.message || 'Erro ao fazer login'
            };
        }
    };

    const logout = () => {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('idToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('username');
        setUser(null);
    };

    const value = {
        user,
        loading,
        login,
        logout,
        isAuthenticated: !!user,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};

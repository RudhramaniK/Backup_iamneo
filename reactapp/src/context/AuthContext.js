// src/context/AuthContext.js
import React, { createContext, useState, useContext, useEffect } from 'react';
import { authService } from '../services/api'; 
import { parseJwt } from '../utils/jwt';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkAuthStatus = async () => {
      const token = localStorage.getItem('token');
      if (token) {
        try {
          const response = await authService.getCurrentUser();
          const userData = response.data;
          const decodedToken = parseJwt(token);
          const roleFromToken = decodedToken.role || '';
          const role = roleFromToken.startsWith('ROLE_') ? roleFromToken.replace('ROLE_', '') : roleFromToken;
          setCurrentUser({
            ...userData,
            token,
            role, // Set role without ROLE_ prefix if present
            creatorName: userData.name || decodedToken.name,
          });
        } catch (error) {
          console.error('Auth check failed:', error.response ? error.response.data : error.message);
          localStorage.removeItem('token');
        }
      }
      setLoading(false);
    };

    checkAuthStatus();
  }, []);

  const login = async (credentials) => {
    setLoading(true);
    try {
      const response = await authService.login(credentials);
      const token = typeof response.data.data === 'string'
        ? response.data.data
        : response.data.data.token;
      localStorage.setItem('token', token);
      const decodedToken = parseJwt(token);
      const userData = typeof response.data.data === 'object'
        ? response.data.data
        : decodedToken;
      const roleFromToken = decodedToken.role || '';
      const role = roleFromToken.startsWith('ROLE_') ? roleFromToken.replace('ROLE_', '') : roleFromToken;
      setCurrentUser({
        ...userData,
        token,
        role, // Set role without ROLE_ prefix
        creatorName: userData.name || decodedToken.name,
      });
      setLoading(false);
      return true;
    } catch (error) {
      setLoading(false);
      console.error('Login failed:', error.response ? error.response.data : error.message);
      throw error;
    }
  };

  const register = async (userData) => {
    setLoading(true);
    try {
      const response = await authService.register(userData);
      const token = typeof response.data.data === 'string'
        ? response.data.data
        : response.data.data.token;
      localStorage.setItem('token', token);
      const decodedToken = parseJwt(token);
      const userDataFromResponse = typeof response.data.data === 'object'
        ? response.data.data
        : decodedToken;
      const roleFromToken = decodedToken.role || '';
      const role = roleFromToken.startsWith('ROLE_') ? roleFromToken.replace('ROLE_', '') : roleFromToken;
      setCurrentUser({
        ...userDataFromResponse,
        token,
        role, // Set role without ROLE_ prefix
        creatorName: userDataFromResponse.name || decodedToken.name,
      });
      setLoading(false);
      return true;
    } catch (error) {
      setLoading(false);
      console.error('Registration failed:', error.response ? error.response.data : error.message);
      throw error;
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setCurrentUser(null);
  };

  const value = {
    currentUser,
    login,
    register,
    logout,
    loading
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
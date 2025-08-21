// src/services/api.js
import axios from 'axios';

const API_BASE_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests - FIXED to not add token for auth routes
api.interceptors.request.use(
  (config) => {
    if (!config.url.includes('/auth/')) {
      const token = localStorage.getItem('token');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
        console.log('Sending request with token:', token); // Debug log
      } else {
        console.warn('No token found in localStorage');
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Handle response errors
api.interceptors.response.use(
  (response) => {
    console.log('✅ API Response:', {
      url: response.config.url,
      status: response.status,
      data: response.data
    });
    return response;
  },
  (error) => {
    console.error('❌ API Error:', {
      url: error.config?.url,
      status: error.response?.status,
      data: error.response?.data,
      message: error.message
    });
    
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth API calls
export const authService = {
  login: (credentials) => api.post('/auth/login', credentials),
  register: (userData) => api.post('/auth/register', userData),
  getCurrentUser: () => api.get('/auth/me'),
};

// Poll API calls
export const pollService = {
  getAllPolls: () => api.get('/polls'),
  getPollById: (id) => api.get(`/polls/${id}`),
  createPoll: (pollData) => api.post('/polls', pollData),
  vote: (pollId, optionId) => api.post(`/polls/${pollId}/votes`, { optionId }),
  getUserPolls: () => api.get('/polls/my-polls'),
  closePoll: (id) => api.post(`/polls/${id}/close`), 
  updatePoll: (id, pollData) => api.put(`/polls/${id}`, pollData),
};

// Admin API calls
export const adminService = {
  getAllUsers: () => api.get('/admin/users'),
  deleteUser: (id) => api.delete(`/admin/users/${id}`),
  toggleUserStatus: (id) => api.patch(`/admin/users/${id}/status`),
  getAllPollsAdmin: () => api.get('/admin/polls'),
  deletePollAdmin: (id) => api.delete(`/admin/polls/${id}`),
};

export default api;
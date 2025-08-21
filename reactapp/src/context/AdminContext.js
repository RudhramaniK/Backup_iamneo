import React, { createContext, useState, useContext } from 'react';
import { adminService } from '../services/api';

const AdminContext = createContext();

export const useAdmin = () => {
  const context = useContext(AdminContext);
  if (!context) {
    throw new Error('useAdmin must be used within an AdminProvider');
  }
  return context;
};

export const AdminProvider = ({ children }) => {
  const [users, setUsers] = useState([]);
  const [polls, setPolls] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Fetch all users
  const fetchUsers = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await adminService.getAllUsers();
      setUsers(response.data);
      return { success: true };
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'Failed to fetch users';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    } finally {
      setLoading(false);
    }
  };

  // Fetch all polls for admin
  const fetchPolls = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await adminService.getAllPollsAdmin();
      setPolls(response.data);
      return { success: true };
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'Failed to fetch polls';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    } finally {
      setLoading(false);
    }
  };

  // Delete a user
  const deleteUser = async (userId) => {
    setError('');
    try {
      await adminService.deleteUser(userId);
      setUsers(users.filter(user => user.id !== userId));
      return { success: true };
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'Failed to delete user';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    }
  };

  // Toggle user status (enable/disable)
  const toggleUserStatus = async (userId) => {
    setError('');
    try {
      await adminService.toggleUserStatus(userId);
      // Refresh users list to get updated status
      await fetchUsers();
      return { success: true };
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'Failed to update user status';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    }
  };

  // Delete a poll (admin)
  const deletePoll = async (pollId) => {
    setError('');
    try {
      await adminService.deletePollAdmin(pollId);
      setPolls(polls.filter(poll => poll.id !== pollId));
      return { success: true };
    } catch (err) {
      const errorMsg = err.response?.data?.message || 'Failed to delete poll';
      setError(errorMsg);
      return { success: false, message: errorMsg };
    }
  };

  // Clear error
  const clearError = () => setError('');

  const value = {
    users,
    polls,
    loading,
    error,
    fetchUsers,
    fetchPolls,
    deleteUser,
    toggleUserStatus,
    deletePoll,
    clearError
  };

  return (
    <AdminContext.Provider value={value}>
      {children}
    </AdminContext.Provider>
  );
};
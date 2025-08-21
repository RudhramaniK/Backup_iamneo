import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { adminService, pollService } from '../services/api';

const AdminPanel = () => {
  const { currentUser } = useAuth();
  const [users, setUsers] = useState([]);
  const [polls, setPolls] = useState([]);
  const [activeTab, setActiveTab] = useState('users');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (currentUser?.role === 'ADMIN') {
      fetchData();
    }
  }, [currentUser]);

  const fetchData = async () => {
    try {
      const [usersResponse, pollsResponse] = await Promise.all([
        adminService.getAllUsers(),
        adminService.getAllPollsAdmin()
      ]);
      setUsers(usersResponse.data);
      setPolls(pollsResponse.data);
    } catch (error) {
      console.error('Error fetching admin data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteUser = async (userId) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        await adminService.deleteUser(userId);
        setUsers(users.filter(user => user.id !== userId));
      } catch (error) {
        console.error('Error deleting user:', error);
        alert('Failed to delete user. Please try again.');
      }
    }
  };

  const handleToggleUserStatus = async (userId) => {
    try {
      await adminService.toggleUserStatus(userId);
      // Refresh users list
      const response = await adminService.getAllUsers();
      setUsers(response.data);
    } catch (error) {
      console.error('Error toggling user status:', error);
      alert('Failed to update user status. Please try again.');
    }
  };

  const handleDeletePoll = async (pollId) => {
    if (window.confirm('Are you sure you want to delete this poll?')) {
      try {
        await pollService.closePoll(pollId);
        setPolls(polls.filter(poll => poll.id !== pollId));
      } catch (error) {
        console.error('Error deleting poll:', error);
        alert('Failed to delete poll. Please try again.');
      }
    }
  };

  if (!currentUser || currentUser.role !== 'ADMIN') {
    return (
      <div className="container">
        <div className="notification is-danger">
          You do not have permission to access this page.
        </div>
      </div>
    );
  }

  if (loading) {
    return <div className="container">Loading admin data...</div>;
  }

  return (
    <div className="admin-panel">
      <div className="container">
        <h1 className="title">Admin Panel</h1>

        <div className="tabs">
          <ul>
            <li className={activeTab === 'users' ? 'is-active' : ''}>
              <a onClick={() => setActiveTab('users')}>Users ({users.length})</a>
            </li>
            <li className={activeTab === 'polls' ? 'is-active' : ''}>
              <a onClick={() => setActiveTab('polls')}>Polls ({polls.length})</a>
            </li>
          </ul>
        </div>

        {activeTab === 'users' && (
          <div className="table-container">
            <table className="table is-fullwidth is-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map(user => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                    <td>{user.role}</td>
                    <td>{user.enabled ? 'Active' : 'Disabled'}</td>
                    <td>
                      <div className="buttons are-small">
                        <button
                          className={`button ${user.enabled ? 'is-warning' : 'is-success'}`}
                          onClick={() => handleToggleUserStatus(user.id)}
                        >
                          {user.enabled ? 'Disable' : 'Enable'}
                        </button>
                        <button
                          className="button is-danger"
                          onClick={() => handleDeleteUser(user.id)}
                          disabled={user.id === currentUser.id}
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}

        {activeTab === 'polls' && (
          <div className="table-container">
            <table className="table is-fullwidth is-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Question</th>
                  <th>Creator</th>
                  <th>Created At</th>
                  <th>Votes</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {polls.map(poll => (
                  <tr key={poll.id}>
                    <td>{poll.id}</td>
                    <td>{poll.question}</td>
                    <td>{poll.creatorName ? poll.creatorName : "Unknown"}</td>
                    <td>{new Date(poll.createdAt).toLocaleDateString()}</td>
                    <td>{poll.options.reduce((sum, opt) => sum + opt.voteCount, 0)}</td>
                    <td>
                      <button
                        className="button is-danger is-small"
                        onClick={() => handleDeletePoll(poll.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminPanel;
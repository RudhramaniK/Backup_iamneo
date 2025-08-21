// src/pages/Dashboard.js
import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { pollService } from '../services/api';
import PollCard from '../components/PollCard';
import { Link } from 'react-router-dom';
import LogoutButton from '../components/LogoutButton';

const Dashboard = () => {
  const { currentUser } = useAuth();
  const [userPolls, setUserPolls] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchUserPolls = async () => {
    try {
      const response = await pollService.getUserPolls();
      setUserPolls(response.data);
    } catch (error) {
      console.error('Error fetching user polls:', {
        message: error.message,
        status: error.response?.status,
        data: error.response?.data,
        config: error.config,
      });
      if (error.response?.status === 500) {
        alert('Server error occurred. Please try again later or contact support.');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUserPolls();
  }, []);

  const handleVote = async (pollId, optionId) => {
    try {
      console.log('Dashboard: Voting for option:', optionId, 'in poll:', pollId);
      await pollService.vote(pollId, optionId);
      console.log('Dashboard: Vote successful, refreshing polls');
      await fetchUserPolls();
    } catch (error) {
      console.error('Dashboard: Error voting:', error);
      const errorMessage = error.response?.data?.message || 'Failed to vote. Please try again.';
      alert(errorMessage);
      throw error; // Re-throw to let PollCard handle it
    }
  };

  const handleDelete = async (pollId) => {
    if (window.confirm('Are you sure you want to delete this poll?')) {
      try {
        await pollService.closePoll(pollId);
        setUserPolls(userPolls.filter(poll => poll.id !== pollId));
      } catch (error) {
        console.error('Error deleting poll:', error);
        alert('Failed to delete poll. Please try again.');
      }
    }
  };

  return (
    <div className="dashboard-page">
      <div className="container">
        <h1 className="title">Dashboard</h1>
        {currentUser ? (
          <p className="subtitle">Welcome back, {currentUser.creatorName || 'User'}!</p>
        ) : (
          <p className="subtitle">Loading user data...</p>
        )}

        <div className="buttons">
          <Link to="/create" className="button is-primary">
            Create New Poll
          </Link>
          <Link to="/polls" className="button is-info">
            View All Polls
          </Link>
          <LogoutButton />
        </div>

        <h2 className="title is-3 mt-5">Your Polls</h2>
        
        {loading ? (
          <div>Loading your polls...</div>
        ) : userPolls.length > 0 ? (
          <div className="columns is-multiline">
            {userPolls.map(poll => (
              <div key={poll.id} className="column is-one-third">
                <PollCard 
                  poll={poll} 
                  onVote={handleVote}
                  isOwner={true}
                  onDelete={handleDelete}
                />
              </div>
            ))}
          </div>
        ) : (
          <div className="notification is-info">
            You haven't created any polls yet. <Link to="/create">Create your first poll!</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
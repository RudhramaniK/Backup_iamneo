// src/pages/CreatePoll.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { pollService } from '../services/api';
import PollForm from '../components/PollForm';
import { useAuth } from '../context/AuthContext';

const CreatePoll = () => {
  const navigate = useNavigate();
  const { currentUser, loading } = useAuth();

  useEffect(() => {
    // Only redirect after loading is complete
    if (!loading && (!currentUser || currentUser.role !== 'CREATOR')) { // Fixed: should be CREATOR role
      navigate('/dashboard');
    }
  }, [currentUser, loading, navigate]);

  if (loading) {
    return <div>Loading...</div>;
  }

  // Don't render anything while redirecting
  if (!currentUser || currentUser.role !== 'CREATOR') { // Fixed: should be CREATOR role
    return null;
  }

  const handleSubmit = async (pollData) => {
    try {
      await pollService.createPoll(pollData);
      navigate('/dashboard');
    } catch (error) {
      console.error('Error creating poll:', error.response ? error.response.data : error.message);
      alert('Failed to create poll. Please try again.');
    }
  };

  return (
    <div className="columns is-centered">
      <div className="column is-half">
        <div className="card">
          <div className="card-content">
            <h1 className="title">Create New Poll</h1>
            <PollForm onSubmit={handleSubmit} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CreatePoll;
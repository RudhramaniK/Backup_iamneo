import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { pollService } from '../services/api';
import PollCard from '../components/PollCard';

const PollsList = () => {
  const { currentUser } = useAuth();
  const [polls, setPolls] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('all'); // all, voted, notVoted

  useEffect(() => {
    const fetchPolls = async () => {
      try {
        const response = await pollService.getAllPolls();
        setPolls(response.data);
      } catch (error) {
        console.error('Error fetching polls:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchPolls();
  }, []);

  const handleVote = async (pollId, optionId) => {
    try {
      console.log('PollsList: Voting for option:', optionId, 'in poll:', pollId);
      await pollService.vote(pollId, optionId);
      console.log('PollsList: Vote successful, refreshing polls');
      // Refresh the polls to show updated results
      const response = await pollService.getAllPolls();
      setPolls(response.data);
    } catch (error) {
      console.error('PollsList: Error voting:', error);
      const errorMessage = error.response?.data?.message || 'Failed to vote. Please try again.';
      alert(errorMessage);
      throw error; // Re-throw to let PollCard handle it
    }
  };

  const filteredPolls = polls.filter(poll => {
    if (!currentUser) return true;
    
    // For now, always show as not voted to avoid backend issues
    const hasVoted = false;
    
    if (filter === 'voted') {
      return hasVoted;
    } else if (filter === 'notVoted') {
      return !hasVoted;
    }
    return true;
  });

  return (
    <div className="polls-list-page">
      <div className="container">
        <h1 className="title">All Polls</h1>
        
        {currentUser && (
          <div className="tabs is-centered">
            <ul>
              <li className={filter === 'all' ? 'is-active' : ''}>
                <a onClick={() => setFilter('all')}>All Polls</a>
              </li>
              <li className={filter === 'voted' ? 'is-active' : ''}>
                <a onClick={() => setFilter('voted')}>Voted</a>
              </li>
              <li className={filter === 'notVoted' ? 'is-active' : ''}>
                <a onClick={() => setFilter('notVoted')}>Not Voted</a>
              </li>
            </ul>
          </div>
        )}

        {loading ? (
          <div>Loading polls...</div>
        ) : filteredPolls.length > 0 ? (
          <div className="columns is-multiline">
            {filteredPolls.map(poll => (
              <div key={poll.id} className="column is-one-third">
                <PollCard 
                  poll={poll} 
                  onVote={handleVote}
                />
              </div>
            ))}
          </div>
        ) : (
          <div className="notification is-info">
            {filter === 'all' 
              ? 'No polls available. Be the first to create one!' 
              : `No ${filter} polls available.`
            }
          </div>
        )}
      </div>
    </div>
  );
};

export default PollsList;
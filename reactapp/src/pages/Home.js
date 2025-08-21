import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { pollService } from '../services/api';
import PollCard from '../components/PollCard';

const Home = () => {
  const { currentUser } = useAuth();
  const [recentPolls, setRecentPolls] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRecentPolls = async () => {
      try {
        const response = await pollService.getAllPolls();
        setRecentPolls(response.data.slice(0, 3));
      } catch (error) {
        console.error('Error fetching polls:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchRecentPolls();
  }, []);

  const handleVote = async (pollId, optionId) => {
    try {
      await pollService.vote(pollId, optionId);
      // Refresh the polls to show updated results
      const response = await pollService.getAllPolls();
      setRecentPolls(response.data.slice(0, 3));
    } catch (error) {
      console.error('Error voting:', error);
      alert('Failed to vote. Please try again.');
    }
  };

  return (
    <div className="home-page">
      <section className="hero is-primary">
        <div className="hero-body">
          <div className="container">
            <h1 className="title">Welcome to PollApp</h1>
            <h2 className="subtitle">
              Create and share polls with your friends and community
            </h2>
            {!currentUser && (
              <div className="buttons">
                <Link to="/register" className="button is-light">
                  Get Started
                </Link>
                <Link to="/polls" className="button is-outlined is-light">
                  View Polls
                </Link>
              </div>
            )}
          </div>
        </div>
      </section>

      <section className="section">
        <div className="container">
          <h3 className="title is-3">Recent Polls</h3>
          
          {loading ? (
            <div>Loading polls...</div>
          ) : recentPolls.length > 0 ? (
            <div className="columns">
              {recentPolls.map(poll => (
                <div key={poll.id} className="column is-one-third">
                  <PollCard 
                    poll={poll} 
                    onVote={handleVote}
                  />
                </div>
              ))}
            </div>
          ) : (
            <div>No polls available. Be the first to create one!</div>
          )}

          <div className="has-text-centered mt-5">
            <Link to="/polls" className="button is-primary is-large">
              View All Polls
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;
import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import VoteModal from './VoteModal';
import ResultsChart from './ResultsChart';

const PollCard = ({ poll, onVote, showResults = false, isOwner = false, onDelete }) => {
  const { currentUser } = useAuth();
  const [showVoteModal, setShowVoteModal] = useState(false);
  const [showResultsChart, setShowResultsChart] = useState(showResults);

  const handleVoteClick = () => setShowVoteModal(true);

  const handleVote = async (optionId) => {
    try {
      console.log('Attempting to vote for option:', optionId, 'in poll:', poll.id);
      await onVote(poll.id, optionId);
      console.log('Vote successful, closing modal');
      setShowVoteModal(false);
    } catch (error) {
      console.error('Vote failed in PollCard:', error);
      // Don't close modal on error, let user try again
    }
  };

  const handleCloseModal = () => setShowVoteModal(false);

  const totalVotes = poll.options.reduce((sum, option) => sum + option.voteCount, 0);
  
  // Check if current user has voted on this poll
  // For now, always show as not voted to avoid backend issues
  const hasVoted = false;

  return (
    <div className="card poll-card">
      <div className="card-content">
        <div className="media">
          <div className="media-content">
            <p className="title is-4">{poll.question}</p>
            <p className="subtitle is-6">
              By {poll.creatorName ? poll.creatorName : "Unknown"} • {new Date(poll.createdAt).toLocaleDateString()}
            </p>
          </div>
          {isOwner && (
            <div className="media-right">
              <button className="delete" onClick={() => onDelete(poll.id)}></button>
            </div>
          )}
        </div>

        {showResultsChart || hasVoted ? (
          <ResultsChart options={poll.options} totalVotes={totalVotes} />
        ) : (
          <div className="content">
            <ul>
              {poll.options.map(option => (
                <li key={option.id}>{option.text}</li>
              ))}
            </ul>
          </div>
        )}

        <footer className="card-footer">
          {currentUser && !hasVoted && !showResultsChart && (
            <button 
              className="card-footer-item button is-primary" 
              onClick={handleVoteClick}
            >
              Vote
            </button>
          )}
          {(hasVoted || totalVotes > 0) && !showResultsChart && (
            <button 
              className="card-footer-item button is-text" 
              onClick={() => setShowResultsChart(true)}
            >
              View Results ({totalVotes} votes)
            </button>
          )}
        </footer>
      </div>

      {showVoteModal && (
        <VoteModal 
          poll={poll} 
          onClose={handleCloseModal} 
          onVote={handleVote} 
        />
      )}
    </div>
  );
};

export default PollCard;
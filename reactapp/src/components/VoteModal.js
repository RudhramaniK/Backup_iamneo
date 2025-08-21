import React, { useState } from 'react';

const VoteModal = ({ poll, onClose, onVote }) => {
  const [selectedOption, setSelectedOption] = useState(null);
  const [isVoting, setIsVoting] = useState(false);

  const handleVote = async () => {
    if (selectedOption && !isVoting) {
      setIsVoting(true);
      try {
        await onVote(selectedOption);
        onClose();
      } catch (error) {
        console.error('Vote failed:', error);
        // Show error to user
        alert('Vote failed: ' + (error.response?.data?.message || error.message || 'Unknown error'));
      } finally {
        setIsVoting(false);
      }
    }
  };

  return (
    <div className="modal is-active">
      <div className="modal-background" onClick={onClose}></div>
      <div className="modal-content">
        <div className="modal-header">
          <h2 className="title is-4">Vote: {poll.question}</h2>
          <button className="delete" onClick={onClose}></button>
        </div>
        
        <div className="modal-body">
          <div className="field">
            {poll.options.map(option => (
              <div key={option.id} className="control">
                <label className="radio">
                  <input
                    type="radio"
                    name="vote"
                    value={option.id}
                    checked={selectedOption === option.id}
                    onChange={() => setSelectedOption(option.id)}
                  />
                  <span className="ml-2">{option.text}</span>
                </label>
              </div>
            ))}
          </div>
        </div>
        
        <div className="modal-footer">
          <button 
            className="button is-primary" 
            onClick={handleVote} 
            disabled={!selectedOption || isVoting}
          >
            {isVoting ? 'Voting...' : 'Vote'}
          </button>
          <button className="button is-light" onClick={onClose}>
            Cancel
          </button>
        </div>
      </div>
    </div>
  );
};

export default VoteModal;
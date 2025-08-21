import React from 'react';

const ResultsChart = ({ options, totalVotes }) => {
  return (
    <div className="results-chart">
      {options.map(option => {
        const percentage = totalVotes > 0 ? (option.voteCount / totalVotes) * 100 : 0;
        return (
          <div key={option.id} className="result-item">
            <div className="result-info">
              <span className="result-text">{option.text}</span>
              <span className="result-stats">{option.voteCount} votes ({percentage.toFixed(1)}%)</span>
            </div>
            <div className="progress-bar">
              <div 
                className="progress-fill" 
                style={{ width: `${percentage}%` }}
              ></div>
            </div>
          </div>
        );
      })}
      <div className="total-votes">Total votes: {totalVotes}</div>
    </div>
  );
};

export default ResultsChart;
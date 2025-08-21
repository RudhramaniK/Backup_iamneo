import React, { useState } from 'react';

const PollForm = ({ onSubmit, initialData = { question: '', options: ['', ''], privacy: 'PUBLIC' }, buttonText = "Create Poll" }) => {
  const [question, setQuestion] = useState(initialData.question);
  const [options, setOptions] = useState(initialData.options);
  const [privacy, setPrivacy] = useState(initialData.privacy);

  const handleSubmit = (e) => {
    e.preventDefault();
    const validOptions = options.filter(opt => opt.trim() !== '');
    if (question.trim() && validOptions.length >= 2) {
      onSubmit({
        question: question.trim(),
        options: validOptions,
        privacy // <-- Add privacy to payload
      });
    }
  };

  const addOption = () => {
    setOptions([...options, '']);
  };

  const removeOption = (index) => {
    if (options.length > 2) {
      setOptions(options.filter((_, i) => i !== index));
    }
  };

  const updateOption = (index, value) => {
    const newOptions = [...options];
    newOptions[index] = value;
    setOptions(newOptions);
  };

  return (
    <form onSubmit={handleSubmit} className="poll-form">
      <div className="field">
        <label className="label">Question</label>
        <div className="control">
          <input
            className="input"
            type="text"
            value={question}
            onChange={(e) => setQuestion(e.target.value)}
            placeholder="Enter your question"
            required
          />
        </div>
      </div>

      <div className="field">
        <label className="label">Options</label>
        {options.map((option, index) => (
          <div key={index} className="field has-addons">
            <div className="control is-expanded">
              <input
                className="input"
                type="text"
                value={option}
                onChange={(e) => updateOption(index, e.target.value)}
                placeholder={`Option ${index + 1}`}
                required={index < 2}
              />
            </div>
            <div className="control">
              <button
                type="button"
                className="button is-danger"
                onClick={() => removeOption(index)}
                disabled={options.length <= 2}
              >
                Remove
              </button>
            </div>
          </div>
        ))}
      </div>

      <div className="field">
        <label className="label">Privacy</label>
        <div className="control">
          <div className="select">
            <select value={privacy} onChange={e => setPrivacy(e.target.value)}>
              <option value="PUBLIC">Public</option>
              <option value="PRIVATE">Private</option>
            </select>
          </div>
        </div>
      </div>

      <div className="field">
        <div className="control">
          <button type="button" className="button is-info" onClick={addOption}>
            Add Option
          </button>
        </div>
      </div>

      <div className="field">
        <div className="control">
          <button type="submit" className="button is-primary">
            {buttonText}
          </button>
        </div>
      </div>
    </form>
  );
};

export default PollForm;
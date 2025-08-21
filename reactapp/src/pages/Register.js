import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Register = () => {
  // CHANGE: Use 'name' instead of 'username' to match backend
  const [userData, setUserData] = useState({
    name: '', // CHANGED from 'username' to 'name'
    email: '',
    password: '',
    role: 'VOTER'
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setUserData({
      ...userData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await register(userData); // If successful, just navigate
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed');
    }
    setLoading(false);
  };

  return (
    <div className="columns is-centered">
      <div className="column is-half">
        <div className="card">
          <div className="card-content">
            <h1 className="title">Register</h1>
            
            {error && (
              <div className="notification is-danger">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="field">
                {/* CHANGE: Label and input name to 'name' */}
                <label className="label">Name</label>
                <div className="control">
                  <input
                    className="input"
                    type="text"
                    name="name" // CHANGED from 'username' to 'name'
                    value={userData.name} // CHANGED from username to name
                    onChange={handleChange}
                    required
                    minLength="3"
                    maxLength="20"
                  />
                </div>
              </div>

              <div className="field">
                <label className="label">Email</label>
                <div className="control">
                  <input
                    className="input"
                    type="email"
                    name="email"
                    value={userData.email}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="field">
                <label className="label">Password</label>
                <div className="control">
                  <input
                    className="input"
                    type="password"
                    name="password"
                    value={userData.password}
                    onChange={handleChange}
                    required
                    minLength="6"
                  />
                </div>
              </div>

              <div className="field">
                <label className="label">I want to:</label>
                <div className="control">
                  <div className="select is-fullwidth">
                    <select
                      name="role"
                      value={userData.role}
                      onChange={handleChange}
                      required
                    >
                      <option value="VOTER">Vote on Polls</option>
                      <option value="CREATOR">Create Polls</option>
                    </select>
                  </div>
                </div>
                <p className="help">Choose "Create Polls" if you want to make your own polls.</p>
              </div>

              <div className="field">
                <div className="control">
                  <button 
                    className={`button is-primary is-fullwidth ${loading ? 'is-loading' : ''}`}
                    type="submit"
                    disabled={loading}
                  >
                    Register
                  </button>
                </div>
              </div>
            </form>

            <div className="has-text-centered">
              <p>Already have an account? <Link to="/login">Login here</Link></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Login = () => {
  const [credentials, setCredentials] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await login(credentials); // If successful, just navigate
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed');
    }
    setLoading(false);
  };

  return (
    <div className="columns is-centered">
      <div className="column is-half">
        <div className="card">
          <div className="card-content">
            <h1 className="title">Login</h1>
            
            {error && (
              <div className="notification is-danger">
                {error}
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="field">
                <label className="label">Emial</label>
                <div className="control">
                  <input
                    className="input"
                    type="text"
                    name="email"
                    value={credentials.email}
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
                    value={credentials.password}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="field">
                <div className="control">
                  <button 
                    className={`button is-primary is-fullwidth ${loading ? 'is-loading' : ''}`}
                    type="submit"
                    disabled={loading}
                  >
                    Login
                  </button>
                </div>
              </div>
            </form>

            <div className="has-text-centered">
              <p>Don't have an account? <Link to="/register">Register here</Link></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
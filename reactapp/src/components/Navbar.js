import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
  const { currentUser, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">PollApp</Link>
      </div>
      <div className="navbar-menu">
        <div className="navbar-start">
          <Link to="/" className="navbar-item">Home</Link>
          <Link to="/polls" className="navbar-item">All Polls</Link>
          {currentUser && (
            <Link to="/dashboard" className="navbar-item">Dashboard</Link>
          )}
          {currentUser?.role === 'ADMIN' && (
            <Link to="/admin" className="navbar-item">Admin Panel</Link>
          )}
        </div>
        <div className="navbar-end">
          {currentUser ? (
            <div className="navbar-item has-dropdown is-hoverable">
              <span className="navbar-link">
                {currentUser.name}
                {currentUser.role === 'ADMIN' && ' (Admin)'}
              </span>
              <div className="navbar-dropdown">
                {currentUser?.role === 'CREATOR' && (
                  <Link to="/create" className="navbar-item">Create Poll</Link>
                )}
                {currentUser?.role === 'CREATOR' && <hr className="navbar-divider" />}
                <button className="navbar-item" onClick={handleLogout}>
                  Logout
                </button>
              </div>
            </div>
          ) : (
            <div className="navbar-item">
              <div className="buttons">
                <Link to="/register" className="button is-primary">
                  <strong>Sign up</strong>
                </Link>
                <Link to="/login" className="button is-light">
                  Log in
                </Link>
              </div>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
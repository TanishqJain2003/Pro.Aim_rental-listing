import React, { useState, useEffect } from 'react';
import GoogleMapEmbed from './GoogleMapEmbed';
import { useAuth } from '../contexts/AuthContext';
import axios from 'axios';
const isMock = (import.meta as any).env?.VITE_MOCK_AUTH === 'true';

interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
}

const Profile: React.FC = () => {
  const { user: authUser } = useAuth();
  const [user, setUser] = useState<User | null>(null);
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: ''
  });
  const [errors, setErrors] = useState<{ [key: string]: string }>({});
  const [isLoading, setIsLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  useEffect(() => {
    if (authUser) {
      setUser(authUser);
      setFormData({
        firstName: authUser.firstName || '',
        lastName: authUser.lastName || '',
        email: authUser.email || ''
      });
    }
  }, [authUser]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    // Clear error when user starts typing
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  const validateForm = () => {
    const newErrors: { [key: string]: string } = {};

    if (!formData.firstName.trim()) {
      newErrors.firstName = 'First name is required';
    }

    if (!formData.lastName.trim()) {
      newErrors.lastName = 'Last name is required';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage({ type: '', text: '' });

    if (!validateForm()) {
      return;
    }

    setIsLoading(true);
    try {
      if (isMock) {
        const updated = { ...user!, ...formData } as User;
        setUser(updated);
        setMessage({ type: 'success', text: 'Profile updated successfully!' });
      } else {
        const response = await axios.put(`/api/users/${user?.id}`, formData);
        setUser(response.data);
        setMessage({ type: 'success', text: 'Profile updated successfully!' });
      }
      setIsEditing(false);
    } catch (error: any) {
      if (isMock && !error?.response) {
        const updated = { ...user!, ...formData } as User;
        setUser(updated);
        setMessage({ type: 'success', text: 'Profile updated successfully!' });
      } else {
        setMessage({ type: 'error', text: error.response?.data || 'Failed to update profile' });
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleCancel = () => {
    setFormData({
      firstName: user?.firstName || '',
      lastName: user?.lastName || '',
      email: user?.email || ''
    });
    setErrors({});
    setIsEditing(false);
  };

  if (!user) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900">Profile</h1>
        <p className="text-secondary-600 mt-2">Manage your account information</p>
      </div>

      {message.text && (
        <div className={`mb-6 p-4 rounded-lg ${
          message.type === 'success' 
            ? 'bg-green-50 border border-green-200 text-green-700' 
            : 'bg-red-50 border border-red-200 text-red-700'
        }`}>
          {message.text}
        </div>
      )}

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Profile Information */}
        <div className="lg:col-span-2">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title">Personal Information</h3>
              <p className="card-subtitle">Update your personal details</p>
            </div>

            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label htmlFor="firstName" className="block text-sm font-medium text-secondary-700">
                    First Name
                  </label>
                  <input
                    id="firstName"
                    name="firstName"
                    type="text"
                    disabled={!isEditing}
                    className={`mt-1 input-field ${!isEditing ? 'bg-secondary-50' : ''} ${errors.firstName ? 'border-red-300 focus:ring-red-500' : ''}`}
                    value={formData.firstName}
                    onChange={handleChange}
                  />
                  {errors.firstName && (
                    <p className="mt-1 text-sm text-red-600">{errors.firstName}</p>
                  )}
                </div>

                <div>
                  <label htmlFor="lastName" className="block text-sm font-medium text-secondary-700">
                    Last Name
                  </label>
                  <input
                    id="lastName"
                    name="lastName"
                    type="text"
                    disabled={!isEditing}
                    className={`mt-1 input-field ${!isEditing ? 'bg-secondary-50' : ''} ${errors.lastName ? 'border-red-300 focus:ring-red-500' : ''}`}
                    value={formData.lastName}
                    onChange={handleChange}
                  />
                  {errors.lastName && (
                    <p className="mt-1 text-sm text-red-600">{errors.lastName}</p>
                  )}
                </div>
              </div>

              <div>
                <label htmlFor="email" className="block text-sm font-medium text-secondary-700">
                  Email Address
                </label>
                <input
                  id="email"
                  name="email"
                  type="email"
                  disabled={!isEditing}
                  className={`mt-1 input-field ${!isEditing ? 'bg-secondary-50' : ''} ${errors.email ? 'border-red-300 focus:ring-red-500' : ''}`}
                  value={formData.email}
                  onChange={handleChange}
                />
                {errors.email && (
                  <p className="mt-1 text-sm text-red-600">{errors.email}</p>
                )}
              </div>

              <div className="flex justify-end space-x-3">
                {!isEditing ? (
                  <button
                    type="button"
                    onClick={() => setIsEditing(true)}
                    className="btn-primary"
                  >
                    Edit Profile
                  </button>
                ) : (
                  <>
                    <button
                      type="button"
                      onClick={handleCancel}
                      className="btn-secondary"
                    >
                      Cancel
                    </button>
                    <button
                      type="submit"
                      disabled={isLoading}
                      className="btn-primary"
                    >
                      {isLoading ? 'Saving...' : 'Save Changes'}
                    </button>
                  </>
                )}
              </div>
            </form>
          </div>

          {/* Location */}
          <div className="card mt-6">
            <div className="card-header">
              <h3 className="card-title">Location</h3>
              <p className="card-subtitle">Your saved or current location</p>
            </div>
            <div className="p-4">
              <GoogleMapEmbed placeQuery={`${user.firstName || 'User'} location`} heightClassName="h-72" />
            </div>
          </div>
        </div>

        {/* Profile Summary */}
        <div className="lg:col-span-1">
          <div className="card">
            <div className="card-header">
              <h3 className="card-title">Account Summary</h3>
              <p className="card-subtitle">Your account details</p>
            </div>

            <div className="space-y-4">
              <div className="flex items-center justify-between p-3 bg-secondary-50 rounded-lg">
                <span className="text-sm font-medium text-secondary-600">Username</span>
                <span className="text-sm text-secondary-900">{user.username}</span>
              </div>

              <div className="flex items-center justify-between p-3 bg-secondary-50 rounded-lg">
                <span className="text-sm font-medium text-secondary-600">Role</span>
                <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-primary-100 text-primary-800">
                  {user.role}
                </span>
              </div>

              <div className="flex items-center justify-between p-3 bg-secondary-50 rounded-lg">
                <span className="text-sm font-medium text-secondary-600">Member Since</span>
                <span className="text-sm text-secondary-900">2024</span>
              </div>
            </div>
          </div>

          {/* Quick Actions */}
          <div className="card mt-6">
            <div className="card-header">
              <h3 className="card-title">Quick Actions</h3>
            </div>
            <div className="space-y-3">
              <button className="w-full text-left p-3 text-sm text-secondary-700 hover:bg-secondary-50 rounded-lg transition-colors duration-200">
                Change Password
              </button>
              <button className="w-full text-left p-3 text-sm text-secondary-700 hover:bg-secondary-50 rounded-lg transition-colors duration-200">
                Download Data
              </button>
              <button className="w-full text-left p-3 text-sm text-red-600 hover:bg-red-50 rounded-lg transition-colors duration-200">
                Delete Account
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;

import React, { useState, useEffect } from 'react';
import axios from 'axios';
const isMock = (import.meta as any).env?.VITE_MOCK_AUTH === 'true';

interface User {
  id: number;
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
  createdAt: string;
}

const Users: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedRole, setSelectedRole] = useState('all');
  const [currentPage, setCurrentPage] = useState(1);
  const [usersPerPage] = useState(10);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      if (isMock) {
        setUsers([
          { id: 1, username: 'jdoe', email: 'jdoe@example.com', firstName: 'John', lastName: 'Doe', role: 'ADMIN', createdAt: new Date().toISOString() },
          { id: 2, username: 'asmith', email: 'asmith@example.com', firstName: 'Alice', lastName: 'Smith', role: 'USER', createdAt: new Date().toISOString() },
          { id: 3, username: 'bwayne', email: 'bwayne@example.com', firstName: 'Bruce', lastName: 'Wayne', role: 'MODERATOR', createdAt: new Date().toISOString() },
        ]);
        return;
      }
      const response = await axios.get('/api/users');
      setUsers(response.data);
    } catch (err: any) {
      if (isMock && !err?.response) {
        setUsers([
          { id: 1, username: 'jdoe', email: 'jdoe@example.com', firstName: 'John', lastName: 'Doe', role: 'ADMIN', createdAt: new Date().toISOString() },
          { id: 2, username: 'asmith', email: 'asmith@example.com', firstName: 'Alice', lastName: 'Smith', role: 'USER', createdAt: new Date().toISOString() },
          { id: 3, username: 'bwayne', email: 'bwayne@example.com', firstName: 'Bruce', lastName: 'Wayne', role: 'MODERATOR', createdAt: new Date().toISOString() },
        ]);
        return;
      }
      setError(err.response?.data || 'Failed to fetch users');
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteUser = async (userId: number) => {
    if (window.confirm('Are you sure you want to delete this user?')) {
      try {
        if (isMock) {
          setUsers(users.filter(user => user.id !== userId));
          return;
        }
        await axios.delete(`/api/users/${userId}`);
        setUsers(users.filter(user => user.id !== userId));
      } catch (err: any) {
        if (isMock && !err?.response) {
          setUsers(users.filter(user => user.id !== userId));
          return;
        }
        setError(err.response?.data || 'Failed to delete user');
      }
    }
  };

  const handleRoleChange = async (userId: number, newRole: string) => {
    try {
      const user = users.find(u => u.id === userId);
      if (user) {
        const updatedUser = { ...user, role: newRole };
        if (isMock) {
          setUsers(users.map(u => u.id === userId ? updatedUser : u));
          return;
        }
        await axios.put(`/api/users/${userId}`, updatedUser);
        setUsers(users.map(u => u.id === userId ? updatedUser : u));
      }
    } catch (err: any) {
      if (isMock && !err?.response) {
        setUsers(users.map(u => u.id === userId ? { ...u, role: newRole } : u));
        return;
      }
      setError(err.response?.data || 'Failed to update user role');
    }
  };

  // Filter users based on search term and role
  const filteredUsers = users.filter(user => {
    const matchesSearch = user.username.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         `${user.firstName} ${user.lastName}`.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesRole = selectedRole === 'all' || user.role === selectedRole;
    return matchesSearch && matchesRole;
  });

  // Pagination
  const indexOfLastUser = currentPage * usersPerPage;
  const indexOfFirstUser = indexOfLastUser - usersPerPage;
  const currentUsers = filteredUsers.slice(indexOfFirstUser, indexOfLastUser);
  const totalPages = Math.ceil(filteredUsers.length / usersPerPage);

  const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900">User Management</h1>
        <p className="text-secondary-600 mt-2">Manage all users in the system</p>
      </div>

      {error && (
        <div className="mb-6 bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
          {error}
        </div>
      )}

      {/* Filters and Search */}
      <div className="card mb-6">
        <div className="flex flex-col md:flex-row gap-4">
          <div className="flex-1">
            <label htmlFor="search" className="block text-sm font-medium text-secondary-700 mb-2">
              Search Users
            </label>
            <input
              id="search"
              type="text"
              placeholder="Search by username, email, or name..."
              className="input-field"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>
          <div>
            <label htmlFor="role" className="block text-sm font-medium text-secondary-700 mb-2">
              Filter by Role
            </label>
            <select
              id="role"
              className="input-field"
              value={selectedRole}
              onChange={(e) => setSelectedRole(e.target.value)}
            >
              <option value="all">All Roles</option>
              <option value="USER">User</option>
              <option value="ADMIN">Admin</option>
              <option value="MODERATOR">Moderator</option>
            </select>
          </div>
        </div>
      </div>

      {/* Users Table */}
      <div className="card">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-secondary-200">
            <thead className="bg-secondary-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">
                  User
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">
                  Email
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">
                  Role
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">
                  Created
                </th>
                <th className="px-6 py-3 text-left text-xs font-medium text-secondary-500 uppercase tracking-wider">
                  Actions
                </th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-secondary-200">
              {currentUsers.map((user) => (
                <tr key={user.id} className="hover:bg-secondary-50">
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="flex items-center">
                      <div className="h-10 w-10 bg-primary-100 rounded-full flex items-center justify-center">
                        <span className="text-primary-600 font-medium text-sm">
                          {user.firstName?.charAt(0) || user.username.charAt(0)}
                        </span>
                      </div>
                      <div className="ml-4">
                        <div className="text-sm font-medium text-secondary-900">
                          {user.firstName} {user.lastName}
                        </div>
                        <div className="text-sm text-secondary-500">
                          @{user.username}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <div className="text-sm text-secondary-900">{user.email}</div>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <select
                      className="text-sm border border-secondary-300 rounded px-2 py-1 focus:outline-none focus:ring-2 focus:ring-primary-500"
                      value={user.role}
                      onChange={(e) => handleRoleChange(user.id, e.target.value)}
                    >
                      <option value="USER">User</option>
                      <option value="MODERATOR">Moderator</option>
                      <option value="ADMIN">Admin</option>
                    </select>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-secondary-500">
                    {new Date(user.createdAt).toLocaleDateString()}
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <button
                      onClick={() => handleDeleteUser(user.id)}
                      className="text-red-600 hover:text-red-900 transition-colors duration-200"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="bg-secondary-50 px-4 py-3 flex items-center justify-between border-t border-secondary-200 sm:px-6">
            <div className="flex-1 flex justify-between sm:hidden">
              <button
                onClick={() => paginate(currentPage - 1)}
                disabled={currentPage === 1}
                className="relative inline-flex items-center px-4 py-2 border border-secondary-300 text-sm font-medium rounded-md text-secondary-700 bg-white hover:bg-secondary-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Previous
              </button>
              <button
                onClick={() => paginate(currentPage + 1)}
                disabled={currentPage === totalPages}
                className="ml-3 relative inline-flex items-center px-4 py-2 border border-secondary-300 text-sm font-medium rounded-md text-secondary-700 bg-white hover:bg-secondary-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                Next
              </button>
            </div>
            <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
              <div>
                <p className="text-sm text-secondary-700">
                  Showing <span className="font-medium">{indexOfFirstUser + 1}</span> to{' '}
                  <span className="font-medium">
                    {Math.min(indexOfLastUser, filteredUsers.length)}
                  </span>{' '}
                  of <span className="font-medium">{filteredUsers.length}</span> results
                </p>
              </div>
              <div>
                <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px">
                  {Array.from({ length: totalPages }, (_, i) => i + 1).map((number) => (
                    <button
                      key={number}
                      onClick={() => paginate(number)}
                      className={`relative inline-flex items-center px-4 py-2 border text-sm font-medium ${
                        currentPage === number
                          ? 'z-10 bg-primary-50 border-primary-500 text-primary-600'
                          : 'bg-white border-secondary-300 text-secondary-500 hover:bg-secondary-50'
                      }`}
                    >
                      {number}
                    </button>
                  ))}
                </nav>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Summary Stats */}
      <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="card">
          <div className="text-center">
            <p className="text-2xl font-bold text-primary-600">{users.length}</p>
            <p className="text-sm text-secondary-600">Total Users</p>
          </div>
        </div>
        <div className="card">
          <div className="text-center">
            <p className="text-2xl font-bold text-green-600">
              {users.filter(u => u.role === 'USER').length}
            </p>
            <p className="text-sm text-secondary-600">Regular Users</p>
          </div>
        </div>
        <div className="card">
          <div className="text-center">
            <p className="text-2xl font-bold text-purple-600">
              {users.filter(u => u.role === 'ADMIN').length}
            </p>
            <p className="text-sm text-secondary-600">Administrators</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Users;

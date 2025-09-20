import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import Navbar from './components/Navbar';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import Dashboard from './components/Dashboard';
import Profile from './components/Profile';
import Users from './components/Users';
import Home from './components/Home';
import Listings from './components/listing/ListingList';
import ListingDetails from './components/listing/ListingDetails';
import PropertyDetail from './components/property/PropertyDetail';
import PropertyForm from './components/property/PropertyForm';
import PropertyList from './components/property/PropertyList'; // ✅ Corrected import
import ManageRentals from './components/ManageRentals';
import PaymentsPage from './components/PaymentsPage';

import './index.css';

// Protected Route Component
const ProtectedRoute: React.FC<{ children: React.ReactNode; roles?: string[] }> = ({ 
  children, 
  roles 
}) => {
  const { user, isAuthenticated } = useAuth();

  if (!isAuthenticated) return <Navigate to="/login" replace />;
  if (roles && user && !roles.includes(user.role)) return <Navigate to="/dashboard" replace />;

  return <>{children}</>;
};
const Payments = () => (
  <div className="p-6">
    <h1 className="text-2xl font-bold">Payments</h1>
    <p className="text-gray-600">Here you can track and manage rent payments.</p>
  </div>
);

const Maintenance = () => (
  <div className="p-6">
    <h1 className="text-2xl font-bold">Maintenance</h1>
    <p className="text-gray-600">Here you can view and respond to maintenance requests.</p>
  </div>
);

const Renewals = () => (
  <div className="p-6">
    <h1 className="text-2xl font-bold">Lease Renewals</h1>
    <p className="text-gray-600">Here you can manage lease renewals and agreements.</p>
  </div>
);
// Main App Component
const AppContent: React.FC = () => {
  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <main className="container mx-auto px-4 py-8">
          <Routes>
            {/* Public Routes */}
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            {/* Protected Routes */}
            <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
            <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
            <Route path="/users" element={<ProtectedRoute roles={['ADMIN']}><Users /></ProtectedRoute>} />
            <Route path="/listings" element={<ProtectedRoute><Listings /></ProtectedRoute>} />
            <Route path="/listings/:id" element={<ProtectedRoute><ListingDetails /></ProtectedRoute>} />
            {/* Property Routes */}
            <Route path="/properties" element={<ProtectedRoute><PropertyList /></ProtectedRoute>} /> {/* List page */}
            <Route path="/properties/new" element={<ProtectedRoute><PropertyForm /></ProtectedRoute>} />
            <Route path="/properties/:id" element={<ProtectedRoute><PropertyDetail /></ProtectedRoute>} />

            {/* Fallback Route */}
            <Route path="*" element={<Navigate to="/" replace />} />
             {/* Main Rentals Page */}
        <Route path="/rentals" element={<ManageRentals />} />

        {/* Sub Pages */}
        <Route path="/rentals/payments" element={<Payments />} />
        <Route path="/rentals/maintenance" element={<Maintenance />} />
        <Route path="/rentals/renewals" element={<Renewals />} />

        <Route path="/payments" element={<PaymentsPage />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
};

// App Component with Auth Provider
const App: React.FC = () => {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
};

export default App;

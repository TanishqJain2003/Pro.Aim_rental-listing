import React from "react";
import { Link } from "react-router-dom";

const ManageRentals: React.FC = () => {
  return (
    <div className="max-w-6xl mx-auto px-6 py-10">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Manage Rentals</h1>
      <p className="text-gray-600 mb-10">
        Easily handle rent payments, respond to maintenance requests, and manage lease renewals.
      </p>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {/* Payments */}
        <Link
          to="/rentals/payments"
          className="bg-white rounded-2xl shadow-md hover:shadow-lg transition-shadow p-6 flex flex-col items-center text-center"
        >
          {/* Credit Card Icon */}
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-12 h-12 text-blue-600 mb-4"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={1.5}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M2.25 7.5h19.5M2.25 12h19.5m-16.5 4.5h3m3 0h3"
            />
          </svg>
          <h2 className="text-xl font-semibold mb-2">Payments</h2>
          <p className="text-gray-500 text-sm">
            Track rent payments, due dates, and history.
          </p>
        </Link>

        {/* Maintenance Requests */}
        <Link
          to="/rentals/maintenance"
          className="bg-white rounded-2xl shadow-md hover:shadow-lg transition-shadow p-6 flex flex-col items-center text-center"
        >
          {/* Wrench Icon */}
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-12 h-12 text-green-600 mb-4"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={1.5}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M21.75 6.75a9 9 0 11-9-9v4.5a4.5 4.5 0 100 9h4.5a9 9 0 004.5-4.5z"
            />
          </svg>
          <h2 className="text-xl font-semibold mb-2">Maintenance</h2>
          <p className="text-gray-500 text-sm">
            View and respond to tenant maintenance requests.
          </p>
        </Link>

        {/* Lease Renewals */}
        <Link
          to="/rentals/renewals"
          className="bg-white rounded-2xl shadow-md hover:shadow-lg transition-shadow p-6 flex flex-col items-center text-center"
        >
          {/* Document Icon */}
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="w-12 h-12 text-purple-600 mb-4"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
            strokeWidth={1.5}
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M12 4.5v15m7.5-7.5h-15"
            />
          </svg>
          <h2 className="text-xl font-semibold mb-2">Lease Renewals</h2>
          <p className="text-gray-500 text-sm">
            Manage upcoming lease renewals and agreements.
          </p>
        </Link>
      </div>
    </div>
  );
};

export default ManageRentals;

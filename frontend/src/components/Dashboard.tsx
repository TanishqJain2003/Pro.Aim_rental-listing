import React, { useState, useEffect } from 'react';
import { Line, Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import axios from 'axios';

const isMock = (import.meta as any).env?.VITE_MOCK_AUTH === 'true';

// Register Chart.js components
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  BarElement,
  Title,
  Tooltip,
  Legend
);

interface DashboardData {
  totalUsers: number;
  activeUsers: number;
  totalRevenue: number;
  monthlyGrowth: number;
  monthlyStats: { [key: string]: number };
}

const Dashboard: React.FC = () => {
  const [dashboardData, setDashboardData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchDashboardData();
  }, []);

  const fetchDashboardData = async () => {
    try {
      if (isMock) {
        // Provide static mock data
        setDashboardData({
          totalUsers: 1245,
          activeUsers: 876,
          totalRevenue: 53210,
          monthlyGrowth: 12,
          monthlyStats: {
            Jan: 80,
            Feb: 95,
            Mar: 120,
            Apr: 140,
            May: 160,
            Jun: 150,
            Jul: 170,
            Aug: 180,
            Sep: 190,
            Oct: 210,
            Nov: 220,
            Dec: 230
          }
        });
        return;
      }

      // ✅ Include token from localStorage
      const token = localStorage.getItem("token");

      const response = await axios.get('http://localhost:8080/api/dashboard', {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      setDashboardData(response.data);
    } catch (err: any) {
      // ✅ Always store a string in error
      setError(
        err.response?.data?.message ||
        err.message ||
        'Failed to fetch dashboard data'
      );
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-primary-600"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
          {error}
        </div>
      </div>
    );
  }

  if (!dashboardData) {
    return null;
  }

  // Prepare chart data
  const monthlyLabels = Object.keys(dashboardData.monthlyStats);
  const monthlyValues = Object.values(dashboardData.monthlyStats);

  const lineChartData = {
    labels: monthlyLabels,
    datasets: [
      {
        label: 'Monthly Users',
        data: monthlyValues,
        borderColor: 'rgb(59, 130, 246)',
        backgroundColor: 'rgba(59, 130, 246, 0.1)',
        tension: 0.4,
        fill: true,
      },
    ],
  };

  const barChartData = {
    labels: monthlyLabels,
    datasets: [
      {
        label: 'Monthly Growth',
        data: monthlyValues,
        backgroundColor: 'rgba(99, 102, 241, 0.8)',
        borderColor: 'rgb(99, 102, 241)',
        borderWidth: 1,
      },
    ],
  };

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900">Dashboard</h1>
        <p className="text-secondary-600 mt-2">Welcome to your Pro.Aim dashboard</p>
      </div>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        <div className="card">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-blue-100 text-blue-600">
              👤
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-secondary-600">Total Users</p>
              <p className="text-2xl font-semibold text-secondary-900">
                {dashboardData.totalUsers.toLocaleString()}
              </p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-green-100 text-green-600">
              ✅
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-secondary-600">Active Users</p>
              <p className="text-2xl font-semibold text-secondary-900">
                {dashboardData.activeUsers.toLocaleString()}
              </p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-yellow-100 text-yellow-600">
              💰
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-secondary-600">Total Revenue</p>
              <p className="text-2xl font-semibold text-secondary-900">
                ${dashboardData.totalRevenue.toLocaleString()}
              </p>
            </div>
          </div>
        </div>

        <div className="card">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-purple-100 text-purple-600">
              📈
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-secondary-600">Monthly Growth</p>
              <p className="text-2xl font-semibold text-secondary-900">
                {dashboardData.monthlyGrowth}%
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <div className="card">
          <div className="card-header">
            <h3 className="card-title">User Growth Trend</h3>
            <p className="card-subtitle">Monthly user registration trend</p>
          </div>
          <div className="h-80">
            <Line data={lineChartData} options={chartOptions} />
          </div>
        </div>

        <div className="card">
          <div className="card-header">
            <h3 className="card-title">Monthly Statistics</h3>
            <p className="card-subtitle">User activity by month</p>
          </div>
          <div className="h-80">
            <Bar data={barChartData} options={chartOptions} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;


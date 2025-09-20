import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

interface Property {
  id: number;
  title: string;
  description: string;
  address: string;
  city: string;
  state: string;
  rentAmount: number;
  bedrooms: number;
  bathrooms: number;
  propertyType: string;
  status: string;
  imageUrls: string[];
  amenities: string[];
}

const PropertyList: React.FC = () => {
  const { user } = useAuth();
  const [properties, setProperties] = useState<Property[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterCity, setFilterCity] = useState('');
  const [filterType, setFilterType] = useState('');
  const [filterStatus, setFilterStatus] = useState('');
  const [sortBy, setSortBy] = useState('createdAt');
  const [sortOrder, setSortOrder] = useState('desc');

  useEffect(() => {
    fetchProperties();
  }, []);

  const fetchProperties = async () => {
    try {
      setLoading(true);
      // In a real app, this would be an API call
      // const response = await fetch('/api/properties');
      // const data = await response.json();
      
      // Mock data for now
      const mockProperties: Property[] = [
        {
          id: 1,
          title: "Modern Downtown Apartment",
          description: "Beautiful 2-bedroom apartment in the heart of downtown with amazing city views.",
          address: "123 Main St",
          city: "New York",
          state: "NY",
          rentAmount: 2500,
          bedrooms: 2,
          bathrooms: 2,
          propertyType: "APARTMENT",
          status: "AVAILABLE",
          imageUrls: ["https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&h=600&fit=crop"],
          amenities: ["Pool", "Gym", "Parking"]
        },
        {
          id: 2,
          title: "Cozy Suburban House",
          description: "Family-friendly 3-bedroom house with a large backyard and garage.",
          address: "456 Oak Ave",
          city: "Los Angeles",
          state: "CA",
          rentAmount: 3200,
          bedrooms: 3,
          bathrooms: 2,
          propertyType: "HOUSE",
          status: "AVAILABLE",
          imageUrls: ["https://images.unsplash.com/photo-1572120360610-d971b9d7767c?w=800"],
          amenities: ["Backyard", "Garage", "Fireplace"]
        },
        {
          id: 3,
          title: "Luxury Condo with Amenities",
          description: "High-end 1-bedroom condo with premium finishes and resort-style amenities.",
          address: "789 Luxury Blvd",
          city: "Miami",
          state: "FL",
          rentAmount: 2800,
          bedrooms: 1,
          bathrooms: 1,
          propertyType: "CONDO",
          status: "RENTED",
          imageUrls: ["https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?w=800"],
          amenities: ["Pool", "Spa", "Concierge"]
        }
      ];
      
      setProperties(mockProperties);
    } catch (error) {
      console.error('Error fetching properties:', error);
    } finally {
      setLoading(false);
    }
  };

  const filteredProperties = properties.filter(property => {
    const matchesSearch = property.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         property.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         property.address.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCity = !filterCity || property.city === filterCity;
    const matchesType = !filterType || property.propertyType === filterType;
    const matchesStatus = !filterStatus || property.status === filterStatus;
    
    return matchesSearch && matchesCity && matchesType && matchesStatus;
  });

  const sortedProperties = [...filteredProperties].sort((a, b) => {
    let aValue: any = a[sortBy as keyof Property];
    let bValue: any = b[sortBy as keyof Property];
    
    if (sortBy === 'rentAmount') {
      aValue = Number(aValue);
      bValue = Number(bValue);
    }
    
    if (sortOrder === 'asc') {
      return aValue > bValue ? 1 : -1;
    } else {
      return aValue < bValue ? 1 : -1;
    }
  });

  const cities = [...new Set(properties.map(p => p.city))];
  const types = [...new Set(properties.map(p => p.propertyType))];
  const statuses = [...new Set(properties.map(p => p.status))];

  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto">
      {/* Header */}
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold text-gray-900 mb-2">Properties</h1>
          <p className="text-gray-600">Browse and manage rental properties</p>
        </div>
        {(user?.role === 'LANDLORD' || user?.role === 'ADMIN') && (
          <Link
            to="/properties/new"
            className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Add New Property
          </Link>
        )}
      </div>

      {/* Filters and Search */}
      <div className="bg-white p-6 rounded-lg shadow-sm mb-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-6 gap-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Search</label>
            <input
              type="text"
              placeholder="Search properties..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">City</label>
            <select
              value={filterCity}
              onChange={(e) => setFilterCity(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">All Cities</option>
              {cities.map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Type</label>
            <select
              value={filterType}
              onChange={(e) => setFilterType(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">All Types</option>
              {types.map(type => (
                <option key={type} value={type}>{type}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Status</label>
            <select
              value={filterStatus}
              onChange={(e) => setFilterStatus(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="">All Statuses</option>
              {statuses.map(status => (
                <option key={status} value={status}>{status}</option>
              ))}
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Sort By</label>
            <select
              value={sortBy}
              onChange={(e) => setSortBy(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="createdAt">Date</option>
              <option value="rentAmount">Rent</option>
              <option value="title">Title</option>
              <option value="city">City</option>
            </select>
          </div>
          
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">Order</label>
            <select
              value={sortOrder}
              onChange={(e) => setSortOrder(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              <option value="desc">Descending</option>
              <option value="asc">Ascending</option>
            </select>
          </div>
        </div>
      </div>

      {/* Properties Grid */}
      {sortedProperties.length === 0 ? (
        <div className="text-center py-12">
          <div className="text-gray-400 text-6xl mb-4">🏠</div>
          <h3 className="text-lg font-medium text-gray-900 mb-2">No properties found</h3>
          <p className="text-gray-500">Try adjusting your search criteria or filters.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {sortedProperties.map((property) => (
            <div key={property.id} className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow">
              <div className="relative">
                <img
                  src={property.imageUrls[0] || 'https://via.placeholder.com/300x200'}
                  alt={property.title}
                  className="w-full h-48 object-cover"
                />
                <div className="absolute top-2 right-2">
                  <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                    property.status === 'AVAILABLE' ? 'bg-green-100 text-green-800' :
                    property.status === 'RENTED' ? 'bg-red-100 text-red-800' :
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {property.status}
                  </span>
                </div>
              </div>
              
              <div className="p-6">
                <h3 className="text-xl font-semibold text-gray-900 mb-2">
                  <Link to={`/properties/${property.id}`} className="hover:text-blue-600">
                    {property.title}
                  </Link>
                </h3>
                
                <p className="text-gray-600 text-sm mb-4 line-clamp-2">
                  {property.description}
                </p>
                
                <div className="flex items-center text-gray-500 text-sm mb-4">
                  <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  {property.address}, {property.city}, {property.state}
                </div>
                
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center space-x-4 text-sm text-gray-500">
                    <span className="flex items-center">
                      <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2H5a2 2 0 00-2-2z" />
                      </svg>
                      {property.bedrooms} beds
                    </span>
                    <span className="flex items-center">
                      <svg className="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                      </svg>
                      {property.bathrooms} baths
                    </span>
                  </div>
                  <span className="text-2xl font-bold text-blue-600">
                    ${property.rentAmount.toLocaleString()}
                  </span>
                </div>
                
                {property.amenities.length > 0 && (
                  <div className="mb-4">
                    <div className="flex flex-wrap gap-2">
                      {property.amenities.slice(0, 3).map((amenity, index) => (
                        <span key={index} className="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">
                          {amenity}
                        </span>
                      ))}
                      {property.amenities.length > 3 && (
                        <span className="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">
                          +{property.amenities.length - 3} more
                        </span>
                      )}
                    </div>
                  </div>
                )}
                
                <div className="flex justify-between items-center">
                  <Link
                    to={`/properties/${property.id}`}
                    className="text-blue-600 hover:text-blue-700 font-medium"
                  >
                    View Details
                  </Link>
                  
                  {(user?.role === 'LANDLORD' || user?.role === 'ADMIN') && (
                    <Link
                      to={`/properties/${property.id}/edit`}
                      className="text-gray-600 hover:text-gray-700 font-medium"
                    >
                      Edit
                    </Link>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
      
      {/* Results Count */}
      <div className="mt-8 text-center text-gray-500">
        Showing {sortedProperties.length} of {properties.length} properties
      </div>
    </div>
  );
};

export default PropertyList;

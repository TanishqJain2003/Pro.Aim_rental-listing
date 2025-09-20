import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

interface Listing {
  id: number;
  title: string;
  description: string;
  rentAmount: number;
  securityDeposit: number;
  availableDate: string;
  leaseTermMonths: number;
  status: string;
  type: string;
  isFeatured: boolean;
  viewCount: number;
  property: {
    id: number;
    address: string;
    city: string;
    state: string;
    zipCode: string;
    bedrooms: number;
    bathrooms: number;
    propertyType: string;
    amenities: string[];
    images: string[];
  };
  landlord: {
    id: number;
    name: string;
  };
  createdAt: string;
}

const ListingList: React.FC = () => {
  const { user, isAuthenticated } = useAuth();
  const [listings, setListings] = useState<Listing[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCity, setSelectedCity] = useState('');
  const [selectedType, setSelectedType] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [minRent, setMinRent] = useState('');
  const [maxRent, setMaxRent] = useState('');
  const [sortBy, setSortBy] = useState('createdAt');
  const [sortOrder, setSortOrder] = useState('desc');

  useEffect(() => {
    loadListings();
  }, []);

  const loadListings = async () => {
    setLoading(true);
    try {
      // Mock data - replace with actual API call
      const mockListings: Listing[] = [
        {
          id: 1,
          title: "Modern Downtown Apartment",
          description: "Beautiful 2-bedroom apartment in the heart of downtown. Recently renovated with modern amenities and stunning city views.",
          rentAmount: 2500,
          securityDeposit: 3000,
          availableDate: "2024-02-01T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: true,
          viewCount: 156,
          property: {
            id: 1,
            address: "123 Main Street",
            city: "New York",
            state: "NY",
            zipCode: "10001",
            bedrooms: 2,
            bathrooms: 2,
            propertyType: "APARTMENT",
            amenities: ["Central Air", "Dishwasher", "Gym", "Pool"],
            images: ["https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800"]
          },
          landlord: { id: 1, name: "John Smith" },
          createdAt: "2024-01-15T10:00:00Z"
        },
        {
          id: 2,
          title: "Cozy Suburban House",
          description: "Charming 3-bedroom house in a quiet suburban neighborhood. Perfect for families with a large backyard and garage.",
          rentAmount: 3200,
          securityDeposit: 4000,
          availableDate: "2024-02-15T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: false,
          viewCount: 89,
          property: {
            id: 2,
            address: "456 Oak Avenue",
            city: "Brooklyn",
            state: "NY",
            zipCode: "11201",
            bedrooms: 3,
            bathrooms: 2,
            propertyType: "HOUSE",
            amenities: ["Backyard", "Garage", "Central Air", "Dishwasher"],
            images: ["https://images.unsplash.com/photo-1560448075-bb485b067938?w=800"]
          },
          landlord: { id: 2, name: "Sarah Johnson" },
          createdAt: "2024-01-20T10:00:00Z"
        },
        {
          id: 3,
          title: "Luxury Condo with City Views",
          description: "Premium 1-bedroom condo with panoramic city views. High-end finishes and building amenities included.",
          rentAmount: 4500,
          securityDeposit: 5500,
          availableDate: "2024-01-30T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: true,
          viewCount: 234,
          property: {
            id: 3,
            address: "789 Park Avenue",
            city: "Manhattan",
            state: "NY",
            zipCode: "10021",
            bedrooms: 1,
            bathrooms: 1,
            propertyType: "CONDO",
            amenities: ["Doorman", "Gym", "Pool", "Rooftop Deck"],
            images: ["https://images.unsplash.com/photo-1560448204-6031743e9d7d?w=800"]
          },
          landlord: { id: 3, name: "Michael Brown" },
          createdAt: "2024-01-10T10:00:00Z"
        },
        {
          id: 4,
          title: "Beachside Bungalow",
          description: "Relax in this cozy 2-bedroom bungalow just steps away from the beach. Perfect for vacation rentals.",
          rentAmount: 3800,
          securityDeposit: 4500,
          availableDate: "2024-03-01T00:00:00Z",
          leaseTermMonths: 6,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: false,
          viewCount: 120,
          property: {
            id: 4,
            address: "12 Ocean Drive",
            city: "Miami",
            state: "FL",
            zipCode: "33139",
            bedrooms: 2,
            bathrooms: 1,
            propertyType: "HOUSE",
            amenities: ["Beach Access", "Patio", "Outdoor Shower"],
            images: ["https://images.unsplash.com/photo-1600585154526-990dced4db0d?w=800"]
          },
          landlord: { id: 4, name: "Laura Green" },
          createdAt: "2024-02-01T10:00:00Z"
        },
        {
          id: 5,
          title: "Student Apartment Near Campus",
          description: "Affordable 1-bedroom apartment within walking distance of the university. Ideal for students.",
          rentAmount: 1200,
          securityDeposit: 1500,
          availableDate: "2024-02-20T00:00:00Z",
          leaseTermMonths: 9,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: false,
          viewCount: 75,
          property: {
            id: 5,
            address: "22 College Road",
            city: "Boston",
            state: "MA",
            zipCode: "02115",
            bedrooms: 1,
            bathrooms: 1,
            propertyType: "APARTMENT",
            amenities: ["Wi-Fi Included", "Laundry Room", "Study Area"],
            images: ["https://images.unsplash.com/photo-1580587771525-78b9dba3b914?w=800"]
          },
          landlord: { id: 5, name: "David Wilson" },
          createdAt: "2024-01-25T09:00:00Z"
        },
        {
          id: 6,
          title: "Mountain Cabin Getaway",
          description: "Rustic 2-bedroom cabin in the mountains. Perfect for weekend retreats or seasonal rentals.",
          rentAmount: 2000,
          securityDeposit: 2500,
          availableDate: "2024-04-01T00:00:00Z",
          leaseTermMonths: 3,
          status: "ACTIVE",
          type: "SUBLET",
          isFeatured: true,
          viewCount: 65,
          property: {
            id: 6,
            address: "88 Pine Hill",
            city: "Denver",
            state: "CO",
            zipCode: "80202",
            bedrooms: 2,
            bathrooms: 1,
            propertyType: "CABIN",
            amenities: ["Fireplace", "Mountain Views", "Hot Tub"],
            images: ["https://images.unsplash.com/photo-1600607687920-4e3b04b8d1bb?w=800"]
          },
          landlord: { id: 6, name: "Emily Davis" },
          createdAt: "2024-02-05T08:30:00Z"
        },
        {
          id: 7,
          title: "Penthouse Loft",
          description: "Spacious penthouse loft with open floor plan and private rooftop access.",
          rentAmount: 7000,
          securityDeposit: 9000,
          availableDate: "2024-03-15T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: true,
          viewCount: 305,
          property: {
            id: 7,
            address: "99 Skyline Blvd",
            city: "San Francisco",
            state: "CA",
            zipCode: "94107",
            bedrooms: 2,
            bathrooms: 2,
            propertyType: "LOFT",
            amenities: ["Rooftop Deck", "Gym", "Smart Home System"],
            images: ["https://images.unsplash.com/photo-1502672023488-70e25813eb80?w=800"]
          },
          landlord: { id: 7, name: "Sophia Turner" },
          createdAt: "2024-02-18T11:00:00Z"
        },
        {
          id: 8,
          title: "Country Farmhouse",
          description: "Traditional 4-bedroom farmhouse with wide porches and large land area.",
          rentAmount: 2800,
          securityDeposit: 3500,
          availableDate: "2024-05-01T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "RENT",
          isFeatured: false,
          viewCount: 142,
          property: {
            id: 8,
            address: "45 Country Lane",
            city: "Nashville",
            state: "TN",
            zipCode: "37211",
            bedrooms: 4,
            bathrooms: 3,
            propertyType: "HOUSE",
            amenities: ["Barn", "Large Yard", "Fireplace"],
            images: ["https://images.unsplash.com/photo-1570129477492-45c003edd2be?w=800"]
          },
          landlord: { id: 8, name: "Robert Hall" },
          createdAt: "2024-02-25T14:00:00Z"
        },
        {
          id: 9,
          title: "Lakeview Villa",
          description: "Elegant villa with private dock and panoramic lake views.",
          rentAmount: 8500,
          securityDeposit: 10000,
          availableDate: "2024-06-01T00:00:00Z",
          leaseTermMonths: 12,
          status: "ACTIVE",
          type: "SALE",
          isFeatured: true,
          viewCount: 420,
          property: {
            id: 9,
            address: "777 Lakeside Drive",
            city: "Seattle",
            state: "WA",
            zipCode: "98109",
            bedrooms: 5,
            bathrooms: 4,
            propertyType: "VILLA",
            amenities: ["Private Dock", "Garden", "Sauna"],
            images: ["https://images.unsplash.com/photo-1568605114967-8130f3a36994?w=800"]
          },
          landlord: { id: 9, name: "Olivia Martinez" },
          createdAt: "2024-03-01T16:00:00Z"
        }
      ];

      setListings(mockListings);
    } catch (error) {
      console.error("Failed to load listings:", error);
    } finally {
      setLoading(false);
    }
  };


  const filteredListings = listings.filter(listing => {
    const matchesSearch = listing.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         listing.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         listing.property.city.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchesCity = !selectedCity || listing.property.city === selectedCity;
    const matchesType = !selectedType || listing.type === selectedType;
    const matchesStatus = !selectedStatus || listing.status === selectedStatus;
    
    const matchesRent = (!minRent || listing.rentAmount >= Number(minRent)) &&
                       (!maxRent || listing.rentAmount <= Number(maxRent));

    return matchesSearch && matchesCity && matchesType && matchesStatus && matchesRent;
  });

  const sortedListings = [...filteredListings].sort((a, b) => {
    let aValue: any = a[sortBy as keyof Listing];
    let bValue: any = b[sortBy as keyof Listing];

    if (sortBy === 'property.city') {
      aValue = a.property.city;
      bValue = b.property.city;
    } else if (sortBy === 'landlord.name') {
      aValue = a.landlord.name;
      bValue = b.landlord.name;
    }

    if (typeof aValue === 'string') {
      aValue = aValue.toLowerCase();
      bValue = bValue.toLowerCase();
    }

    if (sortOrder === 'asc') {
      return aValue > bValue ? 1 : -1;
    } else {
      return aValue < bValue ? 1 : -1;
    }
  });

  const cities = Array.from(new Set(listings.map(listing => listing.property.city))).sort();
  const canCreateListing = isAuthenticated && (user?.role === 'LANDLORD' || user?.role === 'ADMIN');

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Header */}
      <div className="flex justify-between items-center mb-8">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Rental Listings</h1>
          <p className="text-gray-600 mt-2">Find your perfect rental property</p>
        </div>
        {canCreateListing && (
          <Link
            to="/listings/new"
            className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors"
          >
            Create Listing
          </Link>
        )}
      </div>

      {/* Filters and Search */}
      <div className="bg-white rounded-lg shadow-sm p-6 mb-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
          {/* Search */}
          <div className="lg:col-span-2">
            <label htmlFor="search" className="block text-sm font-medium text-gray-700 mb-2">
              Search
            </label>
            <input
              type="text"
              id="search"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              placeholder="Search by title, description, or city..."
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          {/* City Filter */}
          <div>
            <label htmlFor="city" className="block text-sm font-medium text-gray-700 mb-2">
              City
            </label>
            <select
              id="city"
              value={selectedCity}
              onChange={(e) => setSelectedCity(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="">All Cities</option>
              {cities.map(city => (
                <option key={city} value={city}>{city}</option>
              ))}
            </select>
          </div>

          {/* Type Filter */}
          <div>
            <label htmlFor="type" className="block text-sm font-medium text-gray-700 mb-2">
              Type
            </label>
            <select
              id="type"
              value={selectedType}
              onChange={(e) => setSelectedType(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="">All Types</option>
              <option value="RENT">Rent</option>
              <option value="SALE">Sale</option>
              <option value="SUBLET">Sublet</option>
            </select>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          {/* Status Filter */}
          <div>
            <label htmlFor="status" className="block text-sm font-medium text-gray-700 mb-2">
              Status
            </label>
            <select
              id="status"
              value={selectedStatus}
              onChange={(e) => setSelectedStatus(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="">All Statuses</option>
              <option value="ACTIVE">Active</option>
              <option value="INACTIVE">Inactive</option>
              <option value="EXPIRED">Expired</option>
              <option value="RENTED">Rented</option>
            </select>
          </div>

          {/* Min Rent */}
          <div>
            <label htmlFor="minRent" className="block text-sm font-medium text-gray-700 mb-2">
              Min Rent
            </label>
            <input
              type="number"
              id="minRent"
              value={minRent}
              onChange={(e) => setMinRent(e.target.value)}
              placeholder="Min"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          {/* Max Rent */}
          <div>
            <label htmlFor="maxRent" className="block text-sm font-medium text-gray-700 mb-2">
              Max Rent
            </label>
            <input
              type="number"
              id="maxRent"
              value={maxRent}
              onChange={(e) => setMaxRent(e.target.value)}
              placeholder="Max"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          {/* Sort */}
          <div>
            <label htmlFor="sort" className="block text-sm font-medium text-gray-700 mb-2">
              Sort By
            </label>
            <select
              id="sort"
              value={`${sortBy}-${sortOrder}`}
              onChange={(e) => {
                const [field, order] = e.target.value.split('-');
                setSortBy(field);
                setSortOrder(order);
              }}
              className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            >
              <option value="createdAt-desc">Newest First</option>
              <option value="createdAt-asc">Oldest First</option>
              <option value="rentAmount-asc">Rent: Low to High</option>
              <option value="rentAmount-desc">Rent: High to Low</option>
              <option value="viewCount-desc">Most Popular</option>
              <option value="property.city-asc">City: A-Z</option>
            </select>
          </div>
        </div>
      </div>

      {/* Results Count */}
      <div className="mb-6">
        <p className="text-gray-600">
          Showing {sortedListings.length} of {listings.length} listings
        </p>
      </div>

      {/* Listings Grid */}
      {sortedListings.length === 0 ? (
        <div className="text-center py-12">
          <h3 className="text-lg font-medium text-gray-900 mb-2">No listings found</h3>
          <p className="text-gray-600">Try adjusting your search criteria or filters.</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {sortedListings.map(listing => (
            <div key={listing.id} className="bg-white rounded-lg shadow-sm overflow-hidden hover:shadow-md transition-shadow">
              {/* Image */}
              <div className="relative h-48 bg-gray-200">
                {listing.property.images.length > 0 ? (
                  <img
                    src={listing.property.images[0]}
                    alt={listing.title}
                    className="w-full h-full object-cover"
                  />
                ) : (
                  <div className="w-full h-full flex items-center justify-center text-gray-400">
                    <svg className="w-16 h-16" fill="currentColor" viewBox="0 0 20 20">
                      <path fillRule="evenodd" d="M4 3a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V5a2 2 0 00-2-2H4zm12 12H4l4-8 3 6 2-4 3 6z" clipRule="evenodd" />
                    </svg>
                  </div>
                )}
                
                {/* Featured Badge */}
                {listing.isFeatured && (
                  <div className="absolute top-2 left-2 bg-yellow-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                    Featured
                  </div>
                )}
                
                {/* Status Badge */}
                <div className="absolute top-2 right-2 bg-green-500 text-white px-2 py-1 rounded-full text-xs font-medium">
                  {listing.status}
                </div>
              </div>

              {/* Content */}
              <div className="p-6">
                <div className="flex justify-between items-start mb-2">
                  <h3 className="text-lg font-semibold text-gray-900 line-clamp-2">
                    {listing.title}
                  </h3>
                  <span className="text-2xl font-bold text-blue-600">
                    ${listing.rentAmount}
                  </span>
                </div>

                <p className="text-gray-600 text-sm mb-4 line-clamp-2">
                  {listing.description}
                </p>

                <div className="flex items-center text-gray-500 text-sm mb-4">
                  <svg className="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path fillRule="evenodd" d="M5.05 4.05a7 7 0 119.9 9.9L10 18.9l-4.95-4.95a7 7 0 010-9.9zM10 11a2 2 0 100-4 2 2 0 000 4z" clipRule="evenodd" />
                  </svg>
                  {listing.property.address}, {listing.property.city}, {listing.property.state}
                </div>

                <div className="flex items-center justify-between text-sm text-gray-500 mb-4">
                  <div className="flex items-center space-x-4">
                    <span>{listing.property.bedrooms} bed</span>
                    <span>{listing.property.bathrooms} bath</span>
                    <span>{listing.property.propertyType}</span>
                  </div>
                  <span>{listing.viewCount} views</span>
                </div>

                <div className="flex items-center justify-between">
                  <div className="text-sm text-gray-500">
                    Available: {new Date(listing.availableDate).toLocaleDateString()}
                  </div>
                  <Link
                    to={`/listings/${listing.id}`}
                    className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors text-sm"
                  >
                    View Details
                  </Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default ListingList;

import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';

interface Property {
  id: number;
  title: string;
  description: string;
  address: string;
  city: string;
  state: string;
  zipCode: string;
  rentAmount: number;
  securityDeposit: number;
  bedrooms: number;
  bathrooms: number;
  squareFootage: number;
  propertyType: string;
  furnishingStatus: string;
  petPolicy: string;
  smokingPolicy: string;
  amenities: string[];
  images: string[];
  status: string;
  landlord: {
    id: number;
    name: string;
    email: string;
    phone: string;
  };
  createdAt: string;
  updatedAt: string;
}

const PropertyDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { user, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [property, setProperty] = useState<Property | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeImageIndex, setActiveImageIndex] = useState(0);

  useEffect(() => {
    // Mock data - replace with actual API call
    const mockProperty: Property = {
      id: parseInt(id || '1'),
      title: "Modern Downtown Apartment",
      description: "Beautiful 2-bedroom apartment in the heart of downtown. Recently renovated with modern amenities and stunning city views. Perfect for young professionals or small families.",
      address: "123 Main Street",
      city: "New York",
      state: "NY",
      zipCode: "10001",
      rentAmount: 2500,
      securityDeposit: 3000,
      bedrooms: 2,
      bathrooms: 2,
      squareFootage: 1200,
      propertyType: "APARTMENT",
      furnishingStatus: "FURNISHED",
      petPolicy: "ALLOWED",
      smokingPolicy: "NOT_ALLOWED",
      amenities: ["Central Air", "Dishwasher", "Gym", "Pool", "Parking", "Balcony"],
      images: [
        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800",
        "https://images.unsplash.com/photo-1560448075-bb485b067938?w=800",
        "https://images.unsplash.com/photo-1560448204-6031743e9d7d?w=800"
      ],
      status: "AVAILABLE",
      landlord: {
        id: 1,
        name: "John Smith",
        email: "john.smith@email.com",
        phone: "(555) 123-4567"
      },
      createdAt: "2024-01-15T10:00:00Z",
      updatedAt: "2024-01-15T10:00:00Z"
    };

    setProperty(mockProperty);
    setLoading(false);
  }, [id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (error || !property) {
    return (
      <div className="text-center py-12">
        <h2 className="text-2xl font-bold text-gray-900 mb-4">Property Not Found</h2>
        <p className="text-gray-600 mb-6">The property you're looking for doesn't exist or has been removed.</p>
        <Link to="/properties" className="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700">
          Back to Properties
        </Link>
      </div>
    );
  }

  const canEdit = isAuthenticated && (user?.role === 'LANDLORD' || user?.role === 'ADMIN');

  return (
    <div className="max-w-7xl mx-auto px-4 py-8">
      {/* Breadcrumb */}
      <nav className="flex mb-8" aria-label="Breadcrumb">
        <ol className="inline-flex items-center space-x-1 md:space-x-3">
          <li className="inline-flex items-center">
            <Link to="/" className="text-gray-700 hover:text-blue-600">Home</Link>
          </li>
          <li>
            <div className="flex items-center">
              <svg className="w-6 h-6 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
              </svg>
              <Link to="/properties" className="ml-1 text-gray-700 hover:text-blue-600 md:ml-2">Properties</Link>
            </div>
          </li>
          <li aria-current="page">
            <div className="flex items-center">
              <svg className="w-6 h-6 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
              </svg>
              <span className="ml-1 text-gray-500 md:ml-2">{property.title}</span>
            </div>
          </li>
        </ol>
      </nav>

      {/* Property Header */}
      <div className="mb-8">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h1 className="text-4xl font-bold text-gray-900 mb-2">{property.title}</h1>
            <p className="text-xl text-gray-600">{property.address}, {property.city}, {property.state} {property.zipCode}</p>
          </div>
          {canEdit && (
            <Link
              to={`/properties/${property.id}/edit`}
              className="bg-blue-600 text-white px-6 py-3 rounded-lg hover:bg-blue-700 transition-colors"
            >
              Edit Property
            </Link>
          )}
        </div>
        
        {/* Status Badge */}
        <div className="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium bg-green-100 text-green-800">
          {property.status}
        </div>
      </div>

      {/* Image Gallery */}
      <div className="mb-8">
        <div className="relative h-96 rounded-lg overflow-hidden mb-4">
          <img
            src={property.images[activeImageIndex]}
            alt={property.title}
            className="w-full h-full object-cover"
          />
        </div>
        
        {/* Thumbnail Navigation */}
        <div className="flex space-x-2">
          {property.images.map((image, index) => (
            <button
              key={index}
              onClick={() => setActiveImageIndex(index)}
              className={`w-20 h-20 rounded-lg overflow-hidden border-2 ${
                index === activeImageIndex ? 'border-blue-600' : 'border-gray-300'
              }`}
            >
              <img
                src={image}
                alt={`${property.title} ${index + 1}`}
                className="w-full h-full object-cover"
              />
            </button>
          ))}
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Main Content */}
        <div className="lg:col-span-2">
          {/* Description */}
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Description</h2>
            <p className="text-gray-700 leading-relaxed">{property.description}</p>
          </div>

          {/* Property Details */}
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Property Details</h2>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <span className="text-gray-600">Property Type:</span>
                <span className="ml-2 font-medium">{property.propertyType}</span>
              </div>
              <div>
                <span className="text-gray-600">Furnishing:</span>
                <span className="ml-2 font-medium">{property.furnishingStatus}</span>
              </div>
              <div>
                <span className="text-gray-600">Bedrooms:</span>
                <span className="ml-2 font-medium">{property.bedrooms}</span>
              </div>
              <div>
                <span className="text-gray-600">Bathrooms:</span>
                <span className="ml-2 font-medium">{property.bathrooms}</span>
              </div>
              <div>
                <span className="text-gray-600">Square Footage:</span>
                <span className="ml-2 font-medium">{property.squareFootage} sq ft</span>
              </div>
              <div>
                <span className="text-gray-600">Pet Policy:</span>
                <span className="ml-2 font-medium">{property.petPolicy}</span>
              </div>
              <div>
                <span className="text-gray-600">Smoking Policy:</span>
                <span className="ml-2 font-medium">{property.smokingPolicy}</span>
              </div>
            </div>
          </div>

          {/* Amenities */}
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Amenities</h2>
            <div className="grid grid-cols-2 gap-2">
              {property.amenities.map((amenity, index) => (
                <div key={index} className="flex items-center">
                  <svg className="w-5 h-5 text-green-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                  </svg>
                  <span className="text-gray-700">{amenity}</span>
                </div>
              ))}
            </div>
          </div>
        </div>

        {/* Sidebar */}
        <div className="lg:col-span-1">
          {/* Pricing Card */}
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6 sticky top-8">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Pricing</h2>
            <div className="space-y-3 mb-6">
              <div className="flex justify-between">
                <span className="text-gray-600">Monthly Rent:</span>
                <span className="text-2xl font-bold text-gray-900">${property.rentAmount}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">Security Deposit:</span>
                <span className="font-medium text-gray-900">${property.securityDeposit}</span>
              </div>
            </div>
            
            {isAuthenticated && user?.role === 'TENANT' ? (
              <Link
                to={`/applications/new?propertyId=${property.id}`}
                className="w-full bg-green-600 text-white py-3 px-4 rounded-lg hover:bg-green-700 transition-colors text-center block"
              >
                Apply Now
              </Link>
            ) : (
              <button
                onClick={() => navigate('/login')}
                className="w-full bg-blue-600 text-white py-3 px-4 rounded-lg hover:bg-blue-700 transition-colors"
              >
                Sign In to Apply
              </button>
            )}
          </div>

          {/* Landlord Information */}
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Landlord</h2>
            <div className="space-y-2">
              <p className="text-gray-700">
                <span className="font-medium">Name:</span> {property.landlord.name}
              </p>
              <p className="text-gray-700">
                <span className="font-medium">Email:</span> {property.landlord.email}
              </p>
              <p className="text-gray-700">
                <span className="font-medium">Phone:</span> {property.landlord.phone}
              </p>
            </div>
          </div>

          {/* Property Status */}
          <div className="bg-white rounded-lg shadow-sm p-6">
            <h2 className="text-2xl font-semibold text-gray-900 mb-4">Property Status</h2>
            <div className="space-y-2">
              <p className="text-gray-700">
                <span className="font-medium">Listed:</span> {new Date(property.createdAt).toLocaleDateString()}
              </p>
              <p className="text-gray-700">
                <span className="font-medium">Last Updated:</span> {new Date(property.updatedAt).toLocaleDateString()}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PropertyDetail;

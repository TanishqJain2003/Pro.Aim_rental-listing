import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";

const mockListings = [
  {
    id: 1,
    title: "Modern Downtown Apartment",
    description: "Beautiful 2-bedroom apartment in the heart of downtown.",
    rentAmount: 2500,
    securityDeposit: 3000,
    property: {
      address: "123 Main Street",
      city: "New York",
      state: "NY",
      zipCode: "10001",
      bedrooms: 2,
      bathrooms: 2,
      propertyType: "APARTMENT",
      images: ["https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800"]
    },
    landlord: { name: "John Smith" }
  },
  {
    id: 2,
    title: "Cozy Suburban House",
    description: "Charming 3-bedroom house in a quiet suburban neighborhood.",
    rentAmount: 3200,
    securityDeposit: 4000,
    property: {
      address: "456 Oak Avenue",
      city: "Brooklyn",
      state: "NY",
      zipCode: "11201",
      bedrooms: 3,
      bathrooms: 2,
      propertyType: "HOUSE",
      images: ["https://images.unsplash.com/photo-1560448075-bb485b067938?w=800"]
    },
    landlord: { name: "Sarah Johnson" }
  },
  {
    id: 9,
    title: "Luxury Penthouse Suite",
    description: "Experience breathtaking skyline views in this luxury penthouse suite with modern amenities.",
    rentAmount: 7500,
    securityDeposit: 10000,
    property: {
      address: "999 Skyline Blvd",
      city: "Manhattan",
      state: "NY",
      zipCode: "10019",
      bedrooms: 4,
      bathrooms: 3,
      propertyType: "PENTHOUSE",
      images: ["https://images.unsplash.com/photo-1600585154340-be6161a56a0c?w=800"]
    },
    landlord: { name: "Michael Anderson" }
  }
];
  // add more mock listings if needed


const ListingDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const location = useLocation();
  const [listing, setListing] = useState<any>(location.state?.listing || null);

  useEffect(() => {
    if (!listing && id) {
      const found = mockListings.find((l) => l.id === Number(id));
      setListing(found || null);
    }
  }, [id, listing]);

  if (!listing) {
    return (
      <div className="max-w-4xl mx-auto px-4 py-8">
        <h1 className="text-2xl font-bold text-red-600">Listing not found</h1>
        <p className="text-gray-600">
          No data available for listing ID: {id}. Try going back to the listings page.
        </p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-4">{listing.title}</h1>

      {listing.property.images?.length > 0 && (
        <img
          src={listing.property.images[0]}
          alt={listing.title}
          className="w-full h-64 object-cover rounded-lg mb-6"
        />
      )}

      <p className="text-gray-700 mb-4">{listing.description}</p>

      <div className="grid grid-cols-2 gap-4 text-gray-600 mb-6">
        <p><strong>Rent:</strong> ${listing.rentAmount}</p>
        <p><strong>Deposit:</strong> ${listing.securityDeposit}</p>
        <p><strong>Bedrooms:</strong> {listing.property.bedrooms}</p>
        <p><strong>Bathrooms:</strong> {listing.property.bathrooms}</p>
        <p><strong>Type:</strong> {listing.property.propertyType}</p>
        <p><strong>Landlord:</strong> {listing.landlord.name}</p>
      </div>

      <p className="text-gray-500">
        Location: {listing.property.address}, {listing.property.city},{" "}
        {listing.property.state} {listing.property.zipCode}
      </p>
    </div>
  );
};

export default ListingDetails;

package com.proaim.service;

import com.proaim.entity.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ListingService {
    
    // Basic CRUD operations
    Listing createListing(Listing listing);
    Listing updateListing(Long id, Listing listing);
    void deleteListing(Long id);
    Optional<Listing> getListingById(Long id);
    List<Listing> getAllListings();
    Page<Listing> getAllListings(Pageable pageable);
    
    // Listing management by landlord
    List<Listing> getListingsByLandlord(Long landlordId);
    Page<Listing> getListingsByLandlord(Long landlordId, Pageable pageable);
    Long countListingsByLandlord(Long landlordId);
    
    // Listing status management
    List<Listing> getListingsByStatus(Listing.ListingStatus status);
    Listing updateListingStatus(Long id, Listing.ListingStatus status);
    
    // Active listings
    List<Listing> getActiveListings();
    Page<Listing> getActiveListings(Pageable pageable);
    
    // Featured listings
    List<Listing> getFeaturedListings();
    Page<Listing> getFeaturedListings(Pageable pageable);
    
    // Listing search and filtering
    List<Listing> searchListings(String city, String state, BigDecimal minRent, BigDecimal maxRent,
                                Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed);
    Page<Listing> searchListings(String city, String state, BigDecimal minRent, BigDecimal maxRent,
                                Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed,
                                Pageable pageable);
    
    // Geographic search
    List<Listing> getListingsNearLocation(Double latitude, Double longitude, Double radius);
    
    // Listing by property
    List<Listing> getListingsByProperty(Long propertyId);
    
    // Listing by type
    List<Listing> getListingsByType(Listing.ListingType type);
    
    // Popular listings
    Page<Listing> getPopularListings(Pageable pageable);
    
    // Expiring listings
    List<Listing> getExpiringListings();
    
    // Listing analytics
    Long getTotalListingsCount();
    Long getActiveListingsCount();
    Long getFeaturedListingsCount();
    
    // Listing by amenities
    List<Listing> getListingsByAmenities(List<String> amenities);
    
    // Listing by property type
    List<Listing> getListingsByPropertyType(String propertyType);
    
    // Listing by furnishing status
    List<Listing> getListingsByFurnishingStatus(String furnishingStatus);
    
    // Price range search
    List<Listing> getListingsByRentRange(BigDecimal minRent, BigDecimal maxRent);
    
    // Bedroom and bathroom search
    List<Listing> getListingsByBedrooms(Integer bedrooms);
    List<Listing> getListingsByBathrooms(Integer bathrooms);
    
    // Pet and smoking policy search
    List<Listing> getListingsByPetPolicy(Boolean petsAllowed);
    List<Listing> getListingsBySmokingPolicy(Boolean smokingAllowed);
    
    // Lease term search
    List<Listing> getListingsByLeaseTerm(Integer leaseTermMonths);
    
    // View count management
    void incrementViewCount(Long listingId);
    
    // Featured listing management
    Listing setFeaturedStatus(Long id, Boolean isFeatured);
    Listing setFeaturedUntil(Long id, java.time.LocalDateTime featuredUntil);
}

package com.proaim.service;

import com.proaim.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
    
    // Basic CRUD operations
    Property createProperty(Property property);
    Property updateProperty(Long id, Property property);
    void deleteProperty(Long id);
    Optional<Property> getPropertyById(Long id);
    List<Property> getAllProperties();
    Page<Property> getAllProperties(Pageable pageable);
    
    // Property search and filtering
    List<Property> searchProperties(String city, String state, BigDecimal minRent, BigDecimal maxRent, 
                                   Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed);
    Page<Property> searchProperties(String city, String state, BigDecimal minRent, BigDecimal maxRent, 
                                   Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed, 
                                   Pageable pageable);
    
    // Property management by landlord
    List<Property> getPropertiesByLandlord(Long landlordId);
    Page<Property> getPropertiesByLandlord(Long landlordId, Pageable pageable);
    Long countPropertiesByLandlord(Long landlordId);
    
    // Property status management
    List<Property> getPropertiesByStatus(Property.PropertyStatus status);
    Property updatePropertyStatus(Long id, Property.PropertyStatus status);
    
    // Geographic search
    List<Property> getPropertiesNearLocation(Double latitude, Double longitude, Double radius);
    
    // Featured properties
    Page<Property> getFeaturedProperties(Pageable pageable);
    
    // Property analytics
    Long getTotalPropertiesCount();
    Long getAvailablePropertiesCount();
    Long getRentedPropertiesCount();
    
    // Property expiration management
    List<Property> getPropertiesExpiringSoon();
    
    // Property type management
    List<Property> getPropertiesByType(String propertyType);
    List<Property> getPropertiesByFurnishingStatus(String furnishingStatus);
    
    // Amenity-based search
    List<Property> getPropertiesByAmenities(List<String> amenities);
    
    // Price range search
    List<Property> getPropertiesByRentRange(BigDecimal minRent, BigDecimal maxRent);
    
    // Bedroom and bathroom search
    List<Property> getPropertiesByBedrooms(Integer bedrooms);
    List<Property> getPropertiesByBathrooms(Integer bathrooms);
    
    // Pet and smoking policy search
    List<Property> getPropertiesByPetPolicy(Boolean petsAllowed);
    List<Property> getPropertiesBySmokingPolicy(Boolean smokingAllowed);
    
    // Lease term search
    List<Property> getPropertiesByLeaseTerm(Integer leaseTermMonths);
}

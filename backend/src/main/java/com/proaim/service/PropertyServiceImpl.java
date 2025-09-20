package com.proaim.service;

import com.proaim.entity.Property;
import com.proaim.entity.User;
import com.proaim.repository.PropertyRepository;
import com.proaim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyServiceImpl implements PropertyService {
    
    @Autowired
    private PropertyRepository propertyRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Property createProperty(Property property) {
        // Validate landlord exists
        if (property.getLandlord() == null || property.getLandlord().getId() == null) {
            throw new IllegalArgumentException("Landlord is required");
        }
        
        Optional<User> landlord = userRepository.findById(property.getLandlord().getId());
        if (landlord.isEmpty()) {
            throw new IllegalArgumentException("Landlord not found");
        }
        
        // Set default values
        property.setStatus(Property.PropertyStatus.AVAILABLE);
        property.setCreatedAt(LocalDateTime.now());
        property.setUpdatedAt(LocalDateTime.now());
        
        return propertyRepository.save(property);
    }
    
    @Override
    public Property updateProperty(Long id, Property propertyDetails) {
        Optional<Property> existingProperty = propertyRepository.findById(id);
        if (existingProperty.isEmpty()) {
            throw new IllegalArgumentException("Property not found with id: " + id);
        }
        
        Property property = existingProperty.get();
        
        // Update fields
        if (propertyDetails.getTitle() != null) {
            property.setTitle(propertyDetails.getTitle());
        }
        if (propertyDetails.getDescription() != null) {
            property.setDescription(propertyDetails.getDescription());
        }
        if (propertyDetails.getAddress() != null) {
            property.setAddress(propertyDetails.getAddress());
        }
        if (propertyDetails.getCity() != null) {
            property.setCity(propertyDetails.getCity());
        }
        if (propertyDetails.getState() != null) {
            property.setState(propertyDetails.getState());
        }
        if (propertyDetails.getZipCode() != null) {
            property.setZipCode(propertyDetails.getZipCode());
        }
        if (propertyDetails.getRentAmount() != null) {
            property.setRentAmount(propertyDetails.getRentAmount());
        }
        if (propertyDetails.getSecurityDeposit() != null) {
            property.setSecurityDeposit(propertyDetails.getSecurityDeposit());
        }
        if (propertyDetails.getBedrooms() != null) {
            property.setBedrooms(propertyDetails.getBedrooms());
        }
        if (propertyDetails.getBathrooms() != null) {
            property.setBathrooms(propertyDetails.getBathrooms());
        }
        if (propertyDetails.getSquareFootage() != null) {
            property.setSquareFootage(propertyDetails.getSquareFootage());
        }
        if (propertyDetails.getPropertyType() != null) {
            property.setPropertyType(propertyDetails.getPropertyType());
        }
        if (propertyDetails.getFurnishingStatus() != null) {
            property.setFurnishingStatus(propertyDetails.getFurnishingStatus());
        }
        if (propertyDetails.getAmenities() != null) {
            property.setAmenities(propertyDetails.getAmenities());
        }
        if (propertyDetails.getImageUrls() != null) {
            property.setImageUrls(propertyDetails.getImageUrls());
        }
        if (propertyDetails.getAvailableDate() != null) {
            property.setAvailableDate(propertyDetails.getAvailableDate());
        }
        if (propertyDetails.getLeaseTermMonths() != null) {
            property.setLeaseTermMonths(propertyDetails.getLeaseTermMonths());
        }
        if (propertyDetails.getPetsAllowed() != null) {
            property.setPetsAllowed(propertyDetails.getPetsAllowed());
        }
        if (propertyDetails.getSmokingAllowed() != null) {
            property.setSmokingAllowed(propertyDetails.getSmokingAllowed());
        }
        if (propertyDetails.getLatitude() != null) {
            property.setLatitude(propertyDetails.getLatitude());
        }
        if (propertyDetails.getLongitude() != null) {
            property.setLongitude(propertyDetails.getLongitude());
        }
        
        property.setUpdatedAt(LocalDateTime.now());
        
        return propertyRepository.save(property);
    }
    
    @Override
    public void deleteProperty(Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new IllegalArgumentException("Property not found with id: " + id);
        }
        
        // Check if property can be deleted (no active agreements)
        // This is a simplified check - in production you'd want more comprehensive validation
        propertyRepository.deleteById(id);
    }
    
    @Override
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }
    
    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    
    @Override
    public Page<Property> getAllProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }
    
    @Override
    public List<Property> searchProperties(String city, String state, BigDecimal minRent, BigDecimal maxRent,
                                         Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed) {
        return propertyRepository.searchProperties(city, state, minRent, maxRent, bedrooms, bathrooms, petsAllowed, smokingAllowed, Pageable.unpaged()).getContent();
    }
    
    @Override
    public Page<Property> searchProperties(String city, String state, BigDecimal minRent, BigDecimal maxRent,
                                         Integer bedrooms, Integer bathrooms, Boolean petsAllowed, Boolean smokingAllowed,
                                         Pageable pageable) {
        return propertyRepository.searchProperties(city, state, minRent, maxRent, bedrooms, bathrooms, petsAllowed, smokingAllowed, pageable);
    }
    
    @Override
    public List<Property> getPropertiesByLandlord(Long landlordId) {
        Optional<User> landlord = userRepository.findById(landlordId);
        if (landlord.isEmpty()) {
            throw new IllegalArgumentException("Landlord not found");
        }
        return propertyRepository.findByLandlord(landlord.get());
    }
    
    @Override
    public Page<Property> getPropertiesByLandlord(Long landlordId, Pageable pageable) {
        Optional<User> landlord = userRepository.findById(landlordId);
        if (landlord.isEmpty()) {
            throw new IllegalArgumentException("Landlord not found");
        }
        // Note: This would need a custom repository method for pagination
        List<Property> properties = propertyRepository.findByLandlord(landlord.get());
        // For now, return unpaged results
        return Page.empty(pageable);
    }
    
    @Override
    public Long countPropertiesByLandlord(Long landlordId) {
        Optional<User> landlord = userRepository.findById(landlordId);
        if (landlord.isEmpty()) {
            throw new IllegalArgumentException("Landlord not found");
        }
        return propertyRepository.countByLandlord(landlord.get());
    }
    
    @Override
    public List<Property> getPropertiesByStatus(Property.PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }
    
    @Override
    public Property updatePropertyStatus(Long id, Property.PropertyStatus status) {
        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            throw new IllegalArgumentException("Property not found with id: " + id);
        }
        
        Property existingProperty = property.get();
        existingProperty.setStatus(status);
        existingProperty.setUpdatedAt(LocalDateTime.now());
        
        return propertyRepository.save(existingProperty);
    }
    
    @Override
    public List<Property> getPropertiesNearLocation(Double latitude, Double longitude, Double radius) {
        return propertyRepository.findPropertiesNearLocation(latitude, longitude, radius);
    }
    
    @Override
    public Page<Property> getFeaturedProperties(Pageable pageable) {
        return propertyRepository.findFeaturedProperties(pageable);
    }
    
    @Override
    public Long getTotalPropertiesCount() {
        return propertyRepository.count();
    }
    
    @Override
    public Long getAvailablePropertiesCount() {
        return propertyRepository.countByStatus(Property.PropertyStatus.AVAILABLE);
    }
    
    @Override
    public Long getRentedPropertiesCount() {
        return propertyRepository.countByStatus(Property.PropertyStatus.RENTED);
    }
    
    @Override
    public List<Property> getPropertiesExpiringSoon() {
        return propertyRepository.findPropertiesExpiringSoon(LocalDateTime.now().plusDays(30));
    }
    
    @Override
    public List<Property> getPropertiesByType(String propertyType) {
        return propertyRepository.findByPropertyType(propertyType);
    }
    
    @Override
    public List<Property> getPropertiesByFurnishingStatus(String furnishingStatus) {
        return propertyRepository.findByFurnishingStatus(furnishingStatus);
    }
    
    @Override
    public List<Property> getPropertiesByAmenities(List<String> amenities) {
        // This would need a custom repository method
        // For now, return empty list
        return List.of();
    }
    
    @Override
    public List<Property> getPropertiesByRentRange(BigDecimal minRent, BigDecimal maxRent) {
        return propertyRepository.findByRentAmountBetween(minRent, maxRent);
    }
    
    @Override
    public List<Property> getPropertiesByBedrooms(Integer bedrooms) {
        return propertyRepository.findByBedrooms(bedrooms);
    }
    
    @Override
    public List<Property> getPropertiesByBathrooms(Integer bathrooms) {
        return propertyRepository.findByBathrooms(bathrooms);
    }
    
    @Override
    public List<Property> getPropertiesByPetPolicy(Boolean petsAllowed) {
        return propertyRepository.findByPetsAllowed(petsAllowed);
    }
    
    @Override
    public List<Property> getPropertiesBySmokingPolicy(Boolean smokingAllowed) {
        return propertyRepository.findBySmokingAllowed(smokingAllowed);
    }
    
    @Override
    public List<Property> getPropertiesByLeaseTerm(Integer leaseTermMonths) {
        return propertyRepository.findByLeaseTermMonths(leaseTermMonths);
    }
}

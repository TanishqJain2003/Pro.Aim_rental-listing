package com.proaim.controller;

import com.proaim.entity.Property;
import com.proaim.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {
    
    @Autowired
    private PropertyService propertyService;
    
    // Get all properties with pagination
    @GetMapping
    public ResponseEntity<Page<Property>> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Property> properties = propertyService.getAllProperties(pageable);
        return ResponseEntity.ok(properties);
    }
    
    // Get property by ID
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Create new property (Landlord only)
    @PostMapping
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Property> createProperty(@Valid @RequestBody Property property) {
        try {
            Property createdProperty = propertyService.createProperty(property);
            return ResponseEntity.ok(createdProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Update property (Landlord only)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Valid @RequestBody Property property) {
        try {
            Property updatedProperty = propertyService.updateProperty(id, property);
            return ResponseEntity.ok(updatedProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Delete property (Landlord only)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Search properties
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) BigDecimal minRent,
            @RequestParam(required = false) BigDecimal maxRent,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) Boolean petsAllowed,
            @RequestParam(required = false) Boolean smokingAllowed) {
        
        List<Property> properties = propertyService.searchProperties(
                city, state, minRent, maxRent, bedrooms, bathrooms, petsAllowed, smokingAllowed);
        return ResponseEntity.ok(properties);
    }
    
    // Search properties with pagination
    @GetMapping("/search/paginated")
    public ResponseEntity<Page<Property>> searchPropertiesPaginated(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) BigDecimal minRent,
            @RequestParam(required = false) BigDecimal maxRent,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) Boolean petsAllowed,
            @RequestParam(required = false) Boolean smokingAllowed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> properties = propertyService.searchProperties(
                city, state, minRent, maxRent, bedrooms, bathrooms, petsAllowed, smokingAllowed, pageable);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by landlord
    @GetMapping("/landlord/{landlordId}")
    public ResponseEntity<List<Property>> getPropertiesByLandlord(@PathVariable Long landlordId) {
        try {
            List<Property> properties = propertyService.getPropertiesByLandlord(landlordId);
            return ResponseEntity.ok(properties);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Get properties by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Property>> getPropertiesByStatus(@PathVariable String status) {
        try {
            Property.PropertyStatus propertyStatus = Property.PropertyStatus.valueOf(status.toUpperCase());
            List<Property> properties = propertyService.getPropertiesByStatus(propertyStatus);
            return ResponseEntity.ok(properties);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Update property status
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Property> updatePropertyStatus(
            @PathVariable Long id, @RequestParam String status) {
        try {
            Property.PropertyStatus propertyStatus = Property.PropertyStatus.valueOf(status.toUpperCase());
            Property updatedProperty = propertyService.updatePropertyStatus(id, propertyStatus);
            return ResponseEntity.ok(updatedProperty);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // Get properties near location (for map view)
    @GetMapping("/near-location")
    public ResponseEntity<List<Property>> getPropertiesNearLocation(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "10.0") Double radius) {
        
        List<Property> properties = propertyService.getPropertiesNearLocation(latitude, longitude, radius);
        return ResponseEntity.ok(properties);
    }
    
    // Get featured properties
    @GetMapping("/featured")
    public ResponseEntity<Page<Property>> getFeaturedProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Property> properties = propertyService.getFeaturedProperties(pageable);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by type
    @GetMapping("/type/{propertyType}")
    public ResponseEntity<List<Property>> getPropertiesByType(@PathVariable String propertyType) {
        List<Property> properties = propertyService.getPropertiesByType(propertyType);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by furnishing status
    @GetMapping("/furnishing/{furnishingStatus}")
    public ResponseEntity<List<Property>> getPropertiesByFurnishingStatus(@PathVariable String furnishingStatus) {
        List<Property> properties = propertyService.getPropertiesByFurnishingStatus(furnishingStatus);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by rent range
    @GetMapping("/rent-range")
    public ResponseEntity<List<Property>> getPropertiesByRentRange(
            @RequestParam BigDecimal minRent, @RequestParam BigDecimal maxRent) {
        List<Property> properties = propertyService.getPropertiesByRentRange(minRent, maxRent);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by bedrooms
    @GetMapping("/bedrooms/{bedrooms}")
    public ResponseEntity<List<Property>> getPropertiesByBedrooms(@PathVariable Integer bedrooms) {
        List<Property> properties = propertyService.getPropertiesByBedrooms(bedrooms);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by bathrooms
    @GetMapping("/bathrooms/{bathrooms}")
    public ResponseEntity<List<Property>> getPropertiesByBathrooms(@PathVariable Integer bathrooms) {
        List<Property> properties = propertyService.getPropertiesByBathrooms(bathrooms);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by pet policy
    @GetMapping("/pets/{petsAllowed}")
    public ResponseEntity<List<Property>> getPropertiesByPetPolicy(@PathVariable Boolean petsAllowed) {
        List<Property> properties = propertyService.getPropertiesByPetPolicy(petsAllowed);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by smoking policy
    @GetMapping("/smoking/{smokingAllowed}")
    public ResponseEntity<List<Property>> getPropertiesBySmokingPolicy(@PathVariable Boolean smokingAllowed) {
        List<Property> properties = propertyService.getPropertiesBySmokingPolicy(smokingAllowed);
        return ResponseEntity.ok(properties);
    }
    
    // Get properties by lease term
    @GetMapping("/lease-term/{leaseTermMonths}")
    public ResponseEntity<List<Property>> getPropertiesByLeaseTerm(@PathVariable Integer leaseTermMonths) {
        List<Property> properties = propertyService.getPropertiesByLeaseTerm(leaseTermMonths);
        return ResponseEntity.ok(properties);
    }
    
    // Get property analytics
    @GetMapping("/analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyAnalytics> getPropertyAnalytics() {
        PropertyAnalytics analytics = new PropertyAnalytics();
        analytics.setTotalProperties(propertyService.getTotalPropertiesCount());
        analytics.setAvailableProperties(propertyService.getAvailablePropertiesCount());
        analytics.setRentedProperties(propertyService.getRentedPropertiesCount());
        return ResponseEntity.ok(analytics);
    }
    
    // Get properties expiring soon
    @GetMapping("/expiring-soon")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Property>> getPropertiesExpiringSoon() {
        List<Property> properties = propertyService.getPropertiesExpiringSoon();
        return ResponseEntity.ok(properties);
    }
    
    // Inner class for analytics
    public static class PropertyAnalytics {
        private Long totalProperties;
        private Long availableProperties;
        private Long rentedProperties;
        
        // Getters and setters
        public Long getTotalProperties() { return totalProperties; }
        public void setTotalProperties(Long totalProperties) { this.totalProperties = totalProperties; }
        
        public Long getAvailableProperties() { return availableProperties; }
        public void setAvailableProperties(Long availableProperties) { this.availableProperties = availableProperties; }
        
        public Long getRentedProperties() { return rentedProperties; }
        public void setRentedProperties(Long rentedProperties) { this.rentedProperties = rentedProperties; }
    }
}

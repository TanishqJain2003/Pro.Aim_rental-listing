package com.proaim.repository;

import com.proaim.entity.Property;
import com.proaim.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    
    // Find properties by landlord
    List<Property> findByLandlord(User landlord);
    
    // Find properties by status
    List<Property> findByStatus(Property.PropertyStatus status);
    
    // Find properties by city
    List<Property> findByCity(String city);
    
    // Find properties by state
    List<Property> findByState(String state);
    
    // Find properties by property type
    List<Property> findByPropertyType(String propertyType);
    
    // Find properties by rent amount range
    List<Property> findByRentAmountBetween(BigDecimal minRent, BigDecimal maxRent);
    
    // Find properties by number of bedrooms
    List<Property> findByBedrooms(Integer bedrooms);
    
    // Find properties by number of bathrooms
    List<Property> findByBathrooms(Integer bathrooms);
    
    // Find properties by pet policy
    List<Property> findByPetsAllowed(Boolean petsAllowed);
    
    // Find properties by smoking policy
    List<Property> findBySmokingAllowed(Boolean smokingAllowed);
    
    // Find properties by furnishing status
    List<Property> findByFurnishingStatus(String furnishingStatus);
    
    // Find properties by lease term
    List<Property> findByLeaseTermMonths(Integer leaseTermMonths);
    
    // Search properties with multiple criteria
    @Query("SELECT p FROM Property p WHERE " +
           "(:city IS NULL OR p.city = :city) AND " +
           "(:state IS NULL OR p.state = :state) AND " +
           "(:minRent IS NULL OR p.rentAmount >= :minRent) AND " +
           "(:maxRent IS NULL OR p.rentAmount <= :maxRent) AND " +
           "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND " +
           "(:bathrooms IS NULL OR p.bathrooms >= :bathrooms) AND " +
           "(:petsAllowed IS NULL OR p.petsAllowed = :petsAllowed) AND " +
           "(:smokingAllowed IS NULL OR p.smokingAllowed = :smokingAllowed) AND " +
           "p.status = 'AVAILABLE'")
    Page<Property> searchProperties(
            @Param("city") String city,
            @Param("state") String state,
            @Param("minRent") BigDecimal minRent,
            @Param("maxRent") BigDecimal maxRent,
            @Param("bedrooms") Integer bedrooms,
            @Param("bathrooms") Integer bathrooms,
            @Param("petsAllowed") Boolean petsAllowed,
            @Param("smokingAllowed") Boolean smokingAllowed,
            Pageable pageable
    );
    
    // Find properties within a geographic radius (for map view)
    @Query("SELECT p FROM Property p WHERE " +
           "p.status = 'AVAILABLE' AND " +
           "p.latitude IS NOT NULL AND p.longitude IS NOT NULL AND " +
           "SQRT(POWER(p.latitude - :lat, 2) + POWER(p.longitude - :lng, 2)) <= :radius")
    List<Property> findPropertiesNearLocation(
            @Param("lat") Double latitude,
            @Param("lng") Double longitude,
            @Param("radius") Double radiusInDegrees
    );
    
    // Find featured properties
    @Query("SELECT p FROM Property p WHERE p.status = 'AVAILABLE' ORDER BY p.createdAt DESC")
    Page<Property> findFeaturedProperties(Pageable pageable);
    
    // Count properties by landlord
    Long countByLandlord(User landlord);
    
    // Count properties by status
    Long countByStatus(Property.PropertyStatus status);
    
    // Find properties expiring soon
    @Query("SELECT p FROM Property p WHERE p.availableDate <= :date AND p.status = 'AVAILABLE'")
    List<Property> findPropertiesExpiringSoon(@Param("date") java.time.LocalDateTime date);
}


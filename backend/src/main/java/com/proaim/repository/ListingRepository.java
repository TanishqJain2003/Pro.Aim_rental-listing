package com.proaim.repository;

import com.proaim.entity.Listing;
import com.proaim.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    // Find listings by landlord
    List<Listing> findByLandlord(User landlord);

    // Find listings by status
    List<Listing> findByStatus(Listing.ListingStatus status);

    // Find listings by type
    List<Listing> findByType(Listing.ListingType type);

    // Find active listings (updated method using @Query)
    @Query("SELECT l FROM Listing l WHERE l.status = 'ACTIVE'")
    List<Listing> findActiveListings();

    // Find featured listings
    List<Listing> findByIsFeaturedTrue();

    // Find listings by rent amount range
    List<Listing> findByRentAmountBetween(BigDecimal minRent, BigDecimal maxRent);

    // Find listings by available date
    List<Listing> findByAvailableDateAfter(LocalDateTime date);

    // Find listings by lease term
    List<Listing> findByLeaseTermMonths(Integer leaseTermMonths);

    // Find listings expiring soon
    @Query("SELECT l FROM Listing l WHERE l.expiresAt <= :date AND l.status = 'ACTIVE'")
    List<Listing> findExpiringSoon(@Param("date") LocalDateTime date);

    // Find listings by property city
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE p.city = :city AND l.status = 'ACTIVE'")
    List<Listing> findByPropertyCity(@Param("city") String city);

    // Find listings by property state
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE p.state = :state AND l.status = 'ACTIVE'")
    List<Listing> findByPropertyState(@Param("state") String state);

    // Search listings with multiple criteria
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE " +
            "l.status = 'ACTIVE' AND " +
            "(:city IS NULL OR p.city = :city) AND " +
            "(:state IS NULL OR p.state = :state) AND " +
            "(:minRent IS NULL OR l.rentAmount >= :minRent) AND " +
            "(:maxRent IS NULL OR l.rentAmount <= :maxRent) AND " +
            "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND " +
            "(:bathrooms IS NULL OR p.bathrooms >= :bathrooms) AND " +
            "(:petsAllowed IS NULL OR p.petsAllowed = :petsAllowed) AND " +
            "(:smokingAllowed IS NULL OR p.smokingAllowed = :smokingAllowed)")
    Page<Listing> searchListings(
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

    // Find listings within geographic radius
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE " +
            "l.status = 'ACTIVE' AND " +
            "p.latitude IS NOT NULL AND p.longitude IS NOT NULL AND " +
            "SQRT(POWER(p.latitude - :lat, 2) + POWER(p.longitude - :lng, 2)) <= :radius")
    List<Listing> findListingsNearLocation(
            @Param("lat") Double latitude,
            @Param("lng") Double longitude,
            @Param("radius") Double radiusInDegrees
    );

    // Find listings by property amenities
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE " +
            "l.status = 'ACTIVE' AND " +
            "EXISTS (SELECT 1 FROM p.amenities a WHERE a IN :amenities)")
    List<Listing> findByPropertyAmenities(@Param("amenities") List<String> amenities);

    // Find listings by property type
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE p.propertyType = :propertyType AND l.status = 'ACTIVE'")
    List<Listing> findByPropertyType(@Param("propertyType") String propertyType);

    // Find listings by furnishing status
    @Query("SELECT l FROM Listing l JOIN l.property p WHERE p.furnishingStatus = :furnishingStatus AND l.status = 'ACTIVE'")
    List<Listing> findByFurnishingStatus(@Param("furnishingStatus") String furnishingStatus);

    // Find listings with high view count (popular)
    @Query("SELECT l FROM Listing l WHERE l.status = 'ACTIVE' ORDER BY l.viewCount DESC")
    Page<Listing> findPopularListings(Pageable pageable);

    // Count listings by landlord
    Long countByLandlord(User landlord);

    // Count listings by status
    Long countByStatus(Listing.ListingStatus status);

    // Count active listings by city
    @Query("SELECT COUNT(l) FROM Listing l JOIN l.property p WHERE p.city = :city AND l.status = 'ACTIVE'")
    Long countActiveListingsByCity(@Param("city") String city);
}

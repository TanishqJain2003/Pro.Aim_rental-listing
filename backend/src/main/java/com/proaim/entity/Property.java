package com.proaim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "properties")
public class Property {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotBlank(message = "City is required")
    private String city;
    
    @NotBlank(message = "State is required")
    private String state;
    
    @NotBlank(message = "Zip code is required")
    private String zipCode;
    
    @NotNull(message = "Rent amount is required")
    @Positive(message = "Rent amount must be positive")
    private BigDecimal rentAmount;
    
    @NotNull(message = "Security deposit is required")
    @Positive(message = "Security deposit must be positive")
    private BigDecimal securityDeposit;
    
    @NotNull(message = "Bedrooms count is required")
    @Positive(message = "Bedrooms count must be positive")
    private Integer bedrooms;
    
    @NotNull(message = "Bathrooms count is required")
    @Positive(message = "Bathrooms count must be positive")
    private Integer bathrooms;
    
    @NotNull(message = "Square footage is required")
    @Positive(message = "Square footage must be positive")
    private Integer squareFootage;
    
    @NotBlank(message = "Property type is required")
    private String propertyType; // APARTMENT, HOUSE, CONDO, etc.
    
    @NotBlank(message = "Furnishing status is required")
    private String furnishingStatus; // FURNISHED, UNFURNISHED, PARTIALLY_FURNISHED
    
    @ElementCollection
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "amenity")
    private List<String> amenities;
    
    @ElementCollection
    @CollectionTable(name = "property_images", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;
    
    @NotNull(message = "Available date is required")
    private LocalDateTime availableDate;
    
    @NotNull(message = "Lease term is required")
    private Integer leaseTermMonths;
    
    @NotNull(message = "Pet policy is required")
    private Boolean petsAllowed;
    
    @NotNull(message = "Smoking policy is required")
    private Boolean smokingAllowed;
    
    @Enumerated(EnumType.STRING)
    private PropertyStatus status = PropertyStatus.AVAILABLE;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Listing> listings;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications;
    
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Agreement> agreements;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Coordinates for map view
    private Double latitude;
    private Double longitude;
    
    public enum PropertyStatus {
        AVAILABLE, RENTED, UNDER_MAINTENANCE, OFF_MARKET
    }
    
    // Constructors
    public Property() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public BigDecimal getRentAmount() {
        return rentAmount;
    }
    
    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }
    
    public BigDecimal getSecurityDeposit() {
        return securityDeposit;
    }
    
    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }
    
    public Integer getBedrooms() {
        return bedrooms;
    }
    
    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }
    
    public Integer getBathrooms() {
        return bathrooms;
    }
    
    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }
    
    public Integer getSquareFootage() {
        return squareFootage;
    }
    
    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }
    
    public String getPropertyType() {
        return propertyType;
    }
    
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
    public String getFurnishingStatus() {
        return furnishingStatus;
    }
    
    public void setFurnishingStatus(String furnishingStatus) {
        this.furnishingStatus = furnishingStatus;
    }
    
    public List<String> getAmenities() {
        return amenities;
    }
    
    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }
    
    public List<String> getImageUrls() {
        return imageUrls;
    }
    
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
    
    public LocalDateTime getAvailableDate() {
        return availableDate;
    }
    
    public void setAvailableDate(LocalDateTime availableDate) {
        this.availableDate = availableDate;
    }
    
    public Integer getLeaseTermMonths() {
        return leaseTermMonths;
    }
    
    public void setLeaseTermMonths(Integer leaseTermMonths) {
        this.leaseTermMonths = leaseTermMonths;
    }
    
    public Boolean getPetsAllowed() {
        return petsAllowed;
    }
    
    public void setPetsAllowed(Boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }
    
    public Boolean getSmokingAllowed() {
        return smokingAllowed;
    }
    
    public void setSmokingAllowed(Boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }
    
    public PropertyStatus getStatus() {
        return status;
    }
    
    public void setStatus(PropertyStatus status) {
        this.status = status;
    }
    
    public User getLandlord() {
        return landlord;
    }
    
    public void setLandlord(User landlord) {
        this.landlord = landlord;
    }
    
    public List<Listing> getListings() {
        return listings;
    }
    
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
    
    public List<Application> getApplications() {
        return applications;
    }
    
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    
    public List<Agreement> getAgreements() {
        return agreements;
    }
    
    public void setAgreements(List<Agreement> agreements) {
        this.agreements = agreements;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


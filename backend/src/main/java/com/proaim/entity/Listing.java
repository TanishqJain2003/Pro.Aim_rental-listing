package com.proaim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "listings")
public class Listing {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Rent amount is required")
    @Positive(message = "Rent amount must be positive")
    private BigDecimal rentAmount;
    
    @NotNull(message = "Security deposit is required")
    @Positive(message = "Security deposit must be positive")
    private BigDecimal securityDeposit;
    
    @NotNull(message = "Available date is required")
    private LocalDateTime availableDate;
    
    @NotNull(message = "Lease term is required")
    private Integer leaseTermMonths;
    
    @Enumerated(EnumType.STRING)
    private ListingStatus status = ListingStatus.ACTIVE;
    
    @Enumerated(EnumType.STRING)
    private ListingType type = ListingType.RENT;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @Column(name = "featured_until")
    private LocalDateTime featuredUntil;
    
    private Boolean isFeatured = false;
    
    private Integer viewCount = 0;
    
    public enum ListingStatus {
        ACTIVE, INACTIVE, EXPIRED, RENTED
    }
    
    public enum ListingType {
        RENT, SALE, SUBLET
    }
    
    // Constructors
    public Listing() {
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
    
    public ListingStatus getStatus() {
        return status;
    }
    
    public void setStatus(ListingStatus status) {
        this.status = status;
    }
    
    public ListingType getType() {
        return type;
    }
    
    public void setType(ListingType type) {
        this.type = type;
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public User getLandlord() {
        return landlord;
    }
    
    public void setLandlord(User landlord) {
        this.landlord = landlord;
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
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
    
    public LocalDateTime getFeaturedUntil() {
        return featuredUntil;
    }
    
    public void setFeaturedUntil(LocalDateTime featuredUntil) {
        this.featuredUntil = featuredUntil;
    }
    
    public Boolean getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
    
    public Integer getViewCount() {
        return viewCount;
    }
    
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


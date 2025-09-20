package com.proaim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;
    
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.PENDING;
    
    @NotBlank(message = "Cover letter is required")
    @Column(columnDefinition = "TEXT")
    private String coverLetter;
    
    @NotNull(message = "Monthly income is required")
    private BigDecimal monthlyIncome;
    
    @NotNull(message = "Employment status is required")
    private String employmentStatus;
    
    @NotBlank(message = "Employer name is required")
    private String employerName;
    
    @NotBlank(message = "Employer phone is required")
    private String employerPhone;
    
    @NotNull(message = "Rental history is required")
    @Column(columnDefinition = "TEXT")
    private String rentalHistory;
    
    @NotNull(message = "Credit score is required")
    private Integer creditScore;
    
    @NotNull(message = "Pets count is required")
    private Integer petsCount;
    
    @NotBlank(message = "Pet types are required")
    private String petTypes;
    
    @NotNull(message = "Occupants count is required")
    private Integer occupantsCount;
    
    @NotBlank(message = "Move-in date is required")
    private LocalDateTime moveInDate;
    
    @NotBlank(message = "Lease term preference is required")
    private Integer leaseTermPreference;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    
    @Column(name = "reviewed_by")
    private Long reviewedBy;
    
    private String rejectionReason;
    
    @Column(name = "application_fee")
    private BigDecimal applicationFee;
    
    @Column(name = "fee_paid")
    private Boolean feePaid = false;
    
    public enum ApplicationStatus {
        PENDING, UNDER_REVIEW, APPROVED, REJECTED, WITHDRAWN
    }
    
    // Constructors
    public Application() {
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
    
    public User getTenant() {
        return tenant;
    }
    
    public void setTenant(User tenant) {
        this.tenant = tenant;
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public Listing getListing() {
        return listing;
    }
    
    public void setListing(Listing listing) {
        this.listing = listing;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public String getCoverLetter() {
        return coverLetter;
    }
    
    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }
    
    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
    
    public String getEmploymentStatus() {
        return employmentStatus;
    }
    
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public String getEmployerName() {
        return employerName;
    }
    
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
    
    public String getEmployerPhone() {
        return employerPhone;
    }
    
    public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }
    
    public String getRentalHistory() {
        return rentalHistory;
    }
    
    public void setRentalHistory(String rentalHistory) {
        this.rentalHistory = rentalHistory;
    }
    
    public Integer getCreditScore() {
        return creditScore;
    }
    
    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
    
    public Integer getPetsCount() {
        return petsCount;
    }
    
    public void setPetsCount(Integer petsCount) {
        this.petsCount = petsCount;
    }
    
    public String getPetTypes() {
        return petTypes;
    }
    
    public void setPetTypes(String petTypes) {
        this.petTypes = petTypes;
    }
    
    public Integer getOccupantsCount() {
        return occupantsCount;
    }
    
    public void setOccupantsCount(Integer occupantsCount) {
        this.occupantsCount = occupantsCount;
    }
    
    public LocalDateTime getMoveInDate() {
        return moveInDate;
    }
    
    public void setMoveInDate(LocalDateTime moveInDate) {
        this.moveInDate = moveInDate;
    }
    
    public Integer getLeaseTermPreference() {
        return leaseTermPreference;
    }
    
    public void setLeaseTermPreference(Integer leaseTermPreference) {
        this.leaseTermPreference = leaseTermPreference;
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
    
    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }
    
    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
    
    public Long getReviewedBy() {
        return reviewedBy;
    }
    
    public void setReviewedBy(Long reviewedBy) {
        this.reviewedBy = reviewedBy;
    }
    
    public String getRejectionReason() {
        return rejectionReason;
    }
    
    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
    
    public BigDecimal getApplicationFee() {
        return applicationFee;
    }
    
    public void setApplicationFee(BigDecimal applicationFee) {
        this.applicationFee = applicationFee;
    }
    
    public Boolean getFeePaid() {
        return feePaid;
    }
    
    public void setFeePaid(Boolean feePaid) {
        this.feePaid = feePaid;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


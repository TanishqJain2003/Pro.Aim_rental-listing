package com.proaim.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "agreements")
public class Agreement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;
    
    @Enumerated(EnumType.STRING)
    private AgreementStatus status = AgreementStatus.DRAFT;
    
    @NotBlank(message = "Agreement number is required")
    @Column(unique = true)
    private String agreementNumber;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    @NotNull(message = "Rent amount is required")
    @Positive(message = "Rent amount must be positive")
    private BigDecimal rentAmount;
    
    @NotNull(message = "Security deposit is required")
    @Positive(message = "Security deposit must be positive")
    private BigDecimal securityDeposit;
    
    @NotNull(message = "Lease term is required")
    private Integer leaseTermMonths;
    
    @NotBlank(message = "Payment due date is required")
    private Integer paymentDueDay; // Day of month when rent is due
    
    @NotNull(message = "Late fee is required")
    private BigDecimal lateFee;
    
    @NotNull(message = "Pet deposit is required")
    private BigDecimal petDeposit;
    
    @NotNull(message = "Utilities included is required")
    private Boolean utilitiesIncluded;
    
    @Column(columnDefinition = "TEXT")
    private String utilitiesDetails;
    
    @NotNull(message = "Maintenance responsibility is required")
    @Column(columnDefinition = "TEXT")
    private String maintenanceResponsibility;
    
    @NotNull(message = "Pet policy is required")
    @Column(columnDefinition = "TEXT")
    private String petPolicy;
    
    @NotNull(message = "Smoking policy is required")
    @Column(columnDefinition = "TEXT")
    private String smokingPolicy;
    
    @NotNull(message = "Guest policy is required")
    @Column(columnDefinition = "TEXT")
    private String guestPolicy;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "signed_at")
    private LocalDateTime signedAt;
    
    @Column(name = "signed_by_tenant")
    private Boolean signedByTenant = false;
    
    @Column(name = "signed_by_landlord")
    private Boolean signedByLandlord = false;
    
    @Column(name = "tenant_signature")
    private String tenantSignature;
    
    @Column(name = "landlord_signature")
    private String landlordSignature;
    
    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;
    
    @Column(name = "termination_date")
    private LocalDateTime terminationDate;
    
    private String terminationReason;
    
    public enum AgreementStatus {
        DRAFT, PENDING_SIGNATURE, ACTIVE, EXPIRED, TERMINATED, RENEWED
    }
    
    // Constructors
    public Agreement() {
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
    
    public User getLandlord() {
        return landlord;
    }
    
    public void setLandlord(User landlord) {
        this.landlord = landlord;
    }
    
    public Property getProperty() {
        return property;
    }
    
    public void setProperty(Property property) {
        this.property = property;
    }
    
    public Application getApplication() {
        return application;
    }
    
    public void setApplication(Application application) {
        this.application = application;
    }
    
    public AgreementStatus getStatus() {
        return status;
    }
    
    public void setStatus(AgreementStatus status) {
        this.status = status;
    }
    
    public String getAgreementNumber() {
        return agreementNumber;
    }
    
    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
    
    public Integer getLeaseTermMonths() {
        return leaseTermMonths;
    }
    
    public void setLeaseTermMonths(Integer leaseTermMonths) {
        this.leaseTermMonths = leaseTermMonths;
    }
    
    public Integer getPaymentDueDay() {
        return paymentDueDay;
    }
    
    public void setPaymentDueDay(Integer paymentDueDay) {
        this.paymentDueDay = paymentDueDay;
    }
    
    public BigDecimal getLateFee() {
        return lateFee;
    }
    
    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }
    
    public BigDecimal getPetDeposit() {
        return petDeposit;
    }
    
    public void setPetDeposit(BigDecimal petDeposit) {
        this.petDeposit = petDeposit;
    }
    
    public Boolean getUtilitiesIncluded() {
        return utilitiesIncluded;
    }
    
    public void setUtilitiesIncluded(Boolean utilitiesIncluded) {
        this.utilitiesIncluded = utilitiesIncluded;
    }
    
    public String getUtilitiesDetails() {
        return utilitiesDetails;
    }
    
    public void setUtilitiesDetails(String utilitiesDetails) {
        this.utilitiesDetails = utilitiesDetails;
    }
    
    public String getMaintenanceResponsibility() {
        return maintenanceResponsibility;
    }
    
    public void setMaintenanceResponsibility(String maintenanceResponsibility) {
        this.maintenanceResponsibility = maintenanceResponsibility;
    }
    
    public String getPetPolicy() {
        return petPolicy;
    }
    
    public void setPetPolicy(String petPolicy) {
        this.petPolicy = petPolicy;
    }
    
    public String getSmokingPolicy() {
        return smokingPolicy;
    }
    
    public void setSmokingPolicy(String smokingPolicy) {
        this.smokingPolicy = smokingPolicy;
    }
    
    public String getGuestPolicy() {
        return guestPolicy;
    }
    
    public void setGuestPolicy(String guestPolicy) {
        this.guestPolicy = guestPolicy;
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
    
    public LocalDateTime getSignedAt() {
        return signedAt;
    }
    
    public void setSignedAt(LocalDateTime signedAt) {
        this.signedAt = signedAt;
    }
    
    public Boolean getSignedByTenant() {
        return signedByTenant;
    }
    
    public void setSignedByTenant(Boolean signedByTenant) {
        this.signedByTenant = signedByTenant;
    }
    
    public Boolean getSignedByLandlord() {
        return signedByLandlord;
    }
    
    public void setSignedByLandlord(Boolean signedByLandlord) {
        this.signedByLandlord = signedByLandlord;
    }
    
    public String getTenantSignature() {
        return tenantSignature;
    }
    
    public void setTenantSignature(String tenantSignature) {
        this.tenantSignature = tenantSignature;
    }
    
    public String getLandlordSignature() {
        return landlordSignature;
    }
    
    public void setLandlordSignature(String landlordSignature) {
        this.landlordSignature = landlordSignature;
    }
    
    public LocalDateTime getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(LocalDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }
    
    public void setTerminationDate(LocalDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }
    
    public String getTerminationReason() {
        return terminationReason;
    }
    
    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


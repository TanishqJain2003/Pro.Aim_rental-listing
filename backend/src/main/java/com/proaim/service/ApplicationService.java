package com.proaim.service;

import com.proaim.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ApplicationService {
    
    // Basic CRUD operations
    Application createApplication(Application application);
    Application updateApplication(Long id, Application application);
    void deleteApplication(Long id);
    Optional<Application> getApplicationById(Long id);
    List<Application> getAllApplications();
    Page<Application> getAllApplications(Pageable pageable);
    
    // Application management by tenant
    List<Application> getApplicationsByTenant(Long tenantId);
    Page<Application> getApplicationsByTenant(Long tenantId, Pageable pageable);
    Long countApplicationsByTenant(Long tenantId);
    
    // Application management by landlord
    List<Application> getApplicationsByLandlord(Long landlordId);
    Page<Application> getApplicationsByLandlord(Long landlordId, Pageable pageable);
    Long countApplicationsByLandlord(Long landlordId);
    
    // Application management by property
    List<Application> getApplicationsByProperty(Long propertyId);
    Page<Application> getApplicationsByProperty(Long propertyId, Pageable pageable);
    Long countApplicationsByProperty(Long propertyId);
    
    // Application status management
    List<Application> getApplicationsByStatus(Application.ApplicationStatus status);
    Application updateApplicationStatus(Long id, Application.ApplicationStatus status);
    Application reviewApplication(Long id, Application.ApplicationStatus status, String rejectionReason, Long reviewedBy);
    
    // Pending applications
    List<Application> getPendingApplications();
    List<Application> getPendingApplicationsByLandlord(Long landlordId);
    Long countPendingApplicationsByLandlord(Long landlordId);
    
    // Applications needing review
    List<Application> getApplicationsNeedingReview();
    
    // Application search and filtering
    List<Application> getApplicationsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Application> getApplicationsByMoveInDate(LocalDateTime moveInDate);
    List<Application> getApplicationsByLeaseTerm(Integer leaseTermMonths);
    
    // Financial criteria search
    List<Application> getApplicationsByIncomeRange(BigDecimal minIncome, BigDecimal maxIncome);
    List<Application> getApplicationsByCreditScoreRange(Integer minScore, Integer maxScore);
    
    // Pet and occupant search
    List<Application> getApplicationsWithPets(Integer minPetsCount);
    List<Application> getApplicationsByOccupantsCount(Integer occupantsCount);
    
    // Employment search
    List<Application> getApplicationsByEmploymentStatus(String employmentStatus);
    List<Application> getApplicationsByEmployer(String employerName);
    
    // Fee management
    List<Application> getApplicationsByFeeStatus(Boolean feePaid);
    Application updateFeeStatus(Long id, Boolean feePaid);
    
    // Review management
    List<Application> getApplicationsByReviewDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Application> getApplicationsByReviewer(Long reviewedBy);
    
    // Overdue applications
    List<Application> getOverdueApplications();
    
    // Application analytics
    Long getTotalApplicationsCount();
    Long getPendingApplicationsCount();
    Long getApprovedApplicationsCount();
    Long getRejectedApplicationsCount();
    
    // Application by listing
    List<Application> getApplicationsByListing(Long listingId);
    
    // Application validation
    boolean validateApplication(Application application);
    
    // Application processing
    void processApplication(Long id);
    void sendApplicationNotification(Long id);
}

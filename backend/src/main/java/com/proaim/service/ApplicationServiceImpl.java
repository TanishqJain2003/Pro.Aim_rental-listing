package com.proaim.service;

import com.proaim.entity.Application;
import com.proaim.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    // Basic CRUD operations
    @Override
    public Application createApplication(Application application) {
        application.setCreatedAt(LocalDateTime.now());
        return applicationRepository.save(application);
    }

    @Override
    public Application updateApplication(Long id, Application application) {
        Optional<Application> existingApp = applicationRepository.findById(id);
        if (existingApp.isPresent()) {
            Application existing = existingApp.get();
            existing.setUpdatedAt(LocalDateTime.now());
            // Update fields as needed
            return applicationRepository.save(existing);
        }
        throw new RuntimeException("Application not found with id: " + id);
    }

    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Page<Application> getAllApplications(Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }

    // Application management by tenant
    @Override
    public List<Application> getApplicationsByTenant(Long tenantId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return empty list - this should be implemented with proper User lookup
        return List.of();
    }

    @Override
    public Page<Application> getApplicationsByTenant(Long tenantId, Pageable pageable) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return empty page - this should be implemented with proper User lookup
        return Page.empty(pageable);
    }

    @Override
    public Long countApplicationsByTenant(Long tenantId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return 0 - this should be implemented with proper User lookup
        return 0L;
    }

    // Application management by landlord
    @Override
    public List<Application> getApplicationsByLandlord(Long landlordId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return empty list - this should be implemented with proper User lookup
        return List.of();
    }

    @Override
    public Page<Application> getApplicationsByLandlord(Long landlordId, Pageable pageable) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return empty page - this should be implemented with proper User lookup
        return Page.empty(pageable);
    }

    @Override
    public Long countApplicationsByLandlord(Long landlordId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return 0 - this should be implemented with proper User lookup
        return 0L;
    }

    // Application management by property
    @Override
    public List<Application> getApplicationsByProperty(Long propertyId) {
        // This would need a Property object, so we'll need to create a custom query
        // For now, return empty list - this should be implemented with proper Property lookup
        return List.of();
    }

    @Override
    public Page<Application> getApplicationsByProperty(Long propertyId, Pageable pageable) {
        // This would need a Property object, so we'll need to create a custom query
        // For now, return empty page - this should be implemented with proper Property lookup
        return Page.empty(pageable);
    }

    @Override
    public Long countApplicationsByProperty(Long propertyId) {
        // This would need a Property object, so we'll need to create a custom query
        // For now, return 0 - this should be implemented with proper Property lookup
        return 0L;
    }

    // Application status management
    @Override
    public List<Application> getApplicationsByStatus(Application.ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    @Override
    public Application updateApplicationStatus(Long id, Application.ApplicationStatus status) {
        Optional<Application> existingApp = applicationRepository.findById(id);
        if (existingApp.isPresent()) {
            Application existing = existingApp.get();
            existing.setStatus(status);
            existing.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(existing);
        }
        throw new RuntimeException("Application not found with id: " + id);
    }

    @Override
    public Application reviewApplication(Long id, Application.ApplicationStatus status, String rejectionReason, Long reviewedBy) {
        Optional<Application> existingApp = applicationRepository.findById(id);
        if (existingApp.isPresent()) {
            Application existing = existingApp.get();
            existing.setStatus(status);
            existing.setRejectionReason(rejectionReason);
            existing.setReviewedBy(reviewedBy);
            existing.setReviewedAt(LocalDateTime.now());
            existing.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(existing);
        }
        throw new RuntimeException("Application not found with id: " + id);
    }

    // Pending applications
    @Override
    public List<Application> getPendingApplications() {
        return applicationRepository.findByStatus(Application.ApplicationStatus.PENDING);
    }

    @Override
    public List<Application> getPendingApplicationsByLandlord(Long landlordId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return empty list - this should be implemented with proper User lookup
        return List.of();
    }

    @Override
    public Long countPendingApplicationsByLandlord(Long landlordId) {
        // This would need a User object, so we'll need to create a custom query
        // For now, return 0 - this should be implemented with proper User lookup
        return 0L;
    }

    // Applications needing review
    @Override
    public List<Application> getApplicationsNeedingReview() {
        return applicationRepository.findApplicationsNeedingReview();
    }

    // Application search and filtering
    @Override
    public List<Application> getApplicationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationRepository.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public List<Application> getApplicationsByMoveInDate(LocalDateTime moveInDate) {
        return applicationRepository.findByMoveInDate(moveInDate);
    }

    @Override
    public List<Application> getApplicationsByLeaseTerm(Integer leaseTermMonths) {
        return applicationRepository.findByLeaseTermPreference(leaseTermMonths);
    }

    // Financial criteria search
    @Override
    public List<Application> getApplicationsByIncomeRange(BigDecimal minIncome, BigDecimal maxIncome) {
        return applicationRepository.findByMonthlyIncomeRange(minIncome, maxIncome);
    }

    @Override
    public List<Application> getApplicationsByCreditScoreRange(Integer minScore, Integer maxScore) {
        return applicationRepository.findByCreditScoreRange(minScore, maxScore);
    }

    // Pet and occupant search
    @Override
    public List<Application> getApplicationsWithPets(Integer minPetsCount) {
        return applicationRepository.findByPetsCountGreaterThan(minPetsCount);
    }

    @Override
    public List<Application> getApplicationsByOccupantsCount(Integer occupantsCount) {
        // This method doesn't exist in the repository, so we'll filter manually
        return applicationRepository.findAll().stream()
            .filter(app -> app.getOccupantsCount().equals(occupantsCount))
            .toList();
    }

    // Employment search
    @Override
    public List<Application> getApplicationsByEmploymentStatus(String employmentStatus) {
        return applicationRepository.findByEmploymentStatus(employmentStatus);
    }

    @Override
    public List<Application> getApplicationsByEmployer(String employerName) {
        return applicationRepository.findByEmployerName(employerName);
    }

    // Fee management
    @Override
    public List<Application> getApplicationsByFeeStatus(Boolean feePaid) {
        return applicationRepository.findByFeePaid(feePaid);
    }

    @Override
    public Application updateFeeStatus(Long id, Boolean feePaid) {
        Optional<Application> existingApp = applicationRepository.findById(id);
        if (existingApp.isPresent()) {
            Application existing = existingApp.get();
            existing.setFeePaid(feePaid);
            existing.setUpdatedAt(LocalDateTime.now());
            return applicationRepository.save(existing);
        }
        throw new RuntimeException("Application not found with id: " + id);
    }

    // Review management
    @Override
    public List<Application> getApplicationsByReviewDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationRepository.findByReviewDateRange(startDate, endDate);
    }

    @Override
    public List<Application> getApplicationsByReviewer(Long reviewedBy) {
        return applicationRepository.findByReviewedBy(reviewedBy);
    }

    // Overdue applications
    @Override
    public List<Application> getOverdueApplications() {
        // Use the existing repository method with a cutoff date
        return applicationRepository.findOverdueApplications(LocalDateTime.now().minusDays(7));
    }

    // Application analytics
    @Override
    public Long getTotalApplicationsCount() {
        return applicationRepository.count();
    }

    @Override
    public Long getPendingApplicationsCount() {
        return applicationRepository.countByStatus(Application.ApplicationStatus.PENDING);
    }

    @Override
    public Long getApprovedApplicationsCount() {
        return applicationRepository.countByStatus(Application.ApplicationStatus.APPROVED);
    }

    @Override
    public Long getRejectedApplicationsCount() {
        return applicationRepository.countByStatus(Application.ApplicationStatus.REJECTED);
    }

    // Application by listing
    @Override
    public List<Application> getApplicationsByListing(Long listingId) {
        // This method doesn't exist in the repository, so we'll filter manually
        return applicationRepository.findAll().stream()
            .filter(app -> app.getListing() != null && app.getListing().getId().equals(listingId))
            .toList();
    }

    // Application validation
    @Override
    public boolean validateApplication(Application application) {
        // Basic validation logic
        return application.getTenant() != null &&
               application.getProperty() != null &&
               application.getMonthlyIncome() != null &&
               application.getMonthlyIncome().compareTo(BigDecimal.ZERO) > 0 &&
               application.getCreditScore() != null &&
               application.getCreditScore() >= 300 &&
               application.getCreditScore() <= 850;
    }

    // Application processing
    @Override
    public void processApplication(Long id) {
        // Process application logic
        Optional<Application> app = applicationRepository.findById(id);
        if (app.isPresent()) {
            Application application = app.get();
            // Add processing logic here
            application.setUpdatedAt(LocalDateTime.now());
            applicationRepository.save(application);
        }
    }

    @Override
    public void sendApplicationNotification(Long id) {
        // Send notification logic
        Optional<Application> app = applicationRepository.findById(id);
        if (app.isPresent()) {
            // Add notification logic here
            // This could send emails, SMS, etc.
        }
    }
}

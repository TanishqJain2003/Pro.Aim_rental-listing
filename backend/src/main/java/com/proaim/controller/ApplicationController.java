package com.proaim.controller;

import com.proaim.entity.Application;
import com.proaim.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Public endpoints
    @GetMapping
    public ResponseEntity<Page<Application>> getAllApplications(Pageable pageable) {
        return ResponseEntity.ok(applicationService.getAllApplications(pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Application>> getAllApplicationsList() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Application>> getPendingApplications() {
        return ResponseEntity.ok(applicationService.getPendingApplications());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Application>> searchApplications(@RequestParam String keyword) {
        // This could search by tenant name, property address, etc.
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    // Protected endpoints - require authentication
    @GetMapping("/tenant/{tenantId}")
    @PreAuthorize("hasRole('TENANT') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(applicationService.getApplicationsByTenant(tenantId));
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<Application>> getApplicationsByProperty(@PathVariable Long propertyId) {
        return ResponseEntity.ok(applicationService.getApplicationsByProperty(propertyId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable Application.ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Application>> getApplicationsByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(applicationService.getApplicationsByDateRange(startDate, endDate));
    }

    @GetMapping("/move-in-date")
    public ResponseEntity<List<Application>> getApplicationsByMoveInDate(
            @RequestParam LocalDateTime moveInDate) {
        return ResponseEntity.ok(applicationService.getApplicationsByMoveInDate(moveInDate));
    }

    @GetMapping("/lease-term/{leaseTerm}")
    public ResponseEntity<List<Application>> getApplicationsByLeaseTerm(@PathVariable Integer leaseTerm) {
        return ResponseEntity.ok(applicationService.getApplicationsByLeaseTerm(leaseTerm));
    }

    @GetMapping("/income-range")
    public ResponseEntity<List<Application>> getApplicationsByIncomeRange(
            @RequestParam BigDecimal minIncome,
            @RequestParam BigDecimal maxIncome) {
        return ResponseEntity.ok(applicationService.getApplicationsByIncomeRange(minIncome, maxIncome));
    }

    @GetMapping("/credit-score-range")
    public ResponseEntity<List<Application>> getApplicationsByCreditScoreRange(
            @RequestParam Integer minScore,
            @RequestParam Integer maxScore) {
        return ResponseEntity.ok(applicationService.getApplicationsByCreditScoreRange(minScore, maxScore));
    }

    @GetMapping("/with-pets")
    public ResponseEntity<List<Application>> getApplicationsWithPets(@RequestParam(defaultValue = "1") Integer minPetsCount) {
        return ResponseEntity.ok(applicationService.getApplicationsWithPets(minPetsCount));
    }

    @GetMapping("/employment-status/{employmentStatus}")
    public ResponseEntity<List<Application>> getApplicationsByEmploymentStatus(@PathVariable String employmentStatus) {
        return ResponseEntity.ok(applicationService.getApplicationsByEmploymentStatus(employmentStatus));
    }

    @GetMapping("/employer/{employerName}")
    public ResponseEntity<List<Application>> getApplicationsByEmployer(@PathVariable String employerName) {
        return ResponseEntity.ok(applicationService.getApplicationsByEmployer(employerName));
    }

    // Landlord and Admin only endpoints
    @GetMapping("/landlord/{landlordId}")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(applicationService.getApplicationsByLandlord(landlordId));
    }

    @GetMapping("/needing-review")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsNeedingReview() {
        return ResponseEntity.ok(applicationService.getApplicationsNeedingReview());
    }

    @GetMapping("/needing-review/landlord/{landlordId}")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsNeedingReviewByLandlord(@PathVariable Long landlordId) {
        return ResponseEntity.ok(applicationService.getPendingApplicationsByLandlord(landlordId));
    }

    @GetMapping("/fee-status/{feePaid}")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsByFeePaymentStatus(@PathVariable boolean feePaid) {
        return ResponseEntity.ok(applicationService.getApplicationsByFeeStatus(feePaid));
    }

    @GetMapping("/review-date-range")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsByReviewDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(applicationService.getApplicationsByReviewDateRange(startDate, endDate));
    }

    @GetMapping("/reviewer/{reviewerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getApplicationsByReviewer(@PathVariable Long reviewerId) {
        return ResponseEntity.ok(applicationService.getApplicationsByReviewer(reviewerId));
    }

    @GetMapping("/overdue")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<List<Application>> getOverdueApplications() {
        return ResponseEntity.ok(applicationService.getOverdueApplications());
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Application>> getApplicationsByListing(@PathVariable Long listingId) {
        return ResponseEntity.ok(applicationService.getApplicationsByListing(listingId));
    }

    // Tenant only endpoints
    @PostMapping
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.createApplication(application));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        return ResponseEntity.ok(applicationService.updateApplication(id, applicationDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    // Landlord and Admin only endpoints
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam Application.ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, status));
    }

    @PatchMapping("/{id}/review")
    @PreAuthorize("hasRole('LANDLORD') or hasRole('ADMIN')")
    public ResponseEntity<Application> reviewApplication(
            @PathVariable Long id,
            @RequestParam Application.ApplicationStatus status,
            @RequestParam(required = false) String rejectionReason,
            @RequestParam Long reviewedBy) {
        return ResponseEntity.ok(applicationService.reviewApplication(id, status, rejectionReason, reviewedBy));
    }

    @PatchMapping("/{id}/fee-paid")
    @PreAuthorize("hasRole('TENANT') or hasRole('ADMIN')")
    public ResponseEntity<Application> markApplicationFeeAsPaid(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.updateFeeStatus(id, true));
    }

    // Admin only endpoints
    @GetMapping("/analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationAnalytics> getApplicationAnalytics() {
        ApplicationAnalytics analytics = new ApplicationAnalytics();
        analytics.setTotalApplications(applicationService.getTotalApplicationsCount());
        analytics.setPendingApplications(applicationService.getPendingApplicationsCount());
        analytics.setOverdueApplications((long) applicationService.getOverdueApplications().size());
        analytics.setApplicationsWithPets((long) applicationService.getApplicationsWithPets(1).size());
        return ResponseEntity.ok(analytics);
    }

    // Inner class for analytics
    public static class ApplicationAnalytics {
        private Long totalApplications;
        private Long pendingApplications;
        private Long overdueApplications;
        private Long applicationsWithPets;

        // Getters and Setters
        public Long getTotalApplications() { return totalApplications; }
        public void setTotalApplications(Long totalApplications) { this.totalApplications = totalApplications; }

        public Long getPendingApplications() { return pendingApplications; }
        public void setPendingApplications(Long pendingApplications) { this.pendingApplications = pendingApplications; }

        public Long getOverdueApplications() { return overdueApplications; }
        public void setOverdueApplications(Long overdueApplications) { this.overdueApplications = overdueApplications; }

        public Long getApplicationsWithPets() { return applicationsWithPets; }
        public void setApplicationsWithPets(Long applicationsWithPets) { this.applicationsWithPets = applicationsWithPets; }
    }
}

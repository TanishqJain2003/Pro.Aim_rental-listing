package com.proaim.repository;

import com.proaim.entity.Application;
import com.proaim.entity.Property;
import com.proaim.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    // Find applications by tenant
    List<Application> findByTenant(User tenant);
    
    // Find applications by property
    List<Application> findByProperty(Property property);
    
    // Find applications by status
    List<Application> findByStatus(Application.ApplicationStatus status);
    
    // Find applications by landlord (through property)
    @Query("SELECT a FROM Application a JOIN a.property p WHERE p.landlord = :landlord")
    List<Application> findByLandlord(@Param("landlord") User landlord);
    
    // Find pending applications for a landlord
    @Query("SELECT a FROM Application a JOIN a.property p WHERE p.landlord = :landlord AND a.status = 'PENDING'")
    List<Application> findPendingApplicationsByLandlord(@Param("landlord") User landlord);
    
    // Find applications by tenant and status
    List<Application> findByTenantAndStatus(User tenant, Application.ApplicationStatus status);
    
    // Find applications by property and status
    List<Application> findByPropertyAndStatus(Property property, Application.ApplicationStatus status);
    
    // Find applications created within date range
    List<Application> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find applications by move-in date
    List<Application> findByMoveInDate(LocalDateTime moveInDate);
    
    // Find applications by lease term preference
    List<Application> findByLeaseTermPreference(Integer leaseTermMonths);
    
    // Find applications by monthly income range
    @Query("SELECT a FROM Application a WHERE a.monthlyIncome BETWEEN :minIncome AND :maxIncome")
    List<Application> findByMonthlyIncomeRange(
            @Param("minIncome") java.math.BigDecimal minIncome,
            @Param("maxIncome") java.math.BigDecimal maxIncome
    );
    
    // Find applications by credit score range
    @Query("SELECT a FROM Application a WHERE a.creditScore BETWEEN :minScore AND :maxScore")
    List<Application> findByCreditScoreRange(
            @Param("minScore") Integer minScore,
            @Param("maxScore") Integer maxScore
    );
    
    // Find applications with pets
    List<Application> findByPetsCountGreaterThan(Integer petsCount);
    
    // Find applications by employment status
    List<Application> findByEmploymentStatus(String employmentStatus);
    
    // Find applications by employer
    List<Application> findByEmployerName(String employerName);
    
    // Find applications that need review
    @Query("SELECT a FROM Application a WHERE a.status = 'PENDING' OR a.status = 'UNDER_REVIEW'")
    List<Application> findApplicationsNeedingReview();
    
    // Find applications by fee payment status
    List<Application> findByFeePaid(Boolean feePaid);
    
    // Find applications by review date
    @Query("SELECT a FROM Application a WHERE a.reviewedAt BETWEEN :startDate AND :endDate")
    List<Application> findByReviewDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    // Find applications by reviewer
    List<Application> findByReviewedBy(Long reviewedBy);
    
    // Count applications by status
    Long countByStatus(Application.ApplicationStatus status);
    
    // Count applications by tenant
    Long countByTenant(User tenant);
    
    // Count applications by property
    Long countByProperty(Property property);
    
    // Count pending applications for a landlord
    @Query("SELECT COUNT(a) FROM Application a JOIN a.property p WHERE p.landlord = :landlord AND a.status = 'PENDING'")
    Long countPendingApplicationsByLandlord(@Param("landlord") User landlord);
    
    // Find applications with pagination
    Page<Application> findByStatus(Application.ApplicationStatus status, Pageable pageable);
    
    // Find applications by tenant with pagination
    Page<Application> findByTenant(User tenant, Pageable pageable);
    
    // Find applications by landlord with pagination
    @Query("SELECT a FROM Application a JOIN a.property p WHERE p.landlord = :landlord")
    Page<Application> findByLandlord(@Param("landlord") User landlord, Pageable pageable);
    
    // Find applications by property with pagination
    Page<Application> findByProperty(Property property, Pageable pageable);
    
    // Find applications that are overdue for review
    @Query("SELECT a FROM Application a WHERE a.status = 'PENDING' AND a.createdAt < :cutoffDate")
    List<Application> findOverdueApplications(@Param("cutoffDate") LocalDateTime cutoffDate);
}


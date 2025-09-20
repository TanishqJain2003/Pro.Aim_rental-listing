package com.proaim.repository;

import com.proaim.entity.Agreement;
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
public interface AgreementRepository extends JpaRepository<Agreement, Long> {
    
    // Find agreements by tenant
    List<Agreement> findByTenant(User tenant);
    
    // Find agreements by landlord
    List<Agreement> findByLandlord(User landlord);
    
    // Find agreements by property
    List<Agreement> findByProperty(Property property);
    
    // Find agreements by status
    List<Agreement> findByStatus(Agreement.AgreementStatus status);
    
    // Find agreements by tenant and status
    List<Agreement> findByTenantAndStatus(User tenant, Agreement.AgreementStatus status);
    
    // Find agreements by landlord and status
    List<Agreement> findByLandlordAndStatus(User landlord, Agreement.AgreementStatus status);
    
    // Find active agreements
    @Query("SELECT a FROM Agreement a WHERE a.status = 'ACTIVE'")
    List<Agreement> findActiveAgreements();
    
    // Find agreements by agreement number
    Agreement findByAgreementNumber(String agreementNumber);
    
    // Find agreements by start date range
    List<Agreement> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find agreements by end date range
    List<Agreement> findByEndDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find agreements expiring soon
    @Query("SELECT a FROM Agreement a WHERE a.endDate <= :date AND a.status = 'ACTIVE'")
    List<Agreement> findExpiringSoon(@Param("date") LocalDateTime date);
    
    // Find agreements that need renewal
    @Query("SELECT a FROM Agreement a WHERE a.endDate BETWEEN :startDate AND :endDate AND a.status = 'ACTIVE'")
    List<Agreement> findAgreementsNeedingRenewal(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    // Find agreements by lease term
    List<Agreement> findByLeaseTermMonths(Integer leaseTermMonths);
    
    // Find agreements by payment due day
    List<Agreement> findByPaymentDueDay(Integer paymentDueDay);
    
    // Find agreements by rent amount range
    @Query("SELECT a FROM Agreement a WHERE a.rentAmount BETWEEN :minRent AND :maxRent")
    List<Agreement> findByRentAmountRange(
            @Param("minRent") java.math.BigDecimal minRent,
            @Param("maxRent") java.math.BigDecimal maxRent
    );
    
    // Find agreements by security deposit range
    @Query("SELECT a FROM Agreement a WHERE a.securityDeposit BETWEEN :minDeposit AND :maxDeposit")
    List<Agreement> findBySecurityDepositRange(
            @Param("minDeposit") java.math.BigDecimal minDeposit,
            @Param("maxDeposit") java.math.BigDecimal maxDeposit
    );
    
    // Find agreements by signature status
    List<Agreement> findBySignedByTenantAndSignedByLandlord(Boolean tenantSigned, Boolean landlordSigned);
    
    // Find agreements pending tenant signature
    List<Agreement> findBySignedByTenantFalse();
    
    // Find agreements pending landlord signature
    List<Agreement> findBySignedByLandlordFalse();
    
    // Find agreements by effective date
    List<Agreement> findByEffectiveDate(LocalDateTime effectiveDate);
    
    // Find agreements by termination date
    List<Agreement> findByTerminationDate(LocalDateTime terminationDate);
    
    // Find agreements by application
    Agreement findByApplicationId(Long applicationId);
    
    // Find agreements created within date range
    List<Agreement> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find agreements signed within date range
    @Query("SELECT a FROM Agreement a WHERE a.signedAt BETWEEN :startDate AND :endDate")
    List<Agreement> findBySignedDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    // Count agreements by status
    Long countByStatus(Agreement.AgreementStatus status);
    
    // Count agreements by tenant
    Long countByTenant(User tenant);
    
    // Count agreements by landlord
    Long countByLandlord(User landlord);
    
    // Count agreements by property
    Long countByProperty(Property property);
    
    // Count agreements expiring soon
    @Query("SELECT COUNT(a) FROM Agreement a WHERE a.endDate <= :date AND a.status = 'ACTIVE'")
    Long countExpiringSoon(@Param("date") LocalDateTime date);
    
    // Find agreements with pagination
    Page<Agreement> findByStatus(Agreement.AgreementStatus status, Pageable pageable);
    
    // Find agreements by tenant with pagination
    Page<Agreement> findByTenant(User tenant, Pageable pageable);
    
    // Find agreements by landlord with pagination
    Page<Agreement> findByLandlord(User landlord, Pageable pageable);
    
    // Find agreements by property with pagination
    Page<Agreement> findByProperty(Property property, Pageable pageable);
    
    // Find agreements that are overdue for signature
    @Query("SELECT a FROM Agreement a WHERE a.status = 'PENDING_SIGNATURE' AND a.createdAt < :cutoffDate")
    List<Agreement> findOverdueSignatures(@Param("cutoffDate") LocalDateTime cutoffDate);
    
    // Find agreements by utilities included
    List<Agreement> findByUtilitiesIncluded(Boolean utilitiesIncluded);
    
    // Find agreements by pet policy
    @Query("SELECT a FROM Agreement a WHERE a.petPolicy LIKE %:petPolicy%")
    List<Agreement> findByPetPolicyContaining(@Param("petPolicy") String petPolicy);
    
    // Find agreements by smoking policy
    @Query("SELECT a FROM Agreement a WHERE a.smokingPolicy LIKE %:smokingPolicy%")
    List<Agreement> findBySmokingPolicyContaining(@Param("smokingPolicy") String smokingPolicy);
}

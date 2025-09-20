package com.proaim.repository;

import com.proaim.entity.Payment;
import com.proaim.entity.Property;
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
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payments by tenant
    List<Payment> findByTenant(User tenant);

    // Find payments by landlord
    List<Payment> findByLandlord(User landlord);

    // Find payments by property
    List<Payment> findByProperty(Property property);

    // Find payments by status (this is the correct method to keep)
    List<Payment> findByStatus(Payment.PaymentStatus status);

    // Find payments by type
    List<Payment> findByType(Payment.PaymentType type);

    // Find payments by method
    List<Payment> findByMethod(Payment.PaymentMethod method);

    // Find payments by tenant and status
    List<Payment> findByTenantAndStatus(User tenant, Payment.PaymentStatus status);

    // Find payments by landlord and status
    List<Payment> findByLandlordAndStatus(User landlord, Payment.PaymentStatus status);

    // Find payments by property and status
    List<Payment> findByPropertyAndStatus(Property property, Payment.PaymentStatus status);

    // Find payments by payment reference
    Payment findByPaymentReference(String paymentReference);

    // Find payments by transaction ID
    Payment findByTransactionId(String transactionId);

    // Find payments by amount range
    List<Payment> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);

    // Find payments by payment date range
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find payments by due date range
    List<Payment> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find overdue payments
    @Query("SELECT p FROM Payment p WHERE p.dueDate < :date AND p.status = 'PENDING'")
    List<Payment> findOverduePayments(@Param("date") LocalDateTime date);

    // Find payments due soon
    @Query("SELECT p FROM Payment p WHERE p.dueDate BETWEEN :startDate AND :endDate AND p.status = 'PENDING'")
    List<Payment> findPaymentsDueSoon(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Find payments by card last four digits
    List<Payment> findByCardLastFour(String cardLastFour);

    // Find payments by bank account last four digits
    List<Payment> findByBankAccountLastFour(String bankAccountLastFour);

    // Find payments by description
    @Query("SELECT p FROM Payment p WHERE p.paymentDescription LIKE %:description%")
    List<Payment> findByPaymentDescriptionContaining(@Param("description") String description);

    // Find payments with late fees
    @Query("SELECT p FROM Payment p WHERE p.lateFee > 0")
    List<Payment> findPaymentsWithLateFees();

    // Find payments with processing fees
    @Query("SELECT p FROM Payment p WHERE p.processingFee > 0")
    List<Payment> findPaymentsWithProcessingFees();

    // Find payments by total amount range
    List<Payment> findByTotalAmountBetween(BigDecimal minTotal, BigDecimal maxTotal);

    // Find payments created within date range
    List<Payment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find payments processed within date range
    @Query("SELECT p FROM Payment p WHERE p.processedAt BETWEEN :startDate AND :endDate")
    List<Payment> findByProcessedDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Remove this duplicate method:
    // List<Payment> findByStatus(Payment.PaymentStatus status);  <-- redundant

    // Find payments by retry count
    List<Payment> findByRetryCountGreaterThan(Integer retryCount);

    // Find payments scheduled for retry
    @Query("SELECT p FROM Payment p WHERE p.nextRetryAt <= :date AND p.status = 'FAILED'")
    List<Payment> findPaymentsScheduledForRetry(@Param("date") LocalDateTime date);

    // Count payments by status
    Long countByStatus(Payment.PaymentStatus status);

    // Count payments by tenant
    Long countByTenant(User tenant);

    // Count payments by landlord
    Long countByLandlord(User landlord);

    // Count payments by property
    Long countByProperty(Property property);

    // Count payments by type
    Long countByType(Payment.PaymentType type);

    // Count overdue payments
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.dueDate < :date AND p.status = 'PENDING'")
    Long countOverduePayments(@Param("date") LocalDateTime date);

    // Count payments due soon
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.dueDate BETWEEN :startDate AND :endDate AND p.status = 'PENDING'")
    Long countPaymentsDueSoon(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    // Find payments with pagination
    Page<Payment> findByStatus(Payment.PaymentStatus status, Pageable pageable);

    // Find payments by tenant with pagination
    Page<Payment> findByTenant(User tenant, Pageable pageable);

    // Find payments by landlord with pagination
    Page<Payment> findByLandlord(User landlord, Pageable pageable);

    // Find payments by property with pagination
    Page<Payment> findByProperty(Property property, Pageable pageable);

    // Find payments by type with pagination
    Page<Payment> findByType(Payment.PaymentType type, Pageable pageable);

    // Calculate total amount by status
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") Payment.PaymentStatus status);

    // Calculate total amount by tenant
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.tenant = :tenant")
    BigDecimal sumAmountByTenant(@Param("tenant") User tenant);

    // Calculate total amount by landlord
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.landlord = :landlord")
    BigDecimal sumAmountByLandlord(@Param("landlord") User landlord);

    // Calculate total amount by property
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.property = :property")
    BigDecimal sumAmountByProperty(@Param("property") Property property);

    // Find payments by agreement
    List<Payment> findByAgreementId(Long agreementId);

    // Find rent payments
    @Query("SELECT p FROM Payment p WHERE p.type = 'RENT'")
    List<Payment> findRentPayments();

    // Find security deposit payments
    @Query("SELECT p FROM Payment p WHERE p.type = 'SECURITY_DEPOSIT'")
    List<Payment> findSecurityDepositPayments();

    // Find application fee payments
    @Query("SELECT p FROM Payment p WHERE p.type = 'APPLICATION_FEE'")
    List<Payment> findApplicationFeePayments();
}

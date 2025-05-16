package com.home.mec888.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "invoice_number", nullable = false, unique = true, length = 255)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = false, insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp invoiceDate;

    @Column(name = "total_amount", nullable = false, precision = 10)
    private Double totalAmount;

    @Column(name = "details", columnDefinition = "TEXT")
    private String details;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    public Invoice() {
    }

    public Invoice(Payment payment, String invoiceNumber, Double totalAmount, String details) {
        this.payment = payment;
        this.invoiceNumber = invoiceNumber;
        this.totalAmount = totalAmount;
        this.details = details;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", payment=" + (payment != null ? payment.getId() : "null") +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", totalAmount=" + totalAmount +
                ", details='" + details + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
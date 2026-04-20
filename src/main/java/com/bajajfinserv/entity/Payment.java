package com.bajajfinserv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @Column(name = "PAYMENT_ID")
    private Integer paymentId;

    @Column(name = "EMP_ID")
    private Integer empId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "PAYMENT_TIME")
    private LocalDateTime paymentTime;

}

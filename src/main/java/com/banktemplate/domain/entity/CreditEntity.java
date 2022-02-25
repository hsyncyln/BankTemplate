package com.banktemplate.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "credit_responses")
public class CreditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "identity_number", unique = true)
    private String identityNumber;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "monthly_salary")
    private double monthlySalary;

    @Column(name = "credit_score")
    private int creditScore;

    @Column(name = "credit_status")
    private boolean creditStatus;

    @Column(name = "credit_limit")
    private double creditLimit;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedDate;


 }

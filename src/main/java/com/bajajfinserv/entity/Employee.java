package com.bajajfinserv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "EMPLOYEE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "EMP_ID")
    private Integer empId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DEPARTMENT")
    private Integer department;

}

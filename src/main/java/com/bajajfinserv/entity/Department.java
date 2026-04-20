package com.bajajfinserv.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPARTMENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;

}

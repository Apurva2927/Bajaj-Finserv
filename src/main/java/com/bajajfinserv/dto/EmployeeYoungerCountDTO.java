package com.bajajfinserv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeYoungerCountDTO {

    private Integer empId;

    private String firstName;

    private String lastName;

    private String departmentName;

    private Long youngerEmployeesCount;

}

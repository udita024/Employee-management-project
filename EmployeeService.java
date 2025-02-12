package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // ✅ Convert Entity -> DTO
    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment()
        );
    }

    // ✅ Convert DTO -> Entity
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        return employee;
    }

    // ✅ Get all employees and return DTOs
    public List<EmployeeDTO> getAllEmployees() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get employee by ID and return DTO
    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    // ✅ Save employee (Accepts DTO, Returns DTO)
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee savedEmployee = repository.save(convertToEntity(employeeDTO));
        return convertToDTO(savedEmployee);
    }

    // ✅ Delete employee by ID
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}

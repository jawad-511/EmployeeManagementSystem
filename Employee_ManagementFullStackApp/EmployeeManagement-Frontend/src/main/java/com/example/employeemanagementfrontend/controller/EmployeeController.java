package com.example.employeemanagementfrontend.controller;

import com.example.employeemanagementfrontend.model.Employee;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    
    private final String API_URL = "http://localhost:8080/employees";
    
    @GetMapping
    public String listEmployees(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        Employee[] employees = restTemplate.getForObject(API_URL, Employee[].class);
        model.addAttribute("employees", employees);
        return "employees";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }
    
    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee, 
                            BindingResult result) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(API_URL, employee, Employee.class);
        return "redirect:/employee";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(API_URL + "/{id}", id);
        return "redirect:/employee";
    }
}
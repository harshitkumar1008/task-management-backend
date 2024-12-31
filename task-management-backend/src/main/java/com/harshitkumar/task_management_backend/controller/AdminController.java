package com.harshitkumar.task_management_backend.controller;

import com.harshitkumar.task_management_backend.dto.UpdateUserRequest;
import com.harshitkumar.task_management_backend.dto.UserCreateRequest;
import com.harshitkumar.task_management_backend.entity.User;
import com.harshitkumar.task_management_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String adminDashboard(){
        return "Welcome To Admin Dashboard";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable String id, @RequestBody UpdateUserRequest updateUserRequest){
        return adminService.updateUser(Long.parseLong(id), updateUserRequest);
    }

    @PostMapping("/create-users")
    public String createUser(@RequestBody UserCreateRequest request){
        return adminService.createUser(request);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id){
        return adminService.deleteUser(Long.parseLong(id));
    }

}

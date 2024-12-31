package com.harshitkumar.task_management_backend.service;

import com.harshitkumar.task_management_backend.dto.TokenResponse;
import com.harshitkumar.task_management_backend.dto.UpdateUserRequest;
import com.harshitkumar.task_management_backend.dto.UserCreateRequest;
import com.harshitkumar.task_management_backend.entity.User;
import com.harshitkumar.task_management_backend.repository.TaskRepository;
import com.harshitkumar.task_management_backend.repository.UserRepository;
import com.harshitkumar.task_management_backend.utils.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JWTUtil jwtUtil;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String createUser(UserCreateRequest request){

        User user = new User();

        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getRole().name());

        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        return "User Created Successfully - " + ResponseEntity.ok(new TokenResponse(accessToken, refreshToken).toString());
    }

    public String updateUser(long id, UpdateUserRequest request){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            if(!request.getRole().name().equals(user.get().getRole().name())){
                user.get().setRole(request.getRole());
            }

            if(request.isActive() != user.get().isActive()){
                user.get().setActive(request.isActive());
            }

            userRepository.save(user.get());

            return "User Updated Successfully";
        }else{
            return "User Not Found";
        }
    }

    public String deleteUser(long id){
        try{
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        } catch (Exception e) {
            throw new RuntimeException("User Not Found");
        }
    }


}

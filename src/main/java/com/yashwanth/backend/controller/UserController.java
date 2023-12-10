package com.yashwanth.backend.controller;
import com.yashwanth.backend.model.User;
import com.yashwanth.backend.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yashwanth.backend.exception.UserNotFoundException;
import java.util.List;

@RestController
@CrossOrigin("https://aptiglide.netlify.app")
public class UserController {
    @Autowired
    private Userrepo userrepo;
    @PostMapping("/user")
    User newUser(@RequestBody User newUser){
        return userrepo.save(newUser);
    }
    @GetMapping("/users")
    List<User>getAllUsers(){
        return userrepo.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userrepo.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }
    @PostMapping("/")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        User user = userrepo.findByEmail(loginRequest.getEmail());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful"+user.getFname());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

package spring.user.demo.springuserdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.user.demo.springuserdemo.entity.User;
import spring.user.demo.springuserdemo.exception.ResourceNotFoundException;
import spring.user.demo.springuserdemo.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id")Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found::" +userId));
        return ResponseEntity.ok().body(user);

    }
    @PostMapping("users")
    public User createUser(@Validated @RequestBody User user ){
        return userRepository.save(user);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value="id") Long userId, @Validated @RequestBody User userUpdate) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: "+ userId));
        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setEmailId(userUpdate.getEmailId());
        user.setUpdateAt(String.valueOf(new Date()));
        final User updateUser = userRepository.save(user);
        return ResponseEntity.ok(updateUser);
    }
    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found:: "+userId));
        userRepository.delete(user);
        Map<String, Boolean> resp = new HashMap<>();
        resp.put("deleted user id = "+userId, Boolean.TRUE);
        return  resp;
    }
}

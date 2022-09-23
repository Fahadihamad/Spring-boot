package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;

@RestController
@RequestMapping("/api/v2/")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
 
@Autowired
public UserRepository userrepo;

@GetMapping("/user")
public List<User> getAllUser(){
	return userrepo.findAll();
}
@PostMapping("/user")
public User addUser(@RequestBody User user) {
	return userrepo.save(user);
}
@GetMapping("/user/{id}")
public ResponseEntity<User> getUserById(@PathVariable Integer id){
	
	User user = this.userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID not found"));
	 return ResponseEntity.ok(user);
}
@PutMapping("/user/{id}")
public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user){
	
	User users = userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID not found"));
	
users.setFirstName(user.getFirstName());
users.setLastName(user.getLastName());
users.setEmail(user.getEmail());
users.setDate_of_birth(user.getDate_of_birth());
users.setNational_ID(user.getNational_ID());
users.setPassword(user.getPassword());
User updateUser = userrepo.save(users);
return ResponseEntity.ok(updateUser);
}
@DeleteMapping("/user/{id}")
public ResponseEntity< Map<String,Boolean>> deleteUser(@PathVariable Integer id){
	User users = userrepo.findById(id).orElseThrow(()->new ResourceNotFoundException("ID not found"));
	  
	userrepo.delete(users);
	Map<String,Boolean> response= new HashMap<>();
	response.put("deleted", Boolean.TRUE);
	
	return ResponseEntity.ok(response);
	
}
}
package com.tts.UsersAPI.User;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserRepository repository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers(@RequestParam(value="state", required=false) String state){
	List<User> users;
	if (state != null) 
	{
		users = repository.findByState(state);
	}
	else {
		users = (List<User>) repository.findAll();
	}
	 return new ResponseEntity<>(users, HttpStatus.OK);	
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUserById(@PathVariable(value="id") Long id){
		Optional<User> user = repository.findById(id);
		if(!user.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(user, HttpStatus.OK);	
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult){
		 if (bindingResult.hasErrors()) {
		        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }
		    repository.save(user);
		    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable(value="id") Long id, @RequestBody User user, BindingResult bindingResult){
		Optional<User> userToUpdate = repository.findById(id);
		if(!userToUpdate.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			repository.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(value="id") Long id){
		Optional<User> userToDelete = repository.findById(id);
		if(!userToDelete.isPresent())
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
	
	}



}

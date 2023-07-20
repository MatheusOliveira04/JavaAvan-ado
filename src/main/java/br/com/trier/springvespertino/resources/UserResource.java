package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.models.dto.UserDTO;
import br.com.trier.springvespertino.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserServiceImpl service;
	

	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/starting/{name}")
	public ResponseEntity<List<UserDTO>> findByNameStartingIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findBynameStartingWithIgnoreCase(name)
				.stream().map((user) -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<UserDTO> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).get().toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping({"/email/{email}"})
	public ResponseEntity<UserDTO> findByEmail(@PathVariable String email){
		return ResponseEntity.ok(service.findByEmail(email).get().toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		return ResponseEntity.ok(service.insert(new User(user)).toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDto){
		User user = new User(userDto);
		user.setId(id);
		return ResponseEntity.ok(service.update(user).toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}

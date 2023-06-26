package br.com.trier.springvespertino.services;

import java.util.List;
import java.util.Optional;

import br.com.trier.springvespertino.models.User;

public interface UserService {

	User findById(Integer id);
	
	User insert(User user);
	
	List<User> listAll();
	
	User update(User user);
	
	void delete (Integer id);
	
	List<User> findBynameStartingWithIgnoreCase(String name);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByName(String name);
}

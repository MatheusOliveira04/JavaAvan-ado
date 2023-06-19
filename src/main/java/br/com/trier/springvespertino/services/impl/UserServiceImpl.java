package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.repositories.UserRepository;
import br.com.trier.springvespertino.services.UserService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	
	private void findByEmail(User user) {
		User busca = repository.findByEmail(user.getEmail());
		if(busca != null && busca.getId() != user.getId()) {
			throw new IntegrityViolation("Email já existe %s".formatted(user.getEmail()));
		}
	}
	
	@Override
	public User findById(Integer id) {
		Optional<User> user = repository.findById(id);
		return user.orElseThrow(() -> new ObjectNotFound("O usuário %s não existe".formatted(id)));
	}

	@Override
	public User insert(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		List<User> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum usuário cadastrado");
		}
		return list;
	}

	@Override
	public User update(User user) {
		findById(user.getId());
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
			repository.delete(user);
		}
		

	@Override
	public List<User> findBynameStartingWithIgnoreCase(String name) {
		List<User> list = repository.findBynameStartingWithIgnoreCase(name);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum nome de usúario contém %s".formatted(name));
		}
		return list;
	}

}

package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
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
		Optional<User> busca = repository.findByEmail(user.getEmail());
		if(busca.isPresent() && busca.get().getId() != user.getId()) {
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
		List<User> list = repository.findByNameStartingWithIgnoreCase(name);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum nome de usúario contém %s".formatted(name));
		}
		return list;
	}

	@Override
	public Optional<User> findByEmail(String email) {
		Optional<User> user = repository.findByEmail(email);
		if(user.isPresent()) {
			return user;
		}
		throw new ObjectNotFound("Nenhum email: %s encontrado no usuario".formatted(email));
	}

	@Override
	public Optional<User> findByName(String name) {
		Optional<User> user = repository.findByName(name);
		if(user.isPresent()) {
			return user;
		}
		throw new ObjectNotFound("Nenhum nome: %s encontrado no usuario".formatted(name));
	}

}

package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.repositories.CountryRepository;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class CountryServiceImpl implements CountryService{

	@Autowired
	private CountryRepository repository;
	
	private void nameIsUnique(Country country) {
		Country busca = repository.findByNameIgnoreCase(country.getName());
		if(busca != null && busca.getId() != country.getId()) {
			throw new IntegrityViolation("Nome %s já existe no país".formatted(country.getName()));
		}
	}
	
	@Override
	public Country findById(Integer id) {
		Optional<Country> country = repository.findById(id);
		return country.orElseThrow(() -> new ObjectNotFound("id: %s do país não encontrado".formatted(id)));
	}

	@Override
	public List<Country> findAll() {
		List<Country> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum país encontrado");
		}
		return list;
	}

	@Override
	public Country insert(Country country) {
		nameIsUnique(country);
		return repository.save(country);
	}

	@Override
	public Country update(Country country) {
		findById(country.getId());
		nameIsUnique(country);
		return repository.save(country);
	}

	@Override
	public void delete(Integer id) {
		Country c = findById(id);
		repository.delete(c);
		}
	

	@Override
	public List<Country> findByNameContainingIgnoreCase(String contains) {
		List<Country> list = repository.findByNameContainingIgnoreCase(contains);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum país encontrado que contém %s".formatted(contains));
		}
		return list;
	}

	
}

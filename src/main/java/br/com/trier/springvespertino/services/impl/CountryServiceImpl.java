package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.repositories.CountryRepository;
import br.com.trier.springvespertino.services.CountryService;

@Service
public class CountryServiceImpl implements CountryService{

	@Autowired
	private CountryRepository repository;
	
	@Override
	public Country findById(Integer id) {
		Optional<Country> country = repository.findById(id);
		return country.orElse(null);
	}

	@Override
	public List<Country> findAll() {
		List<Country> list = repository.findAll();
		return list;
	}

	@Override
	public Country insert(Country country) {
		return repository.save(country);
	}

	@Override
	public Country update(Country country) {
		Country newCountry = repository.save(country);
		return newCountry;
	}

	@Override
	public void delete(Integer id) {
		Country newCountry = findById(id);
		if(newCountry != null) {
		repository.delete(newCountry);
		}
	}

	@Override
	public List<Country> findByNameContainingIgnoreCase(String countains) {
		List<Country> list = repository.findByNameContainingIgnoreCase(countains);
		return list;
	}

	
}

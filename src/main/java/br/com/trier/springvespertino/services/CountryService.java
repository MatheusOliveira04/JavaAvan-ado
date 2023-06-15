package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Country;

public interface CountryService {
	
	Country findById(Integer id);
	
	List<Country> findAll();
	
	Country insert(Country country);
	
	Country update(Country country);
	
	void delete(Integer id);
}

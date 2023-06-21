package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.repositories.PilotRepository;
import br.com.trier.springvespertino.services.PilotService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class PilotServiceImpl implements PilotService{

	@Autowired
	private PilotRepository repository;
	
	private void validateCountryIsNull(Pilot pilot) {
		if(pilot.getCountry() == null) {
			throw new IntegrityViolation("Country está null");
		}
	}
	
	@Override
	public Pilot findById(Integer id) {
		return repository.findById(id).orElseThrow( 
				() -> new ObjectNotFound("pista %s não existe".formatted(id)));
	}

	@Override
	public List<Country> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country insert(Country country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country update(Country country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pilot> findByCountryIgnoreCase(Country country) {
		// TODO Auto-generated method stub
		return null;
	}

}

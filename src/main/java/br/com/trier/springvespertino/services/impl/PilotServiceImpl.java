package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.repositories.PilotRepository;
import br.com.trier.springvespertino.services.PilotService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class PilotServiceImpl implements PilotService {

	@Autowired
	private PilotRepository repository;

	private void validateCountryIsNull(Pilot pilot) {
		if (pilot.getCountry() == null) {
			throw new IntegrityViolation("Country está null");
		}
	}

	@Override
	public Pilot findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("piloto %s não existe".formatted(id)));

	}

	@Override
	public List<Pilot> findAll() {
		List<Pilot> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto cadastrado");
		}
		return list;
	}

	@Override
	public Pilot insert(Pilot pilot) {
		validateCountryIsNull(pilot);
		return repository.save(pilot);
	}

	@Override
	public Pilot update(Pilot pilot) {
		findById(pilot.getId());
		validateCountryIsNull(pilot);
		return repository.save(pilot);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));

	}

	@Override
	public List<Pilot> findByNameContainingIgnoreCaseOrderById(String name) {
		List<Pilot> list = repository.findByNameContainingIgnoreCaseOrderById(name);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto contém %s no nome".formatted(name));
		}
		return list;
	}
}

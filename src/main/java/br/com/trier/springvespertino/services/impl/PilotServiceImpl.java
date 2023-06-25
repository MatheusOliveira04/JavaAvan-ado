package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Team;
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
			throw new IntegrityViolation("País está null");
		}
	}

	@Override
	public Pilot findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s do piloto não encontrado".formatted(id)));

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

	@Override
	public List<Pilot> findByTeam(Team team) {
		List<Pilot> list = repository.findByTeam(team);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma equipe encontrada no piloto");
		}
		return list;
	}

	@Override
	public List<Pilot> findByCountry(Country country) {
		List<Pilot> list = repository.findByCountry(country);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum país encontrado no piloto");
		}
		return list;
	}

	


}

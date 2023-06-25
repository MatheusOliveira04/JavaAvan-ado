package br.com.trier.springvespertino.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.PilotRace;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.repositories.PilotRaceRepository;
import br.com.trier.springvespertino.services.PilotRaceService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class PilotRaceServiceImpl implements PilotRaceService{

	@Autowired
	PilotRaceRepository repository;

	private void pilotIsValid(PilotRace pilotRace) {
		if(pilotRace.getPilot() == null) {
			throw new IntegrityViolation("Piloto está nulo");
		}
	}
	
	private void raceIsValid(PilotRace pilotRace) {
		if(pilotRace.getRace() == null) {
			throw new IntegrityViolation("Corrida está nulo");
		}
	}
	
	@Override
	public List<PilotRace> findAll() {
		List<PilotRace> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum cadastro");
		}
		return list;
	}

	@Override
	public PilotRace findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("id: %s do piloto_corrida não encontrado".formatted(id)));
	}

	@Override
	public PilotRace insert(PilotRace pilotRace) {
		pilotIsValid(pilotRace);
		raceIsValid(pilotRace);
		return repository.save(pilotRace);
	}

	@Override
	public PilotRace update(PilotRace pilotRace) {
		findById(pilotRace.getId());
		pilotIsValid(pilotRace);
		raceIsValid(pilotRace);
		return repository.save(pilotRace);
	}

	@Override
	public void delete(Integer id) {
		PilotRace pilotRace = findById(id);
		repository.delete(pilotRace);
	}

	@Override
	public List<PilotRace> findByPilot(Pilot pilot) {
		List<PilotRace> list = repository.findByPilot(pilot);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum piloto encontrado no piloto_corrida");
		}
		return list;
	}

	@Override
	public List<PilotRace> findByRace(Race race) {
		List<PilotRace> list = repository.findByRace(race);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida encontrada no piloto_corrida");
		}
		return list;
	}
}

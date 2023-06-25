package br.com.trier.springvespertino.services.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.repositories.RaceRespository;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class RaceServiceImpl implements RaceService {

	@Autowired
	RaceRespository repository;

	private void dateIsValid(Race race) {
		if (race.getDate() == null) {
			throw new IntegrityViolation("Data está vazia");
		}
		if (race.getDate().getYear() != race.getChampionship().getYear()) {
			throw new IntegrityViolation("Ano data de corrida é diferente de ano do campeonato");
		}
	}

	@Override
	public List<Race> findAll() {
		List<Race> list = repository.findAll();
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhum corrida encontrado");
		}
		return list;
	}

	@Override
	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(
				() -> new ObjectNotFound("Id: %s da corrida não encontrado".formatted(id)));
	}

	@Override
	public Race insert(Race race) {
		dateIsValid(race);
		return repository.save(race);
	}

	@Override
	public Race update(Race race) {
		findById(race.getId());
		dateIsValid(race);
		return repository.save(race);
	}

	@Override
	public void delete(Integer id) {
		Race race = findById(id);
		repository.delete(race);

	}

	@Override
	public List<Race> findByDateAfter(ZonedDateTime date) {
		List<Race> list = repository.findByDateAfter(date);
		if (list.isEmpty()) {
			throw new ObjectNotFound("Nenhuma corrida encontrada com esta data: %s".formatted(date));
		}
		return list;
	}

	@Override
	public List<Race> findBySpeedwayOrderById(Speedway speedway) {
		List<Race> list = repository.findBySpeedwayOrderById(speedway);
		if(list.isEmpty()) {
			throw new ObjectNotFound("pista %s não encontrado na corrida".formatted(speedway.getId()));
		}
		return list;
	}

	@Override
	public List<Race> findByChampionshipOrderById(Championship championship) {
		List<Race> list = repository.findByChampionshipOrderById(championship);
		if(list.isEmpty()) {
			throw new ObjectNotFound("campeonato %s não encontrado na corrida".formatted(championship.getId()));
		}
		return list;
	}

	
	
}

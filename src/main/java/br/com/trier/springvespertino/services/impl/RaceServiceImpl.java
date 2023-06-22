package br.com.trier.springvespertino.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Race;
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
			throw new ObjectNotFound("Nenhum race encontrado");
		}
		return list;
	}

	@Override
	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Race %s não encontrado".formatted(id)));
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
			throw new ObjectNotFound("Nenhum race encontrado com esta data: %s".formatted(date));
		}
		return list;
	}
}

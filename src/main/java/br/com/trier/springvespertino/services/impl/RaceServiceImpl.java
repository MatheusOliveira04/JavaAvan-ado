package br.com.trier.springvespertino.services.impl;

import java.time.LocalDate;
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
public class RaceServiceImpl implements RaceService{

	@Autowired
	RaceRespository repository;

	private void dateIsValid(Race race) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z");
		if((race.getDate().getYear()+1) > LocalDate.now().plusYears(1).getYear() 
				|| race.getDate() == null 
				|| race.getDate().isBefore(ZonedDateTime.of(
						LocalDateTime.of(1990, 1, 1, 0, 0, 0), 
						ZoneId.of("America/Sao_Paulo")))){
			throw new IntegrityViolation("Date %s inválido".
					formatted(race.getDate().format(dtf)));
		}
	}
	
	@Override
	public List<Race> findAll() {
		List<Race> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum race encontrado");
		}
		return list;
	}

	@Override
	public Race findById(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFound("Race %s não encontrado".formatted(id)));
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
}

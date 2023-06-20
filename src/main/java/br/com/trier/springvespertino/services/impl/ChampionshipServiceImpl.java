package br.com.trier.springvespertino.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.repositories.ChampionshipRepository;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class ChampionshipServiceImpl implements ChampionshipService{

	@Autowired
	ChampionshipRepository repository;
	
	private void yearIsValid(Championship championship) {
		int year = LocalDate.now().getYear() + 1;
		if(championship.getYear() == null) {
			throw new IntegrityViolation("Ano não pode ser nulo");
		}		
		if(!(championship.getYear() >= 1990 && championship.getYear() <= year)) {
			throw new IntegrityViolation("Ano inválido, deve ser entre 1990 e %s".formatted(year));
		}
	}
	
	@Override
	public Championship insert(Championship championship) {
		yearIsValid(championship);
		return repository.save(championship);
	}

	@Override
	public Championship findById(Integer id) {
		Optional<Championship> c = repository.findById(id);
		return c.orElseThrow(() -> new ObjectNotFound("id: %s não encontrado".formatted(id)));
	}

	@Override
	public List<Championship> findAll() {
		List<Championship> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum Championship encontrado");
		}
		return list;
	}

	@Override
	public Championship update(Championship championship) {
		findById(championship.getId());
		yearIsValid(championship);
		return repository.save(championship);
	}

	@Override
	public void delete(Integer id) {
		Championship c = findById(id);
		repository.delete(c);
	}

	@Override
	public List<Championship> findByYearBetween(Integer yearBefore, Integer yearAfter) {
		List<Championship> list = repository.findByYearBetween(yearBefore, yearAfter);
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum Championship encontrado");
		}
		return list;
	}

}

package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.repositories.ChampionshipRepository;
import br.com.trier.springvespertino.services.ChampionshipService;

@Service
public class ChampionshipServiceImpl implements ChampionshipService{

	@Autowired
	ChampionshipRepository repository;
	
	@Override
	public Championship insert(Championship championship) {
		return repository.save(championship);
	}

	@Override
	public Championship findById(Integer id) {
		Optional<Championship> c = repository.findById(id);
		return c.orElse(null);
	}

	@Override
	public List<Championship> findAll() {
		List<Championship> list = repository.findAll();
		return list;
	}

	@Override
	public Championship update(Championship championship) {
		return repository.save(championship);
	}

	@Override
	public void delete(Integer id) {
		Championship c = findById(id);
		if(c != null) {
			repository.delete(c);
		}
	}

	@Override
	public List<Championship> findByYearBetween(Integer yearBefore, Integer yearAfter) {
		List<Championship> list = repository.findByYearBetween(yearBefore, yearAfter);
		return list;
	}

}

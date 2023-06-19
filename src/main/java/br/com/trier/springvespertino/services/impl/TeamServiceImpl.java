package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.repositories.TeamRepository;
import br.com.trier.springvespertino.services.TeamService;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	TeamRepository repository;

	@Override
	public Team insert(Team team) {
		return repository.save(team);
	}

	@Override
	public Team findById(Integer id) {
		Optional<Team> team = repository.findById(id);
		return team.orElse(null);
	}

	@Override
	public List<Team> findAll() {
		List<Team> list = repository.findAll();
		return list;
	}

	@Override
	public Team update(Team team) {
		return repository.save(team);
	}

	@Override
	public void delete(Integer id) {
		Team team = findById(id);
		if(team != null) {
			repository.delete(team);
		}
	}

	@Override
	public Team findByNameIgnoreCase(String name) {
		Team team = repository.findByNameIgnoreCase(name);
		return team;
	}
}

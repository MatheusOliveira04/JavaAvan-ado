package br.com.trier.springvespertino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.repositories.TeamRepository;
import br.com.trier.springvespertino.services.TeamService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@Service
public class TeamServiceImpl implements TeamService{

	@Autowired
	TeamRepository repository;

	private void nameIsUnique(Team team) {
		Team busca = repository.findByNameIgnoreCase(team.getName());
		if(busca != null && busca.getId() != team.getId()) {
			throw new IntegrityViolation("Nome %s já existe".formatted(team.getName()));
		}
	}
	
	@Override
	public Team insert(Team team) {
		nameIsUnique(team);
		return repository.save(team);
	}

	@Override
	public Team findById(Integer id) {
		Optional<Team> team = repository.findById(id);
		return team.orElseThrow(() -> new ObjectNotFound("Id: %s do time não encontrado".formatted(id)));
	}

	@Override
	public List<Team> findAll() {
		List<Team> list = repository.findAll();
		if(list.isEmpty()) {
			throw new ObjectNotFound("Nenhum time encontrado");
		}
		return list;
	}

	@Override
	public Team update(Team team) {
		findById(team.getId());
		nameIsUnique(team);
		return repository.save(team);
	}

	@Override
	public void delete(Integer id) {
		Team team = findById(id);
		repository.delete(team);

	}

	@Override
	public Team findByNameIgnoreCase(String name) {
		Team team = repository.findByNameIgnoreCase(name);
		if(team == null) {
			throw new ObjectNotFound("%s não existe".formatted(name));
		}
		return team;
	}
}

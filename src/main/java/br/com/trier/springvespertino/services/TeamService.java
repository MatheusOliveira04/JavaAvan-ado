package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Team;

public interface TeamService {

	Team insert(Team team);
	
	Team findById(Integer id);
	
	List<Team> findAll();
	
	Team update(Team team);
	
	void delete(Integer id);
	
	Team findByNameIgnoreCase(String name);
}

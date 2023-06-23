package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Team;

public interface PilotService {

	Pilot findById(Integer id);

	List<Pilot> findAll();

	Pilot insert(Pilot pilot);

	Pilot update(Pilot pilot);

	void delete(Integer id);
	
	List<Pilot> findByNameContainingIgnoreCaseOrderById(String name);
	
	List<Pilot> findByTeam(Team team);
}

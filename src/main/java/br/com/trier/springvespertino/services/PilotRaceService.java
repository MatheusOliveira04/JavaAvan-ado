package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.PilotRace;
import br.com.trier.springvespertino.models.Race;

public interface PilotRaceService {

	List<PilotRace> findAll();
	
	PilotRace findById(Integer id);
	
	PilotRace insert(PilotRace pilotRace);
	
	PilotRace update(PilotRace pilotRace);
	
	void delete(Integer id);
	
	List<PilotRace> findByPilotIgnoreCase(Pilot pilot);
	
	List<PilotRace> findByRaceIgnoreCase(Race race);
}

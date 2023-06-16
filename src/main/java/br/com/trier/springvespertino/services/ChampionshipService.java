package br.com.trier.springvespertino.services;

import java.util.List;

import br.com.trier.springvespertino.models.Championship;

public interface ChampionshipService {

	Championship insert(Championship championship);
	
	Championship findById(Integer id);
	
	List<Championship> findAll();
	
	Championship update(Championship championship);
	
	void delete(Integer id);
}

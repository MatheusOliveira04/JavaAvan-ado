package br.com.trier.springvespertino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Championship;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{

	
	List<Championship> findByYearBetween(Integer yearBefore, Integer yearAfter); 
}

package br.com.trier.springvespertino.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;

@Repository
public interface RaceRespository extends JpaRepository<Race, Integer>{

	List<Race> findByDateAfter(ZonedDateTime date);
	List<Race> findBySpeedwayOrderById(Speedway speedway);
	List<Race> findByChampionshipOrderById(Championship championship);
}

package br.com.trier.springvespertino.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.models.dto.PilotRaceDTO;
import br.com.trier.springvespertino.models.dto.PilotRaceTeamDTO;
import br.com.trier.springvespertino.models.dto.RaceCountryYearDTO;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.models.dto.SpeedwayChampionshipDTO;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.PilotRaceService;
import br.com.trier.springvespertino.services.PilotService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;
import br.com.trier.springvespertino.services.TeamService;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping("/findBy")
public class ReportResource {

	@Autowired
	CountryService countryService;

	@Autowired
	SpeedwayService speedwayService;

	@Autowired
	RaceService raceService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	PilotRaceService pilotRaceService;
	
	@Autowired
	ChampionshipService championshipService;

	@GetMapping("/races-by-country-year/{countryId}/{year}")
	public ResponseEntity<RaceCountryYearDTO> findRaceByCountryAndYear(@PathVariable Integer countryId,
			@PathVariable Integer year) {

		Country country = countryService.findById(countryId);

		List<RaceDTO> raceDTOs = speedwayService.findByCountryOrderBySizeDesc(country).stream().flatMap(speedway -> {
			try {
				return raceService.findBySpeedwayOrderById(speedway).stream();
			} catch (ObjectNotFound e) {
				return Stream.empty();
			}
		}).filter(race -> race.getDate().getYear() == year).map(Race::toDTO).toList();

		return ResponseEntity.ok(new RaceCountryYearDTO(year, country.getName(), raceDTOs.size(), raceDTOs));
	}
	
	@GetMapping("/race-by-team/{idTeam}")
	public ResponseEntity<PilotRaceTeamDTO> findRaceByTeam(@PathVariable Integer idTeam){
		Team team = teamService.findById(idTeam);
		
		List<PilotRaceDTO> list = pilotService.findByTeam(team).stream().flatMap(pilot ->{
			try {
				return pilotRaceService.findByPilot(pilot).stream();
			} catch(ObjectNotFound onf) {
				return Stream.empty();
			}
		}).map(p -> p.toDTO()).toList();
		
		return ResponseEntity.ok(new PilotRaceTeamDTO(team.getName(), list.size(), list));
	}

	@GetMapping("/speedway-by-championship/{idChampionship}")
	public ResponseEntity<SpeedwayChampionshipDTO> findChampionshipBySpeedway(
			@PathVariable Integer idChampionship){
		
		Championship championship = championshipService.findById(idChampionship);
		
		List<Speedway> list = raceService.findByChampionshipOrderById(championship).stream()
				.map(r -> r.getSpeedway())
				.toList();
		
		return ResponseEntity.ok(new SpeedwayChampionshipDTO(championship.getDescription(),
				list.size(), list));
	}
}

package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;
import br.com.trier.springvespertino.utils.DateUtil;

@RestController
@RequestMapping("/race")
public class RaceResource {
	
	@Autowired
	RaceService service;
	
	@Autowired
	SpeedwayService speedwayService;
	
	@Autowired
	ChampionshipService championshipService;

	@GetMapping
	public ResponseEntity<List<RaceDTO>> listAll() {
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(r -> r.toDTO())
				.toList());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RaceDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping("/date/{date}")
	public ResponseEntity<List<RaceDTO>> findByDateAfter(@PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") String date){
		return ResponseEntity.ok(service.findByDateAfter(DateUtil.convertStringToZDT(date))
				.stream()
				.map(r -> r.toDTO())
				.toList());
	}
	
	@GetMapping("/speedway/{idSpeedway}")
	public ResponseEntity<List<RaceDTO>> findByRaceOrderById(@PathVariable Integer idSpeedway){
		return ResponseEntity.ok(service.findBySpeedwayOrderById(speedwayService.findById(idSpeedway))
				.stream()
				.map(race -> race.toDTO())
				.toList());
	}
	
	@GetMapping("/championship/{idChampionship}")
	public ResponseEntity<List<RaceDTO>> findByChampionshipOrderById(@PathVariable Integer idChampionship){
		return ResponseEntity.ok(service.findByChampionshipOrderById(
				championshipService.findById(idChampionship))
				.stream()
				.map(race -> race.toDTO())
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<RaceDTO> insert(@RequestBody RaceDTO raceDTO) {
		Race race = new Race(raceDTO, 
				speedwayService.findById(raceDTO.getSpeedwayId()),
				championshipService.findById(raceDTO.getChampionshipId()));
		return ResponseEntity.ok(service.insert(race).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RaceDTO> update(@PathVariable Integer id, @RequestBody RaceDTO raceDTO) {
		Race race = new Race(raceDTO, 
				speedwayService.findById(raceDTO.getSpeedwayId()),
				championshipService.findById(raceDTO.getChampionshipId()));
		race.setId(id);
		return ResponseEntity.ok(service.update(race).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}

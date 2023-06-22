package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.RaceService;
import br.com.trier.springvespertino.services.SpeedwayService;

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
	public ResponseEntity<List<Race>> listAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Race> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Race> insert(@RequestBody Race race) {
		speedwayService.findById(race.getSpeedway().getId());
		championshipService.findById(race.getChampionship().getId());
		return ResponseEntity.ok(service.insert(race));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Race> update(@PathVariable Integer id, @RequestBody Race race) {
		race.setId(id);
		speedwayService.findById(race.getSpeedway().getId());
		championshipService.findById(race.getChampionship().getId());
		return ResponseEntity.ok(service.update(race));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}

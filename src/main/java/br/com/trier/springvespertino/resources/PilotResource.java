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

import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.dto.PilotDTO;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.PilotService;
import br.com.trier.springvespertino.services.TeamService;

@RestController
@RequestMapping("/pilot")
public class PilotResource {

	@Autowired
	private PilotService service;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private TeamService teamService;
		
	@GetMapping("/{id}")
	public ResponseEntity<PilotDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<PilotDTO>> listAll() {
		return ResponseEntity.ok(service.findAll()
				.stream()
				.map(pilot -> pilot.toDTO())
				.toList());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<PilotDTO>> findByNameContainingIgnoreCaseOrderById(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainingIgnoreCaseOrderById(name)
				.stream()
				.map(pilot -> pilot.toDTO())
				.toList());
	}
	
	@PostMapping
	public ResponseEntity<PilotDTO> insert(@RequestBody PilotDTO pilotDTO) {
		Pilot pilot = new Pilot(pilotDTO, countryService.findById(pilotDTO.getCountryId()),
		teamService.findById(pilotDTO.getTeamId()));
		return ResponseEntity.ok(service.insert(pilot).toDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<PilotDTO> update(@PathVariable Integer id, @RequestBody PilotDTO pilotDTO) {
		Pilot pilot = new Pilot(pilotDTO,
				countryService.findById(pilotDTO.getCountryId()), 
				teamService.findById(pilotDTO.getTeamId()));
		pilot.setId(id);
		return ResponseEntity.ok(service.update(pilot).toDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	
}

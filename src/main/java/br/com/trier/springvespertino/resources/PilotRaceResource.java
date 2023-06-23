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
import br.com.trier.springvespertino.models.PilotRace;
import br.com.trier.springvespertino.models.dto.PilotRaceDTO;
import br.com.trier.springvespertino.services.PilotRaceService;
import br.com.trier.springvespertino.services.PilotService;
import br.com.trier.springvespertino.services.RaceService;

@RestController
@RequestMapping("/pilotrace")
public class PilotRaceResource {

	@Autowired
	PilotRaceService service;

	@Autowired
	PilotService pilotService;

	@Autowired
	RaceService raceService;

	@GetMapping("/{id}")
	public ResponseEntity<PilotRaceDTO> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}

	@GetMapping
	public ResponseEntity<List<PilotRaceDTO>> findAll() {
		return ResponseEntity.ok(service.findAll().stream().map(pr -> pr.toDTO()).toList());
	}
	
	@PostMapping
	public ResponseEntity<PilotRaceDTO> insert(@RequestBody PilotRaceDTO dto){
		PilotRace pr = new PilotRace(dto, 
				pilotService.findById(dto.getPilotId()), 
				raceService.findById(dto.getRaceId()));
		return ResponseEntity.ok(service.insert(pr).toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PilotRaceDTO> update(@PathVariable Integer id, @RequestBody PilotRaceDTO dto){
		PilotRace pr = new PilotRace(dto, 
				pilotService.findById(dto.getPilotId()), 
				raceService.findById(dto.getRaceId()));
		pr.setId(id);
		return ResponseEntity.ok(service.update(pr).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pilot/{idPilot}")
	public ResponseEntity<List<PilotRaceDTO>> findByPilot(@PathVariable Integer idPilot){
		return ResponseEntity.ok(service.findByPilot(pilotService.findById(idPilot))
				.stream().map(p -> p.toDTO()).toList());
	}
	
	@GetMapping("/race/{idPilot}")
	public ResponseEntity<List<PilotRaceDTO>> findByRace(@PathVariable Integer idRace){
		return ResponseEntity.ok(service.findByRace(raceService.findById(idRace))
				.stream().map(p -> p.toDTO()).toList());
	}
}

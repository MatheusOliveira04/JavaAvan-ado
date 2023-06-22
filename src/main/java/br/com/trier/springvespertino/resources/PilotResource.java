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
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.PilotService;

@RestController
@RequestMapping("/pilot")
public class PilotResource {

	@Autowired
	private PilotService service;
	
	@Autowired
	private CountryService countryService;
		
	@GetMapping("/{id}")
	public ResponseEntity<Pilot> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Pilot>> listAll() {
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Pilot>> findByNameContainingIgnoreCaseOrderById(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainingIgnoreCaseOrderById(name));
	}
	
	@PostMapping
	public ResponseEntity<Pilot> insert(@RequestBody Pilot pilot) {
		countryService.findById(pilot.getCountry().getId());
		return ResponseEntity.ok(service.insert(pilot));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pilot> update(@PathVariable Integer id, @RequestBody Pilot pilot) {
		pilot.setId(id);
		countryService.findById(pilot.getCountry().getId());
		return ResponseEntity.ok(service.update(pilot));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}

	
}

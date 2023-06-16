package br.com.trier.springvespertino.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.services.ChampionshipService;

@RestController
@RequestMapping("/championship")
public class ChampionshipResource {
	
	@Autowired
	ChampionshipService service;
	
	@PostMapping
	public ResponseEntity<Championship> insert(@RequestBody Championship champ){
		Championship c = service.insert(champ);
		return c != null ? ResponseEntity.ok(c) : ResponseEntity.noContent().build(); 
	}

	@GetMapping("/{id}")
	public ResponseEntity<Championship> findById(@PathVariable Integer id){
		Championship c = service.findById(id);
		return c != null ? ResponseEntity.ok(c) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Championship>> findAll(){
		List<Championship> list = service.findAll();
		return list.size() > 0 ? ResponseEntity.ok(list) : ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Championship> update(@PathVariable Integer Id, @RequestBody Championship c){
		c.setId(Id);
		Championship champ = service.update(c);
		return champ != null ? ResponseEntity.ok(champ) : ResponseEntity.noContent().build();
	}
}

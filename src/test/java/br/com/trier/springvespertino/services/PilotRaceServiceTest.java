package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.PilotRace;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PilotRaceServiceTest extends BaseTests{

	@Autowired
	PilotRaceService service;
	
	@Autowired
	PilotService pilotService;
	
	@Autowired
	RaceService raceService;
	
	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void findById() {
		PilotRace pilotRace = service.findById(1);
		assertNotNull(pilotRace);
	}
	
	@Test
	@DisplayName("Teste buscar por id não existe")
	void findByIdNonExist() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void findAll() {
		List<PilotRace> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste Buscar todos nenhum encontrado")
	void findAllEmpty() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum cadastro", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void insert() {
		PilotRace pilotRace = new PilotRace(1, 1, pilotService.findById(1), raceService.findById(1));
		pilotRace = service.insert(pilotRace);
		assertNotNull(pilotRace);
		assertEquals(1, pilotRace.getId());
		assertEquals(1, pilotRace.getColocacao());
		assertEquals(1, pilotRace.getPilot().getId());
		assertEquals("Piloto 1", pilotRace.getPilot().getName());
		assertEquals(1, pilotRace.getRace().getId());
	}
	
	@Test
	@DisplayName("Teste inserir com piloto nulo")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/race.sql"})
	void insertPilotNull() {
		PilotRace pilotRace = new PilotRace(null, 1, null, raceService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(pilotRace));
		assertEquals("Piloto está nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com corrida nulo")
	@Sql({ "classpath:/resources/sqls/country.sql" })
	@Sql({ "classpath:/resources/sqls/team.sql" })
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void insertRaceNull() {
		PilotRace pilotRace = new PilotRace(1, 1, pilotService.findById(1), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(pilotRace));
		assertEquals("Corrida está nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void update(){
		PilotRace pilotRace = new PilotRace(1, 10, pilotService.findById(2), raceService.findById(2));
		pilotRace = service.update(pilotRace);
		assertNotNull(pilotRace);
		assertEquals(1, pilotRace.getId());
		assertEquals(10, pilotRace.getColocacao());
		assertEquals(2, pilotRace.getPilot().getId());
		assertEquals("Piloto 2", pilotRace.getPilot().getName());
		assertEquals(2, pilotRace.getRace().getId());
	}
	
	@Test
	@DisplayName("Teste atualizar com piloto nulo")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void updatePilotNull() {
		PilotRace pilotRace = new PilotRace(1, 10, null, raceService.findById(1));
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(pilotRace));
		assertEquals("Piloto está nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com corrida nulo")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void updateRaceNull() {
		PilotRace pilotRace = new PilotRace(1, 20, pilotService.findById(2), null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(pilotRace));
		assertEquals("Corrida está nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar id não encontrado")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void updateIdNotFound() {
		PilotRace pilotRace = new PilotRace(10, 20, pilotService.findById(2), raceService.findById(2));
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(pilotRace));
		assertEquals("id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void delete() {
		List<PilotRace> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(1);
		list = service.findAll();
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("Teste deletar id não existe")
	void deleteIdNotFound() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por piloto")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void findByPilot() {
		List<PilotRace> list = service.findByPilot(pilotService.findById(1));
		assertEquals(1, list.size());
		assertEquals(1, list.get(0).getId());
		assertEquals("Piloto 1", list.get(0).getPilot().getName());
	}
	
	@Test
	@DisplayName("Teste buscar por piloto não existe")
	void findByPilotNonExist() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByPilot(pilotService.findById(10)));
		assertEquals("piloto 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar por corrida")
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void findByRace() {
		List<PilotRace> list = service.findByRace(raceService.findById(1));
		assertEquals(1, list.size());
		assertEquals(1, list.get(0).getId());
	}
	
	@Test
	@DisplayName("Teste buscar por corrida não existe")
	void findByRaceNonExist() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByRace(raceService.findById(10)));
		assertEquals("Race 10 não encontrado", exception.getMessage());
	}
}

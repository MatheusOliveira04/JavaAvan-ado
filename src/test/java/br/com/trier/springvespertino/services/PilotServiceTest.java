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
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class PilotServiceTest extends BaseTests {

	@Autowired
	PilotService service;
	
	@Autowired
	CountryService countryService;
	
	@Autowired
	TeamService teamService;

	@Test
	@DisplayName("Teste buscar por id")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void findByIdTest() {
		Pilot pilot = service.findById(1);
		assertNotNull(pilot);
		assertEquals(1, pilot.getId());
		assertEquals("Piloto 1", pilot.getName());
		assertEquals("Brazil", pilot.getCountry().getName());
		assertEquals("Equipe 1", pilot.getTeam().getName());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void findByIdNonExist() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("piloto 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar todos")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void listAllTest() {
		List<Pilot> list = service.findAll();
		assertNotNull(list);
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId());
		assertEquals(2, list.get(1).getId());
	}

	@Test
	@DisplayName("Teste buscar todos nenhum encontrado")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum piloto cadastrado", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir")
	@Sql({ "classpath:/resources/sqls/country.sql" })
	@Sql({ "classpath:/resources/sqls/team.sql" })
	void insertTest() {
		Pilot pilot = new Pilot(1, "insert", new Country(1, "insert"), new Team(1, "insert"));
		pilot = service.insert(pilot);
		assertNotNull(pilot);
		assertEquals(1, pilot.getId());
		assertEquals("insert", pilot.getName());
		assertEquals(1, pilot.getCountry().getId());
		assertEquals("Brazil", pilot.getCountry().getName());
		assertEquals(1, pilot.getCountry().getId());
		assertEquals("Equipe 1", pilot.getTeam().getName());
	}

	@Test
	@DisplayName("Teste inserir pilot com country null")
	void insertWithCountryNull() {
		Pilot pilot = new Pilot(1, "insert", null, new Team(1, "insert"));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(pilot));
		assertEquals("Country está null", exception.getMessage());
	}

	@Test
	@DisplayName("Teste atualizar")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void update() {
		Pilot pilot = new Pilot(1, "update", new Country(1, null), new Team(1, null));
		service.update(pilot);
		pilot = null;
		pilot = service.findById(1);
		assertEquals(1, pilot.getId());
		assertEquals("update", pilot.getName());
		assertEquals(1, pilot.getCountry().getId());
		assertEquals("Brazil", pilot.getCountry().getName());
		assertEquals(1, pilot.getTeam().getId());
		assertEquals("Equipe 1", pilot.getTeam().getName());
	}

	@Test
	@DisplayName("Teste atualizar com country null")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void updateInvalidCountry() {
		Pilot pilot = new Pilot(1, "update", null, new Team(1, "update"));
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(pilot));
		assertEquals("Country está null", exception.getMessage());
	}

	@Test
	@DisplayName("Teste deletar")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void deleteTest() {
		List<Pilot> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(1);
		list = service.findAll();
		assertEquals(1, list.size());
	}

	@Test
	@DisplayName("Teste delete id não encotrado")
	@Sql({ "classpath:/resources/sqls/pilot.sql" })
	void deleteNotFoundId() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("piloto 10 não existe", exception.getMessage());
	}
}

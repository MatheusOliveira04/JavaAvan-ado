package br.com.trier.springvespertino.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.services.TeamService;
import jakarta.transaction.Transactional;

@Transactional
public class TeamServiceTest extends BaseTests{

	@Autowired
	TeamService service;

	@Test
	@DisplayName("Teste buscar Team por id")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByIdTest() {
		var team = service.findById(1);
		assertNotNull(team);
		assertEquals("Equipe 1", team.getName());
		assertEquals(1, team.getId());
	}
	
	@Test
	@DisplayName("Teste buscar Team por id não existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByIdNonExist() {
		var team = service.findById(10);
		assertNull(team);
	}
	
	@Test
	@DisplayName("Teste buscar todos Team")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void listAllTest() {
		List<Team> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	
	@Test
	@DisplayName("Teste inserir Team")
	void insertTest() {
		var team = new Team(1, "insert");
		service.insert(team);
		team = null;
		assertNull(team);
		team = service.findById(1);
		assertNotNull(team);
		assertEquals("insert", team.getName());
		assertEquals(1, team.getId());
	}
	
	@Test
	@DisplayName("Teste atualizar Team")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void updateTest() {
		var team = new Team(1, "update");
		service.update(team);
		team = null;
		assertNull(team);
		team = service.findById(1);
		assertNotNull(team);
		assertEquals("update", team.getName());
	}
	
	@Test
	@DisplayName("Teste remove Team")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void deleteTest() {
		List<Team> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(1);
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste remover Team não existente")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void deleteIdNonExist() {
		List<Team> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(10);
		list = service.findAll();
		assertEquals(2, list.size());
	}
}

package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.services.TeamService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
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
		var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
		assertEquals("id: 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos Team")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void listAllTest() {
		List<Team> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos Team não cadastrados")
	void listAllNonExistTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum team encontrado", exception.getMessage());
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
	@DisplayName("Teste inserir Team name duplicado")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void insertNameAlreadyExist() {
		Team team = new Team(3, "Equipe 1");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(team));
		assertEquals("Nome Equipe 1 já existe", exception.getMessage());
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
	@DisplayName("Teste atualizar Team id não existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void updateIdNonExistTest() {
		Team team = new Team(10, "update");
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(team));
		assertEquals("id: 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar Team name duplicado")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void updateNameAlreadyExistTest() {
		Team team = new Team(2, "Equipe 1");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(team));
		assertEquals("Nome Equipe 1 já existe", exception.getMessage());
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
	@DisplayName("Teste remover Team id não existente")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void deleteIdNonExist() {
		var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
		assertEquals("id: 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByNameTest() {
		var team = service.findByNameIgnoreCase("EQUIPE 2");
		assertNotNull(team);
		assertEquals("Equipe 2", team.getName());
		assertEquals(2, team.getId());
	}

	@Test
	@DisplayName("Teste buscar por nome que não existe")
	@Sql({"classpath:/resources/sqls/team.sql"})
	void findByNameNonExistTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findByNameIgnoreCase("findBy")); 
		assertEquals("findBy não existe", exception.getMessage());
	}
}

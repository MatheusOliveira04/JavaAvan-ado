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
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.services.ChampionshipService;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipTest extends BaseTests{

	@Autowired
	ChampionshipService service;
	
	@Test
	@DisplayName("Teste buscar id existente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByIdTest() {
		Championship champ = service.findById(1);
		assertNotNull(champ);
		assertEquals(1, champ.getId());
		assertEquals("World Cup", champ.getDescription());
		assertEquals(2024, champ.getYear());
	}
	
	@Test
	@DisplayName("Teste buscar id não existente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByIdNonExistTest() {
		Championship champ = service.findById(10);
		assertNull(champ);
	}
	
	@Test
	@DisplayName("Teste buscar todos Championship")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void FindAllTest() {
		List<Championship> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste inserir Championship")
	void insertTest() {
		Championship champ = new Championship(null, "insert", 1000);
		service.insert(champ);
		champ = service.findById(1);
		assertNotNull(champ);
		assertEquals(1, champ.getId());
		assertEquals("insert",champ.getDescription());
		assertEquals(1000, champ.getYear());
	}
	
	@Test
	@DisplayName("Teste atualizar Championship")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateTest() {
		var champ = service.findById(1);
		assertNotNull(champ);
		assertEquals(1, champ.getId());
		assertEquals("World Cup", champ.getDescription());
		assertEquals(2024, champ.getYear());
		Championship champUpdate = new Championship(1, "update", 2000);
		service.update(champUpdate);
		champ = service.findById(1);
		assertNotNull(champ);
		assertEquals("update", champ.getDescription());
		assertEquals(2000, champ.getYear());
	}
	
	@Test
	@DisplayName("Teste deletar Championship")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void deleteTest() {
		List<Championship> list = service.findAll();
		assertEquals(2, list.size());
		Championship champ = list.get(0);
		assertNotNull(champ);
		assertEquals("World Cup", champ.getDescription());
		service.delete(champ.getId());
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por ano")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearTest() {
		List<Championship> list = service.findByYearBetween(2024, 2028);
		assertEquals(1, list.size());
		var c = list.get(0);
		assertEquals(1, c.getId());
		assertEquals(2024, c.getYear());
		assertEquals("World Cup", c.getDescription());
	}
}

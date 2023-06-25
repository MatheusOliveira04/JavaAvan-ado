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
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipSericeTest extends BaseTests{

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
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("campeonato id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void FindAllTest() {
		List<Championship> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todos campeonatos sem cadastro")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
		assertEquals("Nenhum campeonato encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		Championship champ = new Championship(1, "insert", 2000);
		service.insert(champ);
		champ = service.findById(1);
		assertNotNull(champ);
		assertEquals(1, champ.getId());
		assertEquals("insert",champ.getDescription());
		assertEquals(2000, champ.getYear());
	}
	
	@Test
	@DisplayName("Teste inserir campeonato com ano inválido")
	void insertYearInvalidTest() {
		Championship c = new Championship(1, "insert",1000);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(c));
		assertEquals("Ano inválido, deve ser entre 1990 e 2024", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir campeonato com ano null")
	void insertYearNullTest() {
		Championship c = new Championship(1, "insert",null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(c));
		assertEquals("Ano não pode ser nulo", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
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
	@DisplayName("Teste atualizar campeonato id não encontrado")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateIdNotFoundTest() {
		Championship c = new Championship(10, "update", 2000);
		var exception = assertThrows(ObjectNotFound.class, () -> service.update(c));
		assertEquals("campeonato id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar campeonato com ano inválido")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateYearInvalidTest() {
		Championship c = new Championship(1, "update", 1000);
		var exception = assertThrows(IntegrityViolation.class,() -> service.update(c));
		assertEquals("Ano inválido, deve ser entre 1990 e 2024", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
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
	@DisplayName("Teste deletar campeonato id não encontrado")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void deleteIdNotFoundTest() {
		var exception = assertThrows(ObjectNotFound.class,() -> service.delete(10));
		assertEquals("campeonato id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano inicial e final")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearBetwenTest() {
		List<Championship> list = service.findByYearBetween(2024, 2028);
		assertEquals(1, list.size());
		var c = list.get(0);
		assertEquals(1, c.getId());
		assertEquals(2024, c.getYear());
		assertEquals("World Cup", c.getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano inicial e final")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearBetweenNonExist() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByYearBetween(1915, 1916));
		assertEquals("Nenhum campeonato encontrado entre 1915 e 1916", exception.getMessage());
	}
}

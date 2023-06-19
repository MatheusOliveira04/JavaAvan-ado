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
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.services.CountryService;
import jakarta.transaction.Transactional;

@Transactional
public class CountryServiceTest extends BaseTests{

	@Autowired
	CountryService service;
	
	@Test
	@DisplayName("Teste buscar id existente")
	@Sql({"classpath:resources/sqls/country.sql"})
	void findByIdTest() {
		var c = service.findById(1);
		assertNotNull(c);
		assertEquals("Brazil", c.getName());
		assertEquals(1, c.getId());
	}
	
	@Test
	@DisplayName("Teste buscar id não existe")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdNonExist() {
		var c = service.findById(10);
		assertNull(c);
	}
	
	@Test
	@DisplayName("Teste buscar todos Country")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listAll() {
		List<Country> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste Inserir Country")
	void insertTest() {
		var c = new Country(1,"Argentina");
		assertNotNull(c);
		service.insert(c);
		c = null;
		assertNull(c);
		c = service.findById(1);
		assertNotNull(c);
		assertEquals(1, c.getId());
		assertEquals("Argentina", c.getName());
	}
	
	@Test
	@DisplayName("Teste atualiza Country")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void updateTest() {
		var c = service.findById(1);
		assertNotNull(c);
		assertEquals("Brazil", c.getName());
		Country country = new Country(1, "update");
		service.update(country);
		c = null;
		assertNull(c);
		c = service.findById(1);
		assertNotNull(c);
		assertEquals("update", c.getName());
	}
	
	@Test
	@DisplayName("Teste deletar Country existente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteTest() {
		List<Country> list = service.findAll();
		assertEquals(2, list.size());
		var c = list.get(0);
		service.delete(c.getId());
		list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar id não existe")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteNonExistTest() {
		List<Country> list = service.findAll();
		assertEquals(2, list.size());
		service.delete(10);
		list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar por nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameTest() {
		List<Country> list = service.findByNameContainingIgnoreCase("A");
		assertEquals(2, list.size());
		list = service.findByNameContainingIgnoreCase("Z");
		assertEquals(1, list.size());
		list = service.findByNameContainingIgnoreCase("G");
		assertEquals(0, list.size());
	}
}

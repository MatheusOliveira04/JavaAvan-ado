package br.com.trier.springvespertino.service;

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
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.services.CountryService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
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
		var exception = assertThrows(ObjectNotFound.class,() -> service.findById(10));
		assertEquals("id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar todos Country")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listAll() {
		List<Country> list = service.findAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar todos Country sem cadastro")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findAll());
			assertEquals("Nenhum Country encontrado", exception.getMessage());
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
	@DisplayName("Teste inserir nome duplicado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertNameAlreadyExistTest() {
		Country c = new Country(3,"Brazil");
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(c));
		assertEquals("Nome Brazil já existe", exception.getMessage());
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
	@DisplayName("Teste atualiza nome duplicado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void updateDoubleNameTest() {
		Country c = new Country(2, "Brazil");
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(c));
		assertEquals("Nome Brazil já existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualiza id não encontrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void updateIdNonExist() {
		Country c = new Country(10, "Update");
		var exception = assertThrows(ObjectNotFound.class,() -> service.update(c));
		assertEquals("id: 10 não encontrado", exception.getMessage());
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
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("id: 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste contém no nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameContainsTest() {
		List<Country> list = service.findByNameContainingIgnoreCase("A");
		assertEquals(2, list.size());
		list = service.findByNameContainingIgnoreCase("Z");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste não contém no nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameNonContainsTest() {
	 var exception = assertThrows(ObjectNotFound.class,() -> service.findByNameContainingIgnoreCase("G"));
	 assertEquals("Nenhum Country encontrado", exception.getMessage());
	}
}

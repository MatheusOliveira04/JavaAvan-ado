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
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class SpeedwayServiceTest extends BaseTests{

	@Autowired
	SpeedwayService service;
	
	@Autowired
	CountryService countryService;

	@Test
	@DisplayName("Teste buscar por id")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql" })
	void findByIdTest() {
		Speedway sp = service.findById(1);
		assertNotNull(sp);
		assertEquals(1, sp.getId());
		assertEquals("pista 1", sp.getName());
		assertEquals(100, sp.getSize());
	}

	@Test
	@DisplayName("Teste buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	void findByIdNonExist() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("pista 10 não existe", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar todos")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql" })
	void listAllTest() {
		List<Speedway> list = service.listAll();
		assertNotNull(list);
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId());
		assertEquals(2, list.get(1).getId());
	}

	@Test
	@DisplayName("Teste buscar todos nenhum encontrado")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
		assertEquals("Nenhuma pista cadastrada", exception.getMessage());
	}

	@Test
	@DisplayName("Teste inserir")
	void insertTest() {
		Speedway sp = new Speedway(1, "insert", 100, new Country(1, "insert"));
		assertNotNull(sp);
		assertEquals(1, sp.getId());
		assertEquals("insert", sp.getName());
		assertEquals(100, sp.getSize());
		assertEquals(1, sp.getCountry().getId());
		assertEquals("insert", sp.getCountry().getName());
	}

	@Test
	@DisplayName("Teste inserir speedway com size inválido")
	void insertWithCountryNull() {
		Speedway sp = new Speedway(1, "insert" , 0, new Country(1, "insert"));
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(sp));
		assertEquals("Tamanho da pista inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir com country inválido")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertInvalidCountry() {
		Speedway sp = new Speedway(1, "Argentina" , 100, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.insert(sp));
		assertEquals("Country está null", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar")
	@Sql({"classpath:/resources/sqls/country.sql" })
	@Sql({"classpath:/resources/sqls/speedway.sql" })
	void update() {
		Country country = countryService.findById(1);
		Speedway sp = new Speedway(1, "update" , 100,countryService.findById(1));
		service.update(sp);
		sp = null;
		sp = service.findById(1);
		assertEquals(1, sp.getId());
		assertEquals("update", sp.getName());
		assertEquals(100, sp.getSize());
		assertEquals(1, sp.getCountry().getId());
		assertEquals("Brazil", sp.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste atualizar com size inválido")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql" })
	void updateInvalidSize() {
		Speedway sp = new Speedway(1, "update" , 0, new Country(1, "Brazil"));
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(sp));
		assertEquals("Tamanho da pista inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar com country inválido")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql" })
	void updateInvalidCountry() {
		Speedway sp = new Speedway(1, "update" , 100, null);
		var exception = assertThrows(IntegrityViolation.class, () -> service.update(sp));
		assertEquals("Country está null", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void deleteTest() {
		List<Speedway> list = service.listAll();
		assertEquals(2, list.size());
		service.delete(1);
		list = service.listAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste delete id não encotrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void deleteNotFoundId(){
		var exception = assertThrows(ObjectNotFound.class, () -> service.delete(10));
		assertEquals("pista 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar que contém no nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findByNameContains() {
		List<Speedway> list = service.findByNameStartsWithIgnoreCase("pist");
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar que contém no nome sem cadastro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findByNameContainsIsEmpty() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByNameStartsWithIgnoreCase("teste"));
		assertEquals("Nenhuma pista cadastrada com esse nome", exception.getMessage());
	}

	@Test
	@DisplayName("Teste buscar que está entre size")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findBySizeBetween() {
		List<Speedway> list = service.findBySizeBetween(100,150);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar que está entre size sem cadastro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findBySizeBetweenIsEmpty() {
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findBySizeBetween(10,20));
		assertEquals("Nenhuma pista cadastrada com essas medidas", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar que está entre size")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findByCountry() {
		Country c = new Country(1, "Brazil");
		List<Speedway> list = service.findByCountryOrderBySizeDesc(c);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar que está entre size sem cadastro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({ "classpath:/resources/sqls/speedway.sql"})
	void findByCountryIsEmpty() {
		Country c = new Country(10, "França");
		var exception = assertThrows(ObjectNotFound.class, 
				() -> service.findByCountryOrderBySizeDesc(c));
		assertEquals("Nenhuma pista cadastrada no país: França", exception.getMessage());
	}
}

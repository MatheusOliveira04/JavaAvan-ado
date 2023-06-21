package br.com.trier.springvespertino.resrouces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.models.Country;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/country.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Country> getCountry(String url) {
		return rest.getForEntity(url, Country.class);
	}

	@Test
	@DisplayName("Busca por id")
	void findByIdTest() {
		ResponseEntity<Country> re = getCountry("/country/1");
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		Country cTest = re.getBody();
		assertEquals(1, cTest.getId());
	}

	@Test
	@DisplayName("busca id não existe")
	void findByNotFound() {
		ResponseEntity<Country> re = rest.getForEntity("/country/10", Country.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("listar todos")
	void listAllTest() {
		ResponseEntity<List<Country>> re = rest.exchange("/country", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Country>>() {
				});
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste inserir")
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		var c = new Country(1, "insert");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> httpEntity = new HttpEntity<>(c, headers);
		ResponseEntity<Country> re = rest.exchange("/country", HttpMethod.POST, httpEntity,
				Country.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		c = null;
		c = re.getBody();
		assertEquals("insert", c.getName());
	}

	@Test
	@DisplayName("Teste atualiza")
	void updateTest() {
		var c = new Country(null, "update");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> httpEntity = new HttpEntity<>(c, header);
		ResponseEntity<Country> re = rest.exchange("/country/1", HttpMethod.PUT, httpEntity,
				Country.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertEquals("update", c.getName());
	}

	@Test
	@DisplayName("Teste atualiza id não existe")
	void updateIdNotFound() {
		var c = new Country(null, "update");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Country> he = new HttpEntity<>(c, header);
		ResponseEntity<Country> re = rest.exchange("/country/10", HttpMethod.PUT, he, Country.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Teste deletar")
	void deleteTest() {
		ResponseEntity<Void> re = rest.exchange("/country/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste deletar id não existe")
	void delteIdNotFound() {
		ResponseEntity<Void> re = rest.exchange("/country/10", HttpMethod.DELETE, null, Void.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}

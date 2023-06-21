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

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.models.Team;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/team.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamResourceTest {

	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<Team> getTeam(String url) {
		return rest.getForEntity(url, Team.class);
	}

	@Test
	@DisplayName("Busca por id")
	void findByIdTest() {
		ResponseEntity<Team> re = getTeam("/team/1");
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		Team tTest = re.getBody();
		assertEquals(1, tTest.getId());
	}

	@Test
	@DisplayName("busca id não existe")
	void findByNotFound() {
		ResponseEntity<Team> re =  getTeam("/team/10");
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("busca por nome")
	void findByName() {
		ResponseEntity<Team> re =  getTeam("/team/name/Equipe 1");
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("busca por nome não existe")
	void findByNameNonExist() {
		ResponseEntity<Team> re =  getTeam("/team/name/Equi");
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	
	@Test
	@DisplayName("listar todos")
	void listAllTest() {
		ResponseEntity<List<Team>> re = rest.exchange("/team", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Team>>() {
				});
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste inserir")
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		var t = new Team(1, "insert");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Team> httpEntity = new HttpEntity<>(t, headers);
		ResponseEntity<Team> re = rest.exchange("/team", HttpMethod.POST, httpEntity,
				Team.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		t = null;
		t = re.getBody();
		assertEquals("insert", t.getName());
	}

	@Test
	@DisplayName("Teste atualiza")
	void updateTest() {
		var c = new Team(null, "update");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Team> httpEntity = new HttpEntity<>(c, header);
		ResponseEntity<Team> re = rest.exchange("/team/1", HttpMethod.PUT, httpEntity,
				Team.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		assertEquals("update", c.getName());
	}

	@Test
	@DisplayName("Teste atualiza id não existe")
	void updateIdNotFound() {
		var c = new Team(null, "update");
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Team> he = new HttpEntity<>(c, header);
		ResponseEntity<Team> re = rest.exchange("/team/10", HttpMethod.PUT, he, Team.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Teste deletar")
	void deleteTest() {
		ResponseEntity<Void> re = rest.exchange("/team/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Teste deletar id não existe")
	void delteIdNotFound() {
		ResponseEntity<Void> re = rest.exchange("/team/10", HttpMethod.DELETE, null, Void.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}

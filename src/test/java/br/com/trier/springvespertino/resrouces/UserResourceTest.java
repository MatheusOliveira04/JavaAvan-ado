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
import br.com.trier.springvespertino.config.jwt.LoginDTO;
import br.com.trier.springvespertino.models.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/resources/sqls/usuario.sql")
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {
	@Autowired
	protected TestRestTemplate rest;

	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("User1@gmail.com","111")), 
				UserDTO.class);
	}

	@SuppressWarnings("unused")
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(url,
				HttpMethod.GET, 
				new HttpEntity(getHeaders("User1@gmail.com", "111")), 
				new ParameterizedTypeReference<List<UserDTO>>() {} );
	}
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, header);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token",
				HttpMethod.POST, requestEntity, String.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		HttpHeaders header2 = new HttpHeaders();
		header2.setBearerAuth(responseEntity.getBody());
		return header2;
	}
	
	@Test
	@DisplayName("Teste buscar por id")
	public void testGetOk() {
		ResponseEntity<UserDTO> response = getUser("/users/3");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Test buscar por id inexistente")
	public void testGetNotFound() {
		ResponseEntity<UserDTO> response = getUser("/users/100");
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste buscar todos")
	public void testfindAll() {
		ResponseEntity<List<UserDTO>> responseEntity = getUsers("/users");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(4, responseEntity.getBody().get(1).getId());
	}
	
	@Test
	@DisplayName("Teste inserir usuário")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("User1@gmail.com", "111");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
	            "/users", 
	            HttpMethod.POST,  
	            requestEntity,    
	            UserDTO.class   
	    );
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

	}

	@Test
	@DisplayName("Teste inserir usuário")
	public void testCreateUserEmailDuplicate() {
		UserDTO dto = new UserDTO(null, "nome", "User1@gmail.com", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("User1@gmail.com", "111");
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users", 
				HttpMethod.POST,  
				requestEntity,    
				UserDTO.class   
				);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste atualiza")
	void testUpdate() {
		UserDTO dto = new UserDTO(null, "update", "update", "update", "ADMIN");
		HttpHeaders header = getHeaders("User1@gmail.com", "111");
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, header);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users/3", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste atualiza id não encontrado")
	void testUpdateIdNotFound() {
		UserDTO dto = new UserDTO(null, "update", "update", "update", "ADMIN");
		HttpHeaders header = getHeaders("User1@gmail.com", "111");
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, header);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users/100", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste atualiza com email duplucado")
	void testUpdateEmailDuplicate() {
		UserDTO dto = new UserDTO(null, "update", "User1@gmail.com", "update", "ADMIN");
		HttpHeaders header = getHeaders("User1@gmail.com", "111");
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, header);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/users/4", HttpMethod.PUT, requestEntity, UserDTO.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	@DisplayName("Teste deletar")
	void deleteTest() {
		HttpHeaders header = getHeaders("User1@gmail.com", "111");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, header);
		ResponseEntity<Void> responseEntity = rest.exchange("/users/3", HttpMethod.DELETE,
				requestEntity, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste deletar id não encontrado")
	void deleteIdNotFoundTest() {
		HttpHeaders header = getHeaders("User1@gmail.com", "111");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, header);
		ResponseEntity<Void> responseEntity = rest.exchange("/users/10", HttpMethod.DELETE,
				requestEntity, Void.class);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Buscar por email")
	void findByEmail() {
		ResponseEntity<UserDTO> dto = getUser("/users/email/User2@gmail.com");
		assertEquals(dto.getStatusCode(), HttpStatus.OK);
	}

	@Test
	@DisplayName("Buscar por email não existe")
	void findByEmailNotFound() {
		ResponseEntity<UserDTO> dto = getUser("/users/email/emailinexistente");
		assertEquals(dto.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Buscar por nome")
	void findByName() {
		ResponseEntity<UserDTO> dto = getUser("/users/name/User 1");
		assertEquals(dto.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por nome não existe")
	void findByNameNotFound() {
		ResponseEntity<UserDTO> dto = getUser("/users/name/nomeinexistente");
		assertEquals(dto.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Buscar por nome, que comece por uma letra")
	void findByNameStarting() {
		ResponseEntity<List<UserDTO>> dto = getUsers("/users/name/starting/U");
		assertEquals(dto.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Buscar por nome que comece por uma letra , que não existe")
	void findByNameStartingNotFound() {
		ResponseEntity<UserDTO> dto = getUser("/users/name/starting/a");
		assertEquals(dto.getStatusCode(), HttpStatus.NOT_FOUND);
	}
}
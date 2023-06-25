package br.com.trier.springvespertino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.UserService;
import br.com.trier.springvespertino.services.exceptions.IntegrityViolation;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService service;
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		User usuario = service.findById(1);
		assertNotNull(usuario);
	}
	
	@Test
	@DisplayName("Buscar por id inexistente")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdNonExistTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> service.findById(10));
		assertEquals("O usuário 10 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todo usuários sem cadastro")
	void ListAllEmptyTest() {
	var exception = assertThrows(ObjectNotFound.class, () -> service.listAll());
	assertEquals("Nenhum usuário cadastrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste inserir usuário com email duplicado")
	void insertUserDuplicatedEmailTest() {
		var user = new User(1, "Marcos" ,"User1@gmail.com", "12355");
		service.insert(user);
		var user2 = new User(2, "Lucas" ,"User1@gmail.com", "17777");
		var exception = assertThrows(IntegrityViolation.class,() -> service.insert(user2));
	}
}

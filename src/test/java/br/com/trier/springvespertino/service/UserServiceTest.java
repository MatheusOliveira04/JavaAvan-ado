package br.com.trier.springvespertino.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.BaseTests;
import br.com.trier.springvespertino.models.User;
import br.com.trier.springvespertino.services.UserService;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{
	
	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Buscar por id")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void findByIdTest() {
		User usuario = userService.findById(1);
		assertNotNull(usuario);
	}
	
	
}

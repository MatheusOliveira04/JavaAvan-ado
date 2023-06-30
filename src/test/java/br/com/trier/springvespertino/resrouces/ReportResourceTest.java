package br.com.trier.springvespertino.resrouces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.springvespertino.SpringVespertinoApplication;
import br.com.trier.springvespertino.models.dto.PilotRaceTeamDTO;
import br.com.trier.springvespertino.models.dto.RaceCountryYearDTO;
import br.com.trier.springvespertino.models.dto.SpeedwayChampionshipDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest(classes = SpringVespertinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportResourceTest {
	
	@Autowired
	TestRestTemplate rest;
	
	@Test
	@DisplayName("Teste buscar corrida por país e ano")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/speedway.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({	"classpath:/resources/sqls/race.sql"})
	void findRaceByCountryAndYear() {
		ResponseEntity<RaceCountryYearDTO> re = rest
				.getForEntity("/findBy/races-by-country-year/1/2020", RaceCountryYearDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK);
		RaceCountryYearDTO dto = re.getBody();
		assertEquals(dto.getCountry(), "Brazil");
	}
	
	@Test
	@DisplayName("Teste buscar corrida por país e ano, ano não encontrado ")
	
	void FindRaceByYearNotFound() {
		ResponseEntity<RaceCountryYearDTO> re = rest
				.getForEntity("/findBy/races-by-country-year/2/2015", RaceCountryYearDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	@DisplayName("Teste buscar corrida por país e ano, país não encontrado ")
	void FindRaceByCountryNotFound() {
		ResponseEntity<RaceCountryYearDTO> re = rest
				.getForEntity("/findBy/races-by-country-year/10/2020", RaceCountryYearDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste buscar corrida por time")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({	"classpath:/resources/sqls/team.sql"})
	@Sql({	"classpath:/resources/sqls/pilot.sql"})
	@Sql({"classpath:/resources/sqls/pilot_race.sql"})
	void findRaceByTeam() {
		 ResponseEntity<PilotRaceTeamDTO> re = rest
				 .getForEntity("/findBy/race-by-team/1", PilotRaceTeamDTO.class);
		 assertEquals(re.getStatusCode(), HttpStatus.OK);
		 PilotRaceTeamDTO dto = re.getBody();
		 assertEquals(dto.getTeam(), "Equipe 1");
	}
	
	@Test
	@DisplayName("Teste buscar corrida por time, time não encontrado ")
	void FindRaceByTeamNotFound() {
		ResponseEntity<PilotRaceTeamDTO> re = rest
				.getForEntity("/findBy/race-by-team/10", PilotRaceTeamDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	@DisplayName("Teste buscar todas corrida em um campeonato")
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findChampionshipByRace() {
		ResponseEntity<SpeedwayChampionshipDTO> re = rest
				.getForEntity("/findBy/speedway-by-championship/1", SpeedwayChampionshipDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.OK); 
	}
	
	@Test
	@DisplayName("Teste buscar todas corrida em um campeonato, campeonato não encontrado")
	void findChampionshipByRaceNotFound() {
		ResponseEntity<SpeedwayChampionshipDTO> re = rest
				.getForEntity("/findBy/speedway-by-championship/2", SpeedwayChampionshipDTO.class);
		assertEquals(re.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
}

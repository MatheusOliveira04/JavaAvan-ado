insert into country(id_country, name_country)
values (1,'Brazil');

insert into country(id_country, name_country)
values (2,'Alemanha');

insert into speedway(id_pista, nome_pista, tamanho_pista, country_id_country) values
(1, 'pista 1', 100,1);

insert into speedway(id_pista, nome_pista, tamanho_pista, country_id_country) 
values (2, 'pista 2', 200,1);

INSERT INTO championship
(id_championship, year_description, description_championship)
VALUES(1,2024, 'World Cup');

INSERT INTO championship
(id_championship, year_description, description_championship)
VALUES(2,2023, 'F1 Cup');

insert into race (id_race, date_race, speedway_id_pista, championship_id_championship)
values (1, '2023-06-21 12:34:56.123456', 1, 1);

insert into race (id_race, date_race, speedway_id_pista, championship_id_championship)
values (2, '2020-06-21 12:34:56.123456', 1, 1);

insert into team(id_team,name_team)values (1, 'Equipe 1');

insert into team(id_team, name_team) values (2, 'Equipe 2');

insert into pilot(id_pilot, name_pilot, country_id_country, team_id_team) 
values (1, 'Piloto 1', 1, 1);

insert into pilot(id_pilot, name_pilot, country_id_country, team_id_team) 
values (2, 'Piloto 2', 1, 1);

INSERT INTO pilot_race
(id_pilot_race ,colocacao_pilot_race, pilot_id_pilot, race_id_race)
VALUES(1,1, 1, 1);

INSERT INTO pilot_race
(id_pilot_race ,colocacao_pilot_race, pilot_id_pilot, race_id_race)
VALUES(2, 2, 2, 2);
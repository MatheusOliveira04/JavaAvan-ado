package br.com.trier.springvespertino.models.dto;

import java.util.List;

import br.com.trier.springvespertino.models.Speedway;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SpeedwayChampionshipDTO {

	private String campeonato;
	private Integer ano;
	private List<RaceDTO> speedway;
}

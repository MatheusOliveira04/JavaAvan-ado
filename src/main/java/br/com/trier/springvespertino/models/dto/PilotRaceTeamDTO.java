package br.com.trier.springvespertino.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PilotRaceTeamDTO {

	private String equipe;
	private Integer raceSize;
	private List<PilotRaceDTO> races;
}

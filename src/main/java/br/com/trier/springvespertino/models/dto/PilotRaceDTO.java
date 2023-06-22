package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PilotRaceDTO {

	private Integer id;
	private Integer colocacao;
	private Integer pilotId;
	private String pilotName;
	private Integer raceId;
	private String raceDate;
}

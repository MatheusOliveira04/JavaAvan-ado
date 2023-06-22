package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RaceDTO {

	private Integer id;
	private String date;
	private Integer speedwayId;
	private String speedwayName;
	private Integer championshipId;
	private String championshipName;
}

package br.com.trier.springvespertino.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotDTO {

	private Integer id;
	private String name;
	private Integer countryId;
	private String countryName;
	private Integer teamId;
	private String teamName;
}

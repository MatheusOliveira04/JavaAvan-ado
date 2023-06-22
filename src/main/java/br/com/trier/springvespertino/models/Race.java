package br.com.trier.springvespertino.models;

import java.time.ZonedDateTime;

import br.com.trier.springvespertino.models.dto.RaceDTO;
import br.com.trier.springvespertino.utils.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "race")
public class Race {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_race")
	private Integer id;
	
	@Column(name = "date_race")
	private ZonedDateTime date;
	
	@ManyToOne
	private Speedway speedway;
	
	@ManyToOne
	private Championship championship;
	
	public Race(RaceDTO raceDTO, Speedway speedway , Championship championship) {
		this(raceDTO.getId(), DateUtil.convertStringToZDT(raceDTO.getDate()),
				speedway, championship);
	}
	
	public RaceDTO toDTO() {
		return new RaceDTO(getId(), DateUtil.convertZDTToString(getDate()) ,
				getSpeedway().getId(),getSpeedway().getName(), 
				getChampionship().getId(), getChampionship().getDescription());
	}
}

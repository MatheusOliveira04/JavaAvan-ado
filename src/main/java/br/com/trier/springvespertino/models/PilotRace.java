package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.PilotRaceDTO;
import br.com.trier.springvespertino.utils.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "pilot_race")
public class PilotRace {
	
	@Column(name = "id_pilot_race")
	private Integer id;
	
	@Column(name = "colocacao_pilot_race")
	private Integer colocacao;
	
	@ManyToOne
	private Pilot pilot;
	
	@ManyToOne
	private Race race;
	
	public PilotRace(PilotRaceDTO dto, Pilot pilot, Race race) {
		this(dto.getId(), dto.getColocacao(), pilot, race);
	}
	
	public PilotRaceDTO toDTO() {
		return new PilotRaceDTO(getId(), getColocacao(), 
				getPilot().getId(), getPilot().getName(),
				getRace().getId(), DateUtil.convertZDTToString(getRace().getDate()));
	}
}

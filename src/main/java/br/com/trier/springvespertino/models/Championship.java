package br.com.trier.springvespertino.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "championship")
public class Championship {

	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_championship")
	private Integer id;
	
	@Column(name = "description_championship")
	private String description;
	
	@Column(name = "ano_description")
	private Integer ano;
}

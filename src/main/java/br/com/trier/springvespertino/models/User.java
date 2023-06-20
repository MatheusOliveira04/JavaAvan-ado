package br.com.trier.springvespertino.models;

import br.com.trier.springvespertino.models.dto.UserDTO;
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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity(name = "usuario")
public class User {

	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Integer id;
	
	@Column(name = "usuario_nome")
	private String name;
	
	@Column (name = "usuario_email", unique = true)
	private String email;
	
	@Column(name = "usuario_senha")
	private String password;
	
	public User(UserDTO dto) {
		this(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword());
	}
	
	public UserDTO toDto() {
		return new UserDTO(id, name, email, password);
	}
}

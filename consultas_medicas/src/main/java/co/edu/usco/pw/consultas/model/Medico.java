package co.edu.usco.pw.consultas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "medicos")
@Data
@Schema(description = "Medico de la clinica")
public class Medico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id del medico", example = "1")
	private Long id;

	@Column(nullable = false, length = 60)
	@Schema(description = "Nombre del medico", example = "Carlos Perez")
	private String nombre;

	@Column(length = 60)
	@Schema(description = "Especialidad", example = "Medicina general")
	private String especialidad;
}

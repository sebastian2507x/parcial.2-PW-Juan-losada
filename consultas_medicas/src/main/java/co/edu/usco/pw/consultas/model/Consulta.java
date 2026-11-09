package co.edu.usco.pw.consultas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "consultas")
@Data
@Schema(description = "Consulta medica")
public class Consulta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(description = "Id de la consulta", example = "1")
	private Long id;

	@Column(nullable = false, length = 40)
	@Schema(description = "Nombre del paciente, maximo 40 caracteres", example = "Ana Gomez")
	private String nombrePaciente;

	@Column(nullable = false, length = 100)
	@Schema(description = "Motivo de la consulta, maximo 100 caracteres", example = "Dolor de cabeza")
	private String motivo;

	@Schema(description = "Numero del consultorio", example = "101")
	private int consultorio;

	@Column(length = 5)
	@Schema(description = "Hora de inicio", example = "08:00")
	private String horaInicio;

	@Column(length = 5)
	@Schema(description = "Hora de finalizacion", example = "08:30")
	private String horaFin;

	@ManyToOne
	@JoinColumn(name = "medico_id")
	@Schema(description = "Medico asignado, solo se manda el id")
	private Medico medico;
}

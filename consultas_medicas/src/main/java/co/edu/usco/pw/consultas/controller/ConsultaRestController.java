package co.edu.usco.pw.consultas.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.usco.pw.consultas.model.Consulta;
import co.edu.usco.pw.consultas.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Servicios de las consultas medicas. Cada servicio dice que rol lo puede usar")
public class ConsultaRestController {

	@Autowired
	private ConsultaService consultaService;

	@Operation(summary = "Listar todas las consultas (ADMINISTRADOR)", description = "Devuelve todas las consultas registradas con su medico")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de consultas"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR") })
	@GetMapping
	public List<Consulta> listar() {
		return consultaService.listar();
	}

	@Operation(summary = "Registrar consulta (ADMINISTRADOR)", description = "Registra una consulta y le asigna consultorio y medico. "
			+ "Nombre del paciente maximo 40 caracteres, motivo maximo 100, consultorio numerico y hora fin mayor a hora inicio. "
			+ "Ejemplo: {\"nombrePaciente\": \"Ana Gomez\", \"motivo\": \"Control\", \"consultorio\": 101, "
			+ "\"horaInicio\": \"08:00\", \"horaFin\": \"08:30\", \"medico\": {\"id\": 1}}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Consulta registrada"),
			@ApiResponse(responseCode = "400", description = "Algun dato no cumple las reglas, el mensaje dice cual"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR") })
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Consulta consulta) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		consulta.setId(null);
		return ResponseEntity.ok(consultaService.guardar(consulta));
	}

	@Operation(summary = "Eliminar consulta (ADMINISTRADOR)", description = "Elimina una consulta por su id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Consulta eliminada"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe la consulta") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id) {
		if (consultaService.eliminar(id)) {
			return ResponseEntity.ok("Consulta eliminada");
		}
		return ResponseEntity.notFound().build();
	}

	@Operation(summary = "Ver mis consultas (MEDICO o PACIENTE)", description = "Si el usuario es MEDICO devuelve solo las consultas asignadas a el. "
			+ "Si es PACIENTE devuelve solo sus citas. Nunca devuelve consultas de otros.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Consultas del usuario que inicio sesion"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario es ADMINISTRADOR, debe usar listar todas") })
	@GetMapping("/mias")
	public List<Consulta> mias(Principal principal) {
		return consultaService.consultasDelUsuario(principal.getName());
	}

	@Operation(summary = "Cambiar horario de mi cita (PACIENTE)", description = "El paciente cambia la hora de inicio y fin de una cita que sea suya. "
			+ "Solo se tienen en cuenta horaInicio y horaFin. Ejemplo: {\"horaInicio\": \"09:00\", \"horaFin\": \"09:30\"}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Horario actualizado"),
			@ApiResponse(responseCode = "400", description = "Las horas no son validas"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "No es PACIENTE o la cita no es suya"),
			@ApiResponse(responseCode = "404", description = "No existe la consulta") })
	@PutMapping("/{id}/horario")
	public ResponseEntity<?> cambiarHorario(@Parameter(description = "Id de la consulta", example = "1") @PathVariable Long id,
			@RequestBody Consulta datos, Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (consulta == null) {
			return ResponseEntity.notFound().build();
		}
		if (!consultaService.esDelPaciente(consulta, principal.getName())) {
			return ResponseEntity.status(403).body("Esta cita no es suya");
		}
		String error = consultaService.validarHorario(datos.getHoraInicio(), datos.getHoraFin());
		if (error != null) {
			return ResponseEntity.badRequest().body(error);
		}
		return ResponseEntity.ok(consultaService.cambiarHorario(consulta, datos.getHoraInicio(), datos.getHoraFin()));
	}
}

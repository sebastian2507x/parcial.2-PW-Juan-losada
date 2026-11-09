package co.edu.usco.pw.consultas.controller;

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

import co.edu.usco.pw.consultas.model.Medico;
import co.edu.usco.pw.consultas.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Medicos", description = "Servicios para administrar los medicos. Solo el rol ADMINISTRADOR puede usarlos")
public class MedicoRestController {

	@Autowired
	private MedicoService medicoService;

	@Operation(summary = "Listar medicos", description = "Devuelve todos los medicos registrados en la base de datos. "
			+ "Es la misma lista que se usa en el campo de seleccion del formulario de consultas.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista de medicos"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR") })
	@GetMapping
	public List<Medico> listar() {
		return medicoService.listar();
	}

	@Operation(summary = "Buscar medico por id", description = "Devuelve un medico segun su id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Medico encontrado"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese id") })
	@GetMapping("/{id}")
	public ResponseEntity<Medico> buscar(@Parameter(description = "Id del medico", example = "1") @PathVariable Long id) {
		Medico medico = medicoService.buscar(id);
		if (medico == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(medico);
	}

	@Operation(summary = "Crear medico", description = "Registra un medico nuevo. No se manda el id porque lo genera la base de datos. "
			+ "Ejemplo: {\"nombre\": \"Carlos Perez\", \"especialidad\": \"Pediatria\"}")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Medico creado"),
			@ApiResponse(responseCode = "400", description = "Falta el nombre"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR") })
	@PostMapping
	public ResponseEntity<Medico> crear(@RequestBody Medico medico) {
		if (medico.getNombre() == null || medico.getNombre().trim().equals("")) {
			return ResponseEntity.badRequest().build();
		}
		medico.setId(null);
		return ResponseEntity.ok(medicoService.guardar(medico));
	}

	@Operation(summary = "Editar medico", description = "Cambia el nombre y la especialidad de un medico existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Medico actualizado"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese id") })
	@PutMapping("/{id}")
	public ResponseEntity<Medico> actualizar(@Parameter(description = "Id del medico a editar", example = "1") @PathVariable Long id,
			@RequestBody Medico medico) {
		Medico actualizado = medicoService.actualizar(id, medico);
		if (actualizado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(actualizado);
	}

	@Operation(summary = "Eliminar medico", description = "Elimina un medico. No se puede eliminar si tiene consultas o un usuario asignado")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Medico eliminado"),
			@ApiResponse(responseCode = "400", description = "El medico tiene consultas o usuario asignado"),
			@ApiResponse(responseCode = "401", description = "No ha iniciado sesion"),
			@ApiResponse(responseCode = "403", description = "El usuario no es ADMINISTRADOR"),
			@ApiResponse(responseCode = "404", description = "No existe un medico con ese id") })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminar(@Parameter(description = "Id del medico a eliminar", example = "1") @PathVariable Long id) {
		String resultado = medicoService.eliminar(id);
		if (resultado.equals("ok")) {
			return ResponseEntity.ok("Medico eliminado");
		}
		if (resultado.equals("no existe")) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.badRequest().body("No se puede eliminar, el medico " + resultado);
	}
}

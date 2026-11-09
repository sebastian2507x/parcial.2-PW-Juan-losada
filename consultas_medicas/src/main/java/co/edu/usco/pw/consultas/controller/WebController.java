package co.edu.usco.pw.consultas.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.usco.pw.consultas.model.Consulta;
import co.edu.usco.pw.consultas.model.Medico;
import co.edu.usco.pw.consultas.model.Usuario;
import co.edu.usco.pw.consultas.repository.UsuarioRepository;
import co.edu.usco.pw.consultas.service.ConsultaService;
import co.edu.usco.pw.consultas.service.MedicoService;

@Controller
public class WebController {

	@Autowired
	private MedicoService medicoService;

	@Autowired
	private ConsultaService consultaService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/denegado")
	public String denegado() {
		return "denegado";
	}

	@GetMapping("/")
	public String raiz() {
		return "redirect:/inicio";
	}

	@GetMapping("/inicio")
	public String inicio(Principal principal) {
		Usuario usuario = usuarioRepository.findByUsername(principal.getName()).orElse(null);
		if (usuario == null) {
			return "redirect:/login";
		}
		if (usuario.getRol().equals("ADMIN")) {
			return "redirect:/admin/medicos";
		}
		if (usuario.getRol().equals("MEDICO")) {
			return "redirect:/medico/consultas";
		}
		return "redirect:/paciente/consultas";
	}

	@GetMapping("/admin/medicos")
	public String medicos(Model model) {
		model.addAttribute("medicos", medicoService.listar());
		return "admin-medicos";
	}

	@PostMapping("/admin/medicos/guardar")
	public String guardarMedico(@ModelAttribute Medico medico) {
		if (medico.getNombre() == null || medico.getNombre().trim().equals("")) {
			return "redirect:/admin/medicos?error=El nombre es obligatorio";
		}
		medicoService.guardar(medico);
		return "redirect:/admin/medicos?ok=Medico guardado";
	}

	@GetMapping("/admin/medicos/editar/{id}")
	public String editarMedico(@PathVariable Long id, Model model) {
		Medico medico = medicoService.buscar(id);
		if (medico == null) {
			return "redirect:/admin/medicos?error=No existe el medico";
		}
		model.addAttribute("medico", medico);
		return "editar-medico";
	}

	@PostMapping("/admin/medicos/actualizar/{id}")
	public String actualizarMedico(@PathVariable Long id, @ModelAttribute Medico medico) {
		medicoService.actualizar(id, medico);
		return "redirect:/admin/medicos?ok=Medico actualizado";
	}

	@GetMapping("/admin/medicos/eliminar/{id}")
	public String eliminarMedico(@PathVariable Long id) {
		String resultado = medicoService.eliminar(id);
		if (resultado.equals("ok")) {
			return "redirect:/admin/medicos?ok=Medico eliminado";
		}
		return "redirect:/admin/medicos?error=No se puede eliminar, el medico " + resultado;
	}

	@GetMapping("/admin/consultas")
	public String consultas(Model model) {
		model.addAttribute("consultas", consultaService.listar());
		model.addAttribute("medicos", medicoService.listar());
		return "admin-consultas";
	}

	@PostMapping("/admin/consultas/guardar")
	public String guardarConsulta(@ModelAttribute Consulta consulta, Model model) {
		String error = consultaService.validar(consulta);
		if (error != null) {
			model.addAttribute("error", error);
			model.addAttribute("consultas", consultaService.listar());
			model.addAttribute("medicos", medicoService.listar());
			return "admin-consultas";
		}
		consultaService.guardar(consulta);
		return "redirect:/admin/consultas?ok=Consulta registrada";
	}

	@GetMapping("/admin/consultas/eliminar/{id}")
	public String eliminarConsulta(@PathVariable Long id) {
		consultaService.eliminar(id);
		return "redirect:/admin/consultas?ok=Consulta eliminada";
	}

	@GetMapping("/medico/consultas")
	public String consultasMedico(Principal principal, Model model) {
		model.addAttribute("consultas", consultaService.consultasDelUsuario(principal.getName()));
		return "medico-consultas";
	}

	@GetMapping("/paciente/consultas")
	public String consultasPaciente(Principal principal, Model model) {
		model.addAttribute("consultas", consultaService.consultasDelUsuario(principal.getName()));
		return "paciente-consultas";
	}

	@PostMapping("/paciente/horario/{id}")
	public String cambiarHorario(@PathVariable Long id, @RequestParam String horaInicio,
			@RequestParam String horaFin, Principal principal) {
		Consulta consulta = consultaService.buscar(id);
		if (!consultaService.esDelPaciente(consulta, principal.getName())) {
			return "redirect:/denegado";
		}
		String error = consultaService.validarHorario(horaInicio, horaFin);
		if (error != null) {
			return "redirect:/paciente/consultas?error=" + error;
		}
		consultaService.cambiarHorario(consulta, horaInicio, horaFin);
		return "redirect:/paciente/consultas?ok=Horario actualizado";
	}
}

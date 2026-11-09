package co.edu.usco.pw.consultas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.consultas.model.Consulta;
import co.edu.usco.pw.consultas.model.Medico;
import co.edu.usco.pw.consultas.model.Usuario;
import co.edu.usco.pw.consultas.repository.ConsultaRepository;
import co.edu.usco.pw.consultas.repository.MedicoRepository;
import co.edu.usco.pw.consultas.repository.UsuarioRepository;

@Service
public class ConsultaService {

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Consulta> listar() {
		return consultaRepository.findAll();
	}

	public Consulta buscar(Long id) {
		return consultaRepository.findById(id).orElse(null);
	}

	public String validar(Consulta consulta) {
		if (consulta.getNombrePaciente() == null || consulta.getNombrePaciente().trim().equals("")) {
			return "El nombre del paciente es obligatorio";
		}
		if (consulta.getNombrePaciente().length() > 40) {
			return "El nombre del paciente no puede tener mas de 40 caracteres";
		}
		if (consulta.getMotivo() == null || consulta.getMotivo().trim().equals("")) {
			return "El motivo es obligatorio";
		}
		if (consulta.getMotivo().length() > 100) {
			return "El motivo no puede tener mas de 100 caracteres";
		}
		if (consulta.getConsultorio() <= 0) {
			return "El consultorio debe ser un numero mayor a 0";
		}
		String error = validarHorario(consulta.getHoraInicio(), consulta.getHoraFin());
		if (error != null) {
			return error;
		}
		if (consulta.getMedico() == null || consulta.getMedico().getId() == null
				|| !medicoRepository.existsById(consulta.getMedico().getId())) {
			return "Debe escoger un medico que exista";
		}
		return null;
	}

	public String validarHorario(String inicio, String fin) {
		if (inicio == null || fin == null || inicio.equals("") || fin.equals("")) {
			return "La hora de inicio y fin son obligatorias";
		}
		if (fin.compareTo(inicio) <= 0) {
			return "La hora de fin debe ser despues de la hora de inicio";
		}
		return null;
	}

	public Consulta guardar(Consulta consulta) {
		Medico medico = medicoRepository.findById(consulta.getMedico().getId()).orElse(null);
		consulta.setMedico(medico);
		return consultaRepository.save(consulta);
	}

	public boolean eliminar(Long id) {
		if (consultaRepository.existsById(id)) {
			consultaRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public List<Consulta> consultasDelUsuario(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
		if (usuario == null) {
			return new ArrayList<>();
		}
		if (usuario.getRol().equals("MEDICO")) {
			if (usuario.getMedico() == null) {
				return new ArrayList<>();
			}
			return consultaRepository.findByMedico(usuario.getMedico());
		}
		if (usuario.getRol().equals("PACIENTE")) {
			return consultaRepository.findByNombrePaciente(usuario.getNombre());
		}
		return new ArrayList<>();
	}

	public boolean esDelPaciente(Consulta consulta, String username) {
		Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
		if (usuario == null || consulta == null) {
			return false;
		}
		return consulta.getNombrePaciente().equalsIgnoreCase(usuario.getNombre());
	}

	public Consulta cambiarHorario(Consulta consulta, String inicio, String fin) {
		consulta.setHoraInicio(inicio);
		consulta.setHoraFin(fin);
		return consultaRepository.save(consulta);
	}
}

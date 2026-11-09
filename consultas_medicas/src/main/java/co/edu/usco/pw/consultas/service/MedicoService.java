package co.edu.usco.pw.consultas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.usco.pw.consultas.model.Medico;
import co.edu.usco.pw.consultas.repository.ConsultaRepository;
import co.edu.usco.pw.consultas.repository.MedicoRepository;
import co.edu.usco.pw.consultas.repository.UsuarioRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private ConsultaRepository consultaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Medico> listar() {
		return medicoRepository.findAll();
	}

	public Medico buscar(Long id) {
		return medicoRepository.findById(id).orElse(null);
	}

	public Medico guardar(Medico medico) {
		return medicoRepository.save(medico);
	}

	public Medico actualizar(Long id, Medico datos) {
		Medico medico = buscar(id);
		if (medico == null) {
			return null;
		}
		medico.setNombre(datos.getNombre());
		medico.setEspecialidad(datos.getEspecialidad());
		return medicoRepository.save(medico);
	}

	public String eliminar(Long id) {
		Medico medico = buscar(id);
		if (medico == null) {
			return "no existe";
		}
		if (consultaRepository.existsByMedico(medico) || usuarioRepository.existsByMedico(medico)) {
			return "tiene consultas o usuario";
		}
		medicoRepository.deleteById(id);
		return "ok";
	}
}

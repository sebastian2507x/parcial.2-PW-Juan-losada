package co.edu.usco.pw.consultas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.usco.pw.consultas.model.Consulta;
import co.edu.usco.pw.consultas.model.Medico;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

	List<Consulta> findByMedico(Medico medico);

	List<Consulta> findByNombrePaciente(String nombrePaciente);

	boolean existsByMedico(Medico medico);
}

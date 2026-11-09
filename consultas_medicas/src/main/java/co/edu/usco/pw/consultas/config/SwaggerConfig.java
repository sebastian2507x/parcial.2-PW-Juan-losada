package co.edu.usco.pw.consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI miOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API Consultas Medicas")
						.version("1.0.0")
						.description("Servicios para gestionar medicos y consultas medicas. "
								+ "Esta documentacion solo la puede ver el ADMINISTRADOR, se entra desde el menu de administrador. "
								+ "Sin sesion los servicios responden 401 y con un rol sin permiso responden 403.")
						.contact(new Contact().name("Juan Sebastian Losada").email("estudiante@usco.edu.co")));
	}
}

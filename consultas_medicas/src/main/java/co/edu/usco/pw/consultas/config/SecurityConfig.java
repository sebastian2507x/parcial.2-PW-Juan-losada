package co.edu.usco.pw.consultas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
						.requestMatchers("/login", "/error", "/denegado").permitAll()
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").hasRole("ADMIN")
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/medico/**").hasRole("MEDICO")
						.requestMatchers("/paciente/**").hasRole("PACIENTE")
						.requestMatchers(HttpMethod.GET, "/api/consultas/mias").hasAnyRole("MEDICO", "PACIENTE")
						.requestMatchers(HttpMethod.PUT, "/api/consultas/*/horario").hasRole("PACIENTE")
						.requestMatchers("/api/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/inicio", true)
						.failureUrl("/login?error")
						.permitAll())
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?salio"))
				.exceptionHandling(e -> e
						.authenticationEntryPoint((request, response, ex) -> {
							if (request.getRequestURI().startsWith("/api")) {
								response.sendError(401, "No ha iniciado sesion");
							} else {
								response.sendRedirect("/login");
							}
						})
						.accessDeniedHandler((request, response, ex) -> {
							if (request.getRequestURI().startsWith("/api")) {
								response.sendError(403, "No tiene permiso");
							} else {
								response.sendRedirect("/denegado");
							}
						}));

		return http.build();
	}
}

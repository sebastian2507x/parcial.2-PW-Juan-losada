# parcial.2-PW-Juan-losada
Segundo Parcial Programación Web
# Consultas Medicas - Parcial 2 Programacion Web

Aplicacion web para gestionar consultas medicas con los roles ADMINISTRADOR, MEDICO y PACIENTE como se estipulo en clase.

## Tecnologias

- Java 21, Spring Boot 3.5.7
- Spring Data JPA + MySQL
- Spring Security (login propio, roles, BCrypt)
- JSP + JSTL + Bootstrap 5
- Swagger

## Como ejecutar

1. Ejecutar `script.sql` en MySQL (crea la base de datos `consultas_medicas`, las tablas y los datos de prueba del sistema).
2. Revisar usuario y clave de MySQL en `src/main/resources/application.properties` (root / 1234).
3. Abrir http://localhost:8080
4. si se usa VS code ejecutar estos comandos en la terminal, los cuales cambian la version de java actual a java 21 y corre el springboot:
$env:JAVA_HOME = (Get-ChildItem "C:\Program Files\Eclipse Adoptium\jdk-21*").FullName
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
java -version
.\mvnw.cmd spring-boot:run

## Usuarios de prueba

| Usuario | Clave | Rol | Observacion |
|---|---|---|---|
| admin | admin123 | ADMIN | crea, edita y elimina medicos y registra consultas |
| medico1 | medico123 | MEDICO | Santiago valencia, solo ve las consultas de el |
| medico2 | medico123 | MEDICO | Isabella pino, solo ve las consultas de ella |
| paciente1 | paciente123 | PACIENTE | Ana arce, solo ve y actualiza horario |
| paciente2 | paciente123 | PACIENTE | Luis trujillo, solo ve y actualiza horario |

Las claves estan guardadas con BCrypt en la tabla `usuarios`.

## Paginas

| Ruta | Rol o permiso de acceso |
|---|---|
| /login | todos |
| /admin/medicos | ADMIN |
| /admin/consultas | ADMIN |
| /medico/consultas | MEDICO |
| /paciente/consultas | PACIENTE |
| /denegado | se muestra cuando el usuario no tiene permiso para estar en esa ruta |

## Servicios web (Swagger)

http://localhost:8080/swagger-ui/index.html

Solo el ADMINISTRADOR puede abrir Swagger. Se inicia sesion con `admin` y se entra desde el enlace "Documentacion API (Swagger)" del menu de administrador. Si otro rol intenta abrirlo ve la pagina de acceso restringido.

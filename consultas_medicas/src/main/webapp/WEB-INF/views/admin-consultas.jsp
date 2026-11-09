<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Consultas</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<a href="/admin/medicos">Medicos</a> | <a href="/admin/consultas">Consultas</a> | <a href="/swagger-ui/index.html">Documentacion API (Swagger)</a>

	<h1>Consultas</h1>

	<c:if test="${param.ok != null}">
		<p>${param.ok}</p>
	</c:if>
	<c:if test="${error != null}">
		<p>${error}</p>
	</c:if>

	<h2>Registrar consulta</h2>
	<form action="/admin/consultas/guardar" method="post">
		Nombre del paciente: <input type="text" name="nombrePaciente" maxlength="40" required /><br /><br />
		Motivo de la consulta: <input type="text" name="motivo" maxlength="100" size="60" required /><br /><br />
		Numero de consultorio: <input type="number" name="consultorio" min="1" required /><br /><br />
		Hora inicio: <input type="time" name="horaInicio" required />
		Hora fin: <input type="time" name="horaFin" required /><br /><br />
		Medico:
		<select name="medico.id" required>
			<option value="">Seleccione...</option>
			<c:forEach var="m" items="${medicos}">
				<option value="${m.id}">${m.nombre}</option>
			</c:forEach>
		</select><br /><br />
		<input type="submit" value="Guardar" />
	</form>

	<h2>Lista de consultas</h2>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Paciente</th>
			<th>Motivo</th>
			<th>Consultorio</th>
			<th>Hora inicio</th>
			<th>Hora fin</th>
			<th>Medico</th>
			<th>Accion</th>
		</tr>
		<c:forEach var="c" items="${consultas}">
			<tr>
				<td>${c.id}</td>
				<td>${c.nombrePaciente}</td>
				<td>${c.motivo}</td>
				<td>${c.consultorio}</td>
				<td>${c.horaInicio}</td>
				<td>${c.horaFin}</td>
				<td>${c.medico.nombre}</td>
				<td><a href="/admin/consultas/eliminar/${c.id}">Eliminar</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>

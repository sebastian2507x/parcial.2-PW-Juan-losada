<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis consultas</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<h1>Mis consultas asignadas</h1>

	<c:if test="${empty consultas}">
		<p>No tiene consultas asignadas</p>
	</c:if>

	<table border="1">
		<tr>
			<th>Paciente</th>
			<th>Motivo</th>
			<th>Consultorio</th>
			<th>Hora inicio</th>
			<th>Hora fin</th>
		</tr>
		<c:forEach var="c" items="${consultas}">
			<tr>
				<td>${c.nombrePaciente}</td>
				<td>${c.motivo}</td>
				<td>${c.consultorio}</td>
				<td>${c.horaInicio}</td>
				<td>${c.horaFin}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>

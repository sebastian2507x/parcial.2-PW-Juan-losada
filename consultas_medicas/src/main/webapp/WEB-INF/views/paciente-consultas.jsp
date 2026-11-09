<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mis citas</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<h1>Mis citas</h1>

	<c:if test="${param.ok != null}">
		<p>${param.ok}</p>
	</c:if>
	<c:if test="${param.error != null}">
		<p>${param.error}</p>
	</c:if>
	<c:if test="${empty consultas}">
		<p>No tiene citas asignadas</p>
	</c:if>

	<table border="1">
		<tr>
			<th>Motivo</th>
			<th>Consultorio</th>
			<th>Medico</th>
			<th>Horario</th>
		</tr>
		<c:forEach var="c" items="${consultas}">
			<tr>
				<td>${c.motivo}</td>
				<td>${c.consultorio}</td>
				<td>${c.medico.nombre}</td>
				<td>
					<form action="/paciente/horario/${c.id}" method="post">
						Inicio: <input type="time" name="horaInicio" value="${c.horaInicio}" required />
						Fin: <input type="time" name="horaFin" value="${c.horaFin}" required />
						<input type="submit" value="Actualizar" />
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>

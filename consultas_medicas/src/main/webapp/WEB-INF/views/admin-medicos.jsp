<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Medicos</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<a href="/admin/medicos">Medicos</a> | <a href="/admin/consultas">Consultas</a> | <a href="/swagger-ui/index.html">Documentacion API (Swagger)</a>

	<h1>Medicos</h1>

	<c:if test="${param.ok != null}">
		<p>${param.ok}</p>
	</c:if>
	<c:if test="${param.error != null}">
		<p>${param.error}</p>
	</c:if>

	<h2>Nuevo medico</h2>
	<form action="/admin/medicos/guardar" method="post">
		Nombre: <input type="text" name="nombre" maxlength="60" required /><br /><br />
		Especialidad: <input type="text" name="especialidad" maxlength="60" /><br /><br />
		<input type="submit" value="Guardar" />
	</form>

	<h2>Lista de medicos</h2>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>Nombre</th>
			<th>Especialidad</th>
			<th>Acciones</th>
		</tr>
		<c:forEach var="m" items="${medicos}">
			<tr>
				<td>${m.id}</td>
				<td>${m.nombre}</td>
				<td>${m.especialidad}</td>
				<td><a href="/admin/medicos/editar/${m.id}">Editar</a> | <a href="/admin/medicos/eliminar/${m.id}">Eliminar</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>

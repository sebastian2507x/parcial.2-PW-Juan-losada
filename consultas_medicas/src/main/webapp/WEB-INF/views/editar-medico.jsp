<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar medico</title>
</head>
<body>
	<%@ include file="menu.jsp"%>
	<h1>Editar medico</h1>
	<form action="/admin/medicos/actualizar/${medico.id}" method="post">
		Nombre: <input type="text" name="nombre" value="${medico.nombre}" maxlength="60" required /><br /><br />
		Especialidad: <input type="text" name="especialidad" value="${medico.especialidad}" maxlength="60" /><br /><br />
		<input type="submit" value="Actualizar" />
	</form>
	<br />
	<a href="/admin/medicos">Cancelar</a>
</body>
</html>

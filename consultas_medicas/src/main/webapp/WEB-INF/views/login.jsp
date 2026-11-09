<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar sesion</title>
</head>
<body>
	<h1>Consultas Medicas</h1>
	<h2>Iniciar sesion</h2>

	<% if (request.getParameter("error") != null) { %>
	<p>Usuario o contraseña incorrectos</p>
	<% } %>
	<% if (request.getParameter("salio") != null) { %>
	<p>Cerro sesion correctamente</p>
	<% } %>

	<form action="/login" method="post">
		Usuario: <input type="text" name="username" required /><br /><br />
		Contraseña: <input type="password" name="password" required /><br /><br />
		<input type="submit" value="Ingresar" />
	</form>
</body>
</html>

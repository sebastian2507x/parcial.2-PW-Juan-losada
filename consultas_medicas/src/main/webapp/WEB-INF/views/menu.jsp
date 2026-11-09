<form action="/logout" method="post">
	Usuario: ${pageContext.request.userPrincipal.name}
	<input type="submit" value="Cerrar sesion" />
</form>
<hr />

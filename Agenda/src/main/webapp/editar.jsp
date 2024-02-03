<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda de contatos</title>
<link rel="icon" href="imagens/phone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Editar contato</h1>
	<form name="frmContato" action="update">
		<table>
			<tr>
				<td><input type="text" name="idcon" placeholder="Id" id="caixa3" readonly value="<%out.print(request.getAttribute("idcon"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" placeholder="Nome" class="caixa-1" value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="email" placeholder="Email" class="caixa-1" value="<%out.print(request.getAttribute("email"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="fone" placeholder="Fone" class="caixa-2" value="<%out.print(request.getAttribute("fone"));%>"></td>
			</tr>
		</table>
		<input type="button" value="Salvar" class="bt-access" onclick="validar()">
	</form>
</body>

<script src="scripts/validador.js"></script>

</html>
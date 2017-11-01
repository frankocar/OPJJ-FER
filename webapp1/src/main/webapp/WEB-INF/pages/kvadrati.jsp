<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table>
	<tr><th>Broj</th><th>Njegov kvadrat</th></tr>
	<c:forEach var="par" items="${listaKvadrata}">
		<tr><td>${par.broj}</td><td>${par.kvadrat}</td></tr>
	</c:forEach>
	
</table>
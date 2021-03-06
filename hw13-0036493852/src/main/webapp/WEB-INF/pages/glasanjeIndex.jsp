<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="band" class="hr.fer.zemris.java.voting.Band" scope="request" />

<html>
<style type="text/css">
body {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	background-color: ${pickedBgCol};
}
</style>
<body>
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<c:forEach var="band" items="${bands}">
			<li><a href="glasanje-glasaj?id=${band.key}">${band.value.name}</a></li>
		</c:forEach>
	</ol>
	<p>
		<a href="index.jsp">Povratak na početnu stranicu</a><br>
	</p>
</body>
</html>


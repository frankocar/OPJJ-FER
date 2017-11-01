<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="poll" class="hr.fer.zemris.java.hw14.model.Poll" scope="request" />

<html>
<style type="text/css">
body {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
}
</style>
<body>
	<h1>Available polls:</h1>
	<ol>
		<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>

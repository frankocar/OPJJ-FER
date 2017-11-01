<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="pollOption" class="hr.fer.zemris.java.hw14.model.PollOption" scope="request" />

<html>
<style type="text/css">
body {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
}
</style>
<body>
	<h1>${title}</h1>
	<p>${message}</p>
	<ol>
		<c:forEach var="option" items="${options}">
			<li><a href="glasanje-glasaj?optionID=${option.id}">${option.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>

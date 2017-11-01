<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body bgColor="${pickedBgCol}">
	<table>
		<tr>
			<th>Angle</th>
			<th>Sine</th>
			<th>Cosine</th>
		</tr>
		<c:forEach var="row" items="${listOfTrigValues}">
			<tr>
				<td>${row.number}</td>
				<td>${row.sine}</td>
				<td>${row.cosine}</td>
			</tr>
		</c:forEach>
	</table>

	<p>
		<a href="index.jsp">Back to homepage</a><br>
	</p>
</body>
</html>
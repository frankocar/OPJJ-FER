<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<style type="text/css">
table.rez td, th {
	text-align: center;
	padding-bottom: 2px;
	padding-top: 2px;
	padding-left: 5px;
	padding-right: 5px;
}
</style>
</head>
<body bgColor="${pickedBgCol}">

	<h1>Trigonometry</h1>
	<p>Sine and cosine values</p>

	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Angle</th>
				<th>Sine</th>
				<th>Cosine</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${listOfTrigValues}">
				<tr>
					<td>${row.number}</td>
					<td><fmt:formatNumber value="${row.sine}" maxFractionDigits="6" /></td>
					<td><fmt:formatNumber value="${row.cosine}" maxFractionDigits="6" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<p>
		<a href="index.jsp">Back to homepage</a><br>
	</p>
</body>
</html>
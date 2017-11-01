<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="band" class="hr.fer.zemris.java.voting.Band" scope="request" />

<html>
<head>
<style type="text/css">
table.rez td {
	border-collapse: collapse;
	width: 40%;
}

table.rez th, td {
	padding: 8px;
	border-bottom: 1px solid #ddd;
	text-align: center;
}

table.rez tr:hover {
	background-color: #f5f5f5
}

body {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	background-color: ${pickedBgCol};
}

</style>
</head>
<body>

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table class="rez">
		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="band" items="${results}">
				<tr>
					<td>${band.name}</td>
					<td>${band.votes}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src=glasanje-grafika width="600" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href=glasanje-xls>ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
		<c:forEach var="band" items="${theBest}">
			<li><a href="${band.exampleLink}" target="_blank">${band.name}</a></li>
		</c:forEach>
	</ul>
	<p>
		<a href="glasanje">Povratak na glasanje</a><br>
	</p>
	<p>
		<a href="index.jsp">Povratak na početnu stranicu</a><br>
	</p>
</body>
</html>
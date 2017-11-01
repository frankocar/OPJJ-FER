<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:useBean id="pollOption" class="hr.fer.zemris.java.hw14.model.PollOption" scope="request" />

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

	<h1>Results</h1>
	<p>Each option got the following number of votes: </p>
	<table class="rez">
		<thead>
			<tr>
				<th>Option</th>
				<th>Votes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="poll" items="${results}">
				<tr>
					<td>${poll.optionTitle}</td>
					<td>${poll.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h2>Graphical representation</h2>
	<img alt="Pie-chart" src=glasanje-grafika width="600" height="400" />
	<h2>Results in xls format</h2>
	<p>
		Results in xls format are available <a href=glasanje-xls>Here</a>
	</p>

	<h2>Various</h2>
	<p>Examples of the winning options:</p>
	<ul>
		<c:forEach var="poll" items="${theBest}">
			<li><a href="${poll.optionLink}" target="_blank">${poll.optionTitle}</a></li>
		</c:forEach>
	</ul>
	<p>
		<a href="index.html">Return to homepage</a><br>
	</p>
</body>
</html>
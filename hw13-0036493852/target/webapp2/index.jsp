<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<html>
	<head>
		<meta charset="utf-8">
		<title>Homepage</title>
	</head>
	<body bgColor="${pickedBgCol}">
		<h1>Homepage</h1>
		<p>
			<a href="setcolor">Background color chooser</a><br>
			<a href="trigonometric?a=0&b=90">Trigonometric function table</a><br>
			<a href="funny">Funny story</a><br>
			<a href="usageChart">OS Usage Chart</a><br>
			<a href="powers?a=1&b=100&n=3">XLS powers table</a><br>
			<a href="appinfo">Server uptime</a><br>
			<a href="glasanje">Best band survey</a><br>
		</p>		
		<form action="trigonometric" method="GET">
 			Start angle:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 			End angle:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 			<input type="submit" value="Create table"><input type="reset" value="Reset">
		</form>
		
	</body>
</html>
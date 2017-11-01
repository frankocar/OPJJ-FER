<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">

body, textarea {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	margin: auto;
    width: 60%;
    padding: 10px;
}

.right {
    float:right;
    right: 100px;
    width: 300px;
    padding: 10px;
    text-align: left;
}

.home a:link, .home a:visited {
    background-color: #99ccff;
    color: white;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    float: right;
    margin-right: 20em;
}

.home a:hover{
    background-color: #0099cc;
}

p.solid {
    outline-color: #99ccff;
	outline-style: solid;
	width: 50%;
}

input[type=text], textarea[type=textarea], select {
    width: 40%;
    padding: 12px 20px;
    margin: auto;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit] {
    width: 40%;
    background-color: #ff9933;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover {
    background-color: #e67300;
}

</style>
</head>
<body>

	<div class="right">
		<c:choose>
			<c:when test="${empty sessionScope['current.user.id']}">
				<p>Not logged in.</p>
			</c:when>	
			<c:otherwise>
				<p>
					Hello, ${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']}!<br> 
					<a href="/blog/servlets/logout">Logout</a><br>
				</p>
			</c:otherwise>	
		</c:choose>
	</div>

	<br><br><br>

	<c:choose>
		<c:when test="${nick == sessionScope['current.user.nick']}">
			<form method="POST" action="/blog/servlets/editor">
				<input type="hidden" name="key" value="${entry.id}"> 
				Title: <br><input type="text" name="title" value="${entry.title}"> <br> 
				Text: <br><textarea type="textarea" name="text">${entry.text}</textarea><br>
				<input type="submit">
			</form>
		</c:when>	
		<c:otherwise>
			Please log in as ${nick} to access this feature.<br>
			<a href="/blog/">Home</a>
		</c:otherwise>
	</c:choose>
	
</body>
</html>
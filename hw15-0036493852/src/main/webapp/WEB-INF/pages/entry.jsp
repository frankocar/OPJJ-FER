<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">

body {
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

.edit a:link, .edit a:visited {
    background-color: #99ccff;
    color: white;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    float: left;
}

.edit a:hover{
    background-color: #0099cc;
}

p.solid {
    outline-color: #99ccff;
	outline-style: solid;
	width: 50%;
}

input[type=text], select {
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

	<br><br>

	<h1>${entry.title}</h1>
	
	<p>${entry.text}</p>
	
	<p><i>Date: ${entry.createdAt}</i></p>
	
	<hr> 
	
	<h3>Comments:</h3>
	<c:forEach items="${entry.comments}" var="comment">
			<p class="solid">
				<b>Message: ${comment.message}</b><br>
				User: ${comment.usersEMail}<br>
				Date: ${comment.postedOn}<br>
			</p>
	</c:forEach>
	
	<c:choose>
		<c:when test="${!empty sessionScope['current.user.nick']}">
			<form method="POST" action="/blog/servlets/comment">
				<input type="hidden" name="user" value="${sessionScope['current.user.nick']}"> 
				<input type="hidden" name="entryID" value="${entry.id}">
				Comment: <br><input type="text" name="message"> <br> 
				<input type="submit">
			</form>
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${nick == sessionScope['current.user.nick']}">
				<div class="edit"><a href="edit?eid=${entry.id}">Edit post</a></div>
		</c:when>
	</c:choose>
	
	<div class="home">
		<a href="/blog/">Home</a>
	</div>
</body>
</html>
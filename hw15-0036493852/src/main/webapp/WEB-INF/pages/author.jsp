<!DOCTYPE html>
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

ol {
    counter-reset: li;
    list-style: none;
    padding: 0;
    margin-bottom: 4em;
    text-shadow: 0 1px 0 rgba(255,255,255,.5);
}

.rectangle-list a{
    position: relative;
    display: block;
    padding: .4em .4em .4em .8em;
    margin: .5em 20em .5em 1em;
    background: #99ccff;
    color: #444;
    text-decoration: none;  
}

.home a:link {
    background-color: #99ccff;
    color: black;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    float:right;    
    margin-right: 20em;
}

.home a:hover{
    background-color: #0099cc;
}

.new a:link{
    background-color: #ff9933;
    color: black;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    float: left;    
    margin-right: 20em;   
    margin-left: 1em;
}

.new a:hover {
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

	<h2>Entries by ${nick}:</h2>

	<p>	<ol class="rectangle-list">
		<c:forEach items="${blogEntries}" var="entry">
			<li><a href="${nick}/${entry.id}">${entry.title}</a></li>
		</c:forEach>
	</ol>
	</p>

	<div class="new">
		<c:choose>
			<c:when test="${nick == sessionScope['current.user.nick']}">
				<a href="${nick}/new">New entry</a>
			</c:when>	
		</c:choose>
	</div>
	
	<div class="home">
		<a href="/blog/">Home</a>
	</div>
</body>
</html>
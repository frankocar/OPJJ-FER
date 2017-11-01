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
	
	<h1>Error</h1>
	
	<p>${errorMsg}</p>
	
	<a href="/blog/">Home</a>
	
</body>
</html>
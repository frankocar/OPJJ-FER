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

.error {
	color: red;
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

input[type=text], input[type=password], select {
    width: 60%;
    padding: 12px 20px;
    margin: auto;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit], button[type=button] {
    width: 60%;
    background-color: #ff9933;
    color: white;
    padding: 14px 20px;
    margin: 8px 0;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

input[type=submit]:hover, button[type=button]:hover {
    background-color: #e67300;
}

.registerBtn a:link, .registerBtn a:visited {
	color: white;	
    text-decoration: none;  
}

.middle {
    width: 50%;
    margin: auto;
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

	<div class="middle">
		<c:choose>
		<c:when test="${empty sessionScope['current.user.id']}">
			<form action="login" method="POST">

				Username: <br><input type="text" name="user"
					value='<c:out value="${form.nick}"/>' size="5"><br>
				<c:if test="${form.hasError('nick')}">
					<div class="error">
						<c:out value="${form.getError('nick')}" />
					</div>
				</c:if>
				
				Password: <br><input type="password" name="pass"
					value='<c:out value=""/>' size="5"><br>
				<c:if test="${form.hasError('password')}">
					<div class="error">
						<c:out value="${form.getError('password')}" />
					</div>
				</c:if>				
				<input type="submit" value="Sign in">
				<button type="button" value="Sign Up"><div class="registerBtn"><a href="register">Sign up</a></div></button>
			</form>
			<br><br><br>		
		</c:when>	
		<c:otherwise>
			<h2>Hello, ${sessionScope['current.user.fn']}</h2>
		</c:otherwise>	
	</c:choose>
	</div>
	
	<p>Users:
	<ol class="rectangle-list">
		<c:forEach items="${users}" var="user">
			<li><a href="author/${user.nick}">${user.nick}</a></li>
		</c:forEach>
	</ol>
	</p>

</body>
</html>
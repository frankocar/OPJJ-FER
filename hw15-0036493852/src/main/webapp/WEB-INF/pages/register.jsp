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

input[type=text], input[type=password], select {
    width: 100%;
    padding: 12px 20px;
    margin: auto;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
}

input[type=submit], button[type=button] {
    width: 100%;
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

.home a:link, .home a:visited {
    background-color: #99ccff;
    color: white;
    padding: 14px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    float:right;
}

.home a:hover{
    background-color: #0099cc;
}

</style>
</head>
<body>

	<c:choose>
		<c:when test="${empty sessionScope['current.user.id']}">
			<form action="register" method="POST">

				First Name: <br><input type="text" name="firstName"
					value='<c:out value="${form.firstName}"/>' size="5"><br>
				<c:if test="${form.hasError('firstName')}">
					<div class="error">
						<c:out value="${form.getError('firstName')}" />
					</div>
				</c:if>
				
				Last Name: <br><input type="text" name="lastName"
					value='<c:out value="${form.lastName}"/>' size="5"><br>
				<c:if test="${form.hasError('lastName')}">
					<div class="error">
						<c:out value="${form.getError('lastName')}" />
					</div>
				</c:if>
			
				Email: <br><input type="text" name="email"
					value='<c:out value="${form.email}"/>' size="5"><br>
				<c:if test="${form.hasError('email')}">
					<div class="error">
						<c:out value="${form.getError('email')}" />
					</div>
				</c:if>

				Username: <br><input type="text" name="user"
					value='<c:out value="${form.nick}"/>' size="5"><br>
				<c:if test="${form.hasError('nick')}">
					<div class="error">
						<c:out value="${form.getError('nick')}" />
					</div>
				</c:if>
				
				Password: <br><input type="password" name="pass"
					value='<c:out value="${form.password}"/>' size="5"><br>
				<c:if test="${form.hasError('password')}">
					<div class="error">
						<c:out value="${form.getError('password')}" />
					</div>
				</c:if>
				<input type="submit" value="Sign up">
			</form>
			<br>
			
			<div class="home">
				<a href="/blog/">Home</a>
			</div>
		</c:when>	
		<c:otherwise>
			You're already registered.<br>
			<div class="home">
				<a href="/blog/">Home</a>
			</div>
		</c:otherwise>	
	</c:choose>
</body>
</html>
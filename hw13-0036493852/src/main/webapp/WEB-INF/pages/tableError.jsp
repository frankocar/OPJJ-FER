<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body bgColor="${pickedBgCol}">
	<h1>
		Error
	</h1>
	
	<p>
		Given parameter is invalid: ${errorMsg}
	</p>

	<p>
		<a href="index.jsp">Back to homepage</a><br>
	</p>
</body>
</html>
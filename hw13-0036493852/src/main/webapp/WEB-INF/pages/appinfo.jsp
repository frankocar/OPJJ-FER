<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%

	long uptimeMillis = System.currentTimeMillis() - (long)getServletContext().getAttribute("startTime");
	long uptimeSeconds = uptimeMillis / 1000;
	
	int millis = (int)uptimeMillis % 1000;
	int seconds = (int)uptimeSeconds % 60;
	int minutes = (int)(uptimeSeconds / 60) % 60;
	int hours = (int)(uptimeSeconds / (60 * 60)) % 60;
	int days = (int)(uptimeSeconds / (60 * 60 * 24)) % 365;
	int years = (int)(uptimeSeconds / (60 * 60 * 24 * 365));
	
	StringBuilder sb = new StringBuilder();
	
	if (years > 0) {
		sb.append(years + " years ");
	}
	
	if (days > 0) {
		sb.append(days + " days ");
	}
	
	if (hours > 0) {
		sb.append(hours + " hours ");
	}
	
	if (minutes > 0) {
		sb.append(minutes + " minutes ");
	}
	
	sb.append(seconds + " seconds and ");
	sb.append(millis + " milliseconds");
	
	
	String uptime = sb.toString();



%>

<html>
<body bgColor="${pickedBgCol}">
	<h1>
		Uptime
	</h1>
	
	<p>
		Current uptime is <%= uptime %>
	</p>

	<p>
		<a href="index.jsp">Back to homepage</a><br>
	</p>
</body>
</html>
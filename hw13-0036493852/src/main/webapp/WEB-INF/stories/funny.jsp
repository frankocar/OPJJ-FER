<%@page import="java.util.Random"%>
<%@page import="java.awt.Color"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>

<html>
<body bgColor="${pickedBgCol}">
<head>
<style>
	body {color: <% 
		String[] colors = {"black", "red", "green", "blue", "brown", "gray", "yellow"};
		out.print(colors[new Random().nextInt(colors.length)]);
	%>;}
</style>
</head>
	<h1>
		A good answer for when you're pulled over
	</h1>
	<p>
		$Hero - our hero.<br>
		$Cop - A representative of our hard worked law enforcement agency.
	</p>
	<p>
		So $Hero is happily speeding along in his car, running a few yellow lights a bit late, etc. 
		Finally, the law catches up to him and pulls him over. Here's how the conversation went:<br><br>
		<b>--$Cop</b>: Can I see your driving license, please?<br>
		<b>--$Hero</b> (with smug grin): Certainly. Here it is, officer.<br>
		<b>--$Cop</b> takes license back to motorcycle and speaks into radio.<br>
		<b>--$Hero:</b> It's not going to help you any, though.<br>
		<b>--$Cop</b> (with no reaction): What do you mean?<br>
		<b>--$Hero</b> (with wider grin): The server you have to check it against is down.<br>
		<b>--$Cop</b> (still no reaction): And why do you say that?<br>
		<b>--$Hero:</b> Because I'm the guy they called to get on site and get it up again.<br><br>
		Our hero did not get a fine this time. Instead he got a police escort to his workplace.<br>
	</p>
	<p>
		<a href="index.jsp">Back to homepage</a><br>
	</p>
</body>
</html>

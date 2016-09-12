<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String username = "dakou";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>跟着Dakou学Spring</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h2>index jsp</h2>
	welcome
	<%=username%><br />
	<%
		out.print("dakou");
	%>
	${username}${param.xxx }${pageContext.session.id }
	<c:out value="${username }"></c:out>
</body>
</html>

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
	<form action="<%=request.getContextPath()%>/user/login" method="post">
		用户名：<input type="text" name="my_username" /> 密码: <input type="text"
			name="my_password"> <input type="submit">
	</form>
</body>
</html>

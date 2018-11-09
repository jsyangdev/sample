<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index</title>
</head>
<body>
	<h1>메인화면(Index)</h1>
	<c:if test="${sample != null}">
		${sample.sampleId}님 반갑습니다 !
		<br><br>
		<a href="${PageContext.request.contextPath}/sample/logoutSample">로그아웃</a>
	</c:if>
	<c:if test="${sample == null}">
		<a href="${PageContext.request.contextPath}/sample/loginSample">로그인</a>
	</c:if>
	
</body>
</html>
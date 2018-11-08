<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sampleList</title>
<!-- BootStrap CDM -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- jQuery CDM -->
<script language="javascript" src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
</head>
<body>
	<h1>sampleList</h1>
	<table class="table">
		<thead>
			<tr>
				<td>SAMPLE NO</td>
				<td>SAMPLE ID</td>
				<td>SAMPLE PW</td>
				<td>DELETE</td>
				<td>UPDATE</td>
			</tr>
		</thead>
		<tbody>
			<!-- model 안의 sampleList 가져와서 사용 -->
			<c:forEach var="sample" items="${sampleList}">
				<tr>
					<td>${sample.sampleNo}</td>
					<td>${sample.sampleId}</td>
					<td>${sample.samplePw}</td>
					<td><a href="/sample/removeSample?sampleNo=${sample.sampleNo}">DELETE</a></td>
					<td><a href="/sample/modifySample?sampleNo=${sample.sampleNo}">UPDATE</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- if문 사용하여 분기 -->
	<c:if test="${currentPage>1}">
		<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=1"><<처음</a>
		<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage-1}"><이전</a>
	</c:if>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	<!-- 첫 페이지부터 마지막 페이지까지 10개로 끊어서 보여주기 -->
	<c:if test="${currentPage<=lastPage-9}">
		<c:forEach begin="${currentPage}" end="${currentPage+9}" var="currentPage" step="1" >
			<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage}">${currentPage}</a>
		</c:forEach>
	</c:if>
	<c:if test="${currentPage>lastPage-9}">
		<c:forEach begin="${currentPage}" end="${lastPage}" var="currentPage" step="1" >
			<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage}">${currentPage}</a>
		</c:forEach>
	</c:if>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	<c:if test="${currentPage<lastPage}">
		<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage+1}">다음></a>
		<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${lastPage}">마지막>></a>
	</c:if>
	<br><br>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	- 현재페이지 : ${currentPage} -
</body>
</html>
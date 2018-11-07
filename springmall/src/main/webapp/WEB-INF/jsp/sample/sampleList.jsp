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
	<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=1"><<처음</a>
	<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage-1}"><이전</a>
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	페이징 할껴
	&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${currentPage+1}">다음></a>
	<a href="${pageContext.request.contextPath}/sample/sampleList?currentPage=${lastPage}">마지막>></a>
</body>
</html>
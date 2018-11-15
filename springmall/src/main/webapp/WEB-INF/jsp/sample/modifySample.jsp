<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modifySample</title>
<!-- BootStrap CDM -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- jQuery CDM -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $('#inputPassword').focus();
    
    $('#inputPassword').blur(function(){
        if($('#inputPassword').val().length < 4){
            $('#pwHelper').text('비밀번호를 4자 이상 입력하시오.');
            $('#inputPassword').focus();
        } else {
            $('#pwHelper').text('');
        }
    });
    $('#join-submit').click(function(){
        if($('#inputPassword').val().length > 3){
            $('#form').submit();
        }    
    });
})
</script>
</head>
<body>
	<h1>수정화면</h1>
	<!--
       	 파일업로드를 위해선 반드시 method="post" enctype="Multipart/form-data"
     -->
	<form action="${PageContext.request.contextPath}/sample/modifySample" method="post" enctype="multipart/form-data">
		<input type="hidden" name="sampleNo" value="${sample.sampleNo}">
		<div class="form-group">
			<label for="inputName">아이디</label>
			<input type="text" class="form-control" id="inputId" name="sampleId" value="${sample.sampleId}">
		</div>
		<div class="form-group">
			<label for="inputPassword">비밀번호</label>
			<input type="password" class="form-control" id="inputPassword" name="samplePw" value="${sample.samplePw}">
		</div>
		<div class="form-group">
			<label for="inputFile">파일업로드</label>
			
				<c:forEach var="row" items="${listForUpdate}">
					<p>
						<a href="#this">${row.samplefileName}.${row.samplefileExt}</a>
						<input type="file" class="form-control" id="inputFile" name="multipartFile">		<!-- 원래 업로드된 file 보여주고 수정할 수 있게 -->
						(${row.samplefileSize}kb)
					</p>
				</c:forEach>
			

			
		</div>
		<div class="form-group text-center">
			<button type="submit" id="join-submit" class="btn btn-success">
				수정확인<i class="fa fa-check spaceLeft"></i>		<!--  <i></i>: 글자를 기울여서 표시하는 태그 -->
			</button>
		</div>
	</form>
</body>
</html>
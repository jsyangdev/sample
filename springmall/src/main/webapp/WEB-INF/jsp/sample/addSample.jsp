<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addSample</title>
<!-- BootStrap CDM -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- jQuery CDM -->
<script type="text/javascript" src="<c:url value='/js/jquery.1.9.1.min.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
    $('#join-submit').click(function(){
    	
        if ($('#inputId').val()=="") {
            
            $('#inputId').focus();
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    });
    
    
    
    
    
});
</script>
</head>
<body>
	<h1>회원가입</h1><br><br>
	<form action="${PageContext.request.contextPath}/sample/addSample"
		method="post">
		<div class="form-group">
			<label for="inputName">아이디</label>
			<input type="text" class="form-control" id="inputId" name="sampleId" placeholder="이름을 입력해 주세요">
			<span id="checkId"></span>
		</div>
		<div class="form-group">
			<label for="inputPassword">비밀번호</label>
			<input type="password" class="form-control" id="inputPassword" name="samplePw" placeholder="비밀번호를 입력해주세요">
		</div>
		<div class="form-group">
			<label for="inputPasswordCheck">비밀번호 확인</label>
			<input type="password" class="form-control" id="inputPasswordCheck" name="samplePwCheck" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요">
		</div>
		<div class="form-group">
			<label>약관 동의</label>
			<div data-toggle="buttons">
				<label class="btn btn-primary active">
					<span class="fa fa-check"></span>
					<input id="agree" type="checkbox" autocomplete="off" checked>
				</label>
				이용약관에 동의합니다.
			</div>
		</div>
		<div class="form-group text-center">
			<button type="submit" id="join-submit" class="btn btn-primary">
				회원가입<i class="fa fa-check spaceLeft"></i>		<!--  <i></i>: 글자를 기울여서 표시하는 태그 -->
			</button>
			<button type="submit" class="btn btn-warning">
				가입취소<i class="fa fa-times spaceLeft"></i>
			</button>
		</div>
	</form>
</body>
</html>
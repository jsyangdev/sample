<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>loginSample</title>
<!-- BootStrap CDM -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- jQuery CDM -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

	/* jquery 이용해 유효성 검사 할 것 */

</script>
</head>
<body>
	<h1>로그인</h1>
	<br><br>
	<form action="${PageContext.request.contextPath}/sample/loginSample" id="form" method="post">
		<div class="form-group">
			<label for="inputName">아이디</label>
			<input type="text" class="form-control" id="inputId" name="sampleId" placeholder="아이디를 입력해주세요">
			<span id="checkId"></span>
		</div>
		<div class="form-group">
			<label for="inputPassword">비밀번호</label>
			<input type="password" class="form-control" id="inputPassword" name="samplePw" placeholder="비밀번호를 입력해주세요">
			<span id="checkPw"></span>
		</div>
		<div class="form-group text-center">
			<button type="submit" id="login-submit" class="btn btn-primary">
				로그인<i class="fa fa-check spaceLeft"></i>		<!--  <i></i>: 글자를 기울여서 표시하는 태그 	-->
			</button>
		</div>
	</form>
</body>
</html>
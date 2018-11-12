<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addSample</title>
<!-- BootStrap CDM -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<!-- jQuery CDM -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $('#inputId').focus();    
    
    $('#inputId').blur(function() {
        if($('#inputId').val().length < 4) {
            $('#idHelper').text('아이디를 4자 이상 입력하시오.');
            $('#inputId').focus();
        } else {
            $('#idHelper').text('');
        }
    });
    $('#inputPassword').blur(function(){
        if($('#inputPassword').val().length < 4){
            $('#pwHelper').text('비밀번호를 4자 이상 입력하시오.');
            $('#inputPassword').focus();
        } else {
            $('#pwHelper').text('');
        }
    });
    $('#inputPasswordCheck').blur(function(){
        if($('#inputPasswordCheck').val() == $('#inputPassword').val()){
            $('#pwCheckHelper').text('');
        } else {
            $('#pwCheckHelper').text('비밀번호가 일치하지 않습니다.');
            $('#inputPasswordCheck').focus();
        }        
    });
    $('#join-submit').click(function(){
        if(($('#inputId').val().length > 3) && ($('#inputPassword').val().length > 3) && ($('#inputPasswordCheck').val() == $('#inputPassword').val())){
            $('#form').submit();
        }    
    });
 
});
</script>
</head>
<body>
    <h1>회원가입</h1><br><br>
    <!--
       	 파일업로드를 위해선 반드시 method="post" enctype="Multipart/form-data"
     -->
    <form action="${PageContext.request.contextPath}/sample/addSample" id="form" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="inputName">아이디</label>
            <input type="text" class="form-control" id="inputId" name="sampleId" placeholder="이름을 입력해 주세요"><br>
            <span id="idHelper" class="text-danger"></span>  
        </div>
        <div class="form-group">
            <label for="inputPassword">비밀번호</label>
            <input type="password" class="form-control" id="inputPassword" name="samplePw" placeholder="비밀번호를 입력해주세요">
            <span id="pwHelper" class="text-danger"></span>  
        </div>
        <div class="form-group">
            <label for="inputPasswordCheck">비밀번호 확인</label>
            <input type="password" class="form-control" id="inputPasswordCheck" name="samplePwCheck" placeholder="비밀번호 확인을 위해 다시한번 입력 해 주세요">
            <span id="pwCheckHelper" class="text-danger"></span>
        </div>
        <div class="form-group">
            <label for="inputFile">파일 업로드</label>
            <input type="file" class="form-control" id="inputFile" name="multipartFile">
            <span id="fileCheckHelper" class="text-danger"></span>
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
            <button type="button" id="join-submit" class="btn btn-primary">
                회원가입<i class="fa fa-check spaceLeft"></i>        <!--  <i></i>: 글자를 기울여서 표시하는 태그 -->
            </button>
            <button type="button" class="btn btn-warning">
                가입취소<i class="fa fa-times spaceLeft"></i>
            </button>
        </div>
    </form>
</body>
</html>

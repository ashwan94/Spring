<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 로그인</title>
<style>
	label {
		display: block;
	}
	#log {
		color: red;
	}
</style>
</head>
<body>
<h2>로그인</h2>
<form action="/login" method="post" id="loginForm" enctype="application/x-www-form-urlencoded">
	<label>아이디:
		<input type="text" name="id" value="${cookie.savedId.value }" placeholder="ID를 입력하세요.">
	</label>
	<label>패스워드:
		<input type="password" name="password" placeholder="패스워드를 입력하세요.">
	</label>
	<label>
		<input type="checkbox" name="saveId" value="true"> 아이디 저장
	</label>
	<input type="hidden" name="location" value="${location}"/> <!-- interceptor 로 이동했을 경우 저장할 이전 URI 주소 -->
	<input type="hidden" name="boardNo" value="${boardNo}"/> <!-- interceptor 로 이동했을 경우 저장할 이전 게시판 no -->
	<div id="log">${msg}</div>
	<button type="button" id="loginBtn">로그인</button>
	<button type="button">취소</button>
</form>
<script>
	// ajax 로 formData 라는 객체를 사용하려면 headers 생략 가능하다
	document.querySelector("#loginBtn").addEventListener("click", evt => {
		// js 에서 지원하는 FormData 는 multipart/form-data 형식으로 data 를 전송함
		// header 와 body 가 아닌 payload 를 통해 전달함
		let formData = new FormData(loginForm);
		// alert("formData : ", formData); // 이유는 모르겠으나 비어있다
		// HTML 의 id 는 java 에서 변수로 바로 가져올 수 있음
		fetch("/ajaxLogin", {
			method: "POST",
			body: formData
		})
				.then(response => response.json())
				.then(data => {
					if (data.msg == "failure") {
						document.querySelector("#log").textContent = "로그인 실패!";
					} else {
						location.href = data.msg;
					}
				})
	})
</script>
</body>
</html>
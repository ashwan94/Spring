<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-05-27
  Time: 오후 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<sec:authorize access="isAnonymous()">
    <a href="/login">로그인</a>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <form action="/logout" method="post">
        <button>로그아웃</button>
    </form>
</sec:authorize>
<a href="/board/list">게시판</a>

</body>
</html>

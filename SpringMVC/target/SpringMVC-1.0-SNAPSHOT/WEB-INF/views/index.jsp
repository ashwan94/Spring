<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>
</h1>
<c:choose>
    <c:when test="${empty sessionScope.member}">
        <a href="<c:url value="/login"/>">로그인</a>
<%--        contextPath 와 동일한 기능으로 <c:url value> 를 사용할 수 있다--%>
    </c:when>
    <c:otherwise>
        <a href="<c:url value="/logout"/>">로그아웃</a>
    </c:otherwise>
</c:choose>
<a href="<c:url value="/board/list"/>">게시판 이동</a>
<br/>
</body>
</html>
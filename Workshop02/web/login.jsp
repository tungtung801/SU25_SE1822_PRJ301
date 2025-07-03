<%--
    Document   : login
    Created on : Jul 1, 2025, 10:07:06 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="username_err" value="${requestScope.username_err}"/>
<c:set var="password_err" value="${requestScope.password_err}"/>
<c:set var="errMsg" value="${requestScope.errMsg}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Portal - Login</title>
        <link rel="stylesheet" href="assets/css/styleLogin.css"/>
    </head>
    <body>

        <div class="login-page-wrapper">
            <div class="header">
                <%@include file="header.jsp" %>
            </div>

            <c:choose>
                <c:when test="${isLoggedIn == false}">
                    <div class="main-login-content">
                        <div class="form-container">
                            <h2>Login</h2> <%-- Thêm tiêu đề vào đây --%>
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="login">
                                <%-- Loại bỏ chữ "Username:" và "Password:" cũ nếu bạn muốn dùng placeholder như đã gợi ý --%>
                                <label for="username">Username:</label>
                                <input type="text" id="username" name="username" placeholder="Enter username">
                                <span class="err">${username_err}</span>

                                <label for="password">Password:</label>
                                <input type="password" id="password" name="password" placeholder="Enter password">
                                <span class="err">${password_err}</span>

                                <input type="submit" value="Login">
                                <span class="err">${errMsg}</span>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:redirect url="welcome.jsp"/>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
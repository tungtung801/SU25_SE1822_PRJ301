<%-- 
    Document   : login
    Created on : Jun 22, 2025, 9:56:54 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="usernameMsg" value="${requestScope.usernameMsg}"/>
<c:set var="passMsg" value="${requestScope.passMsg}"/>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="inputUsername" value="${requestScope.inputUsername}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <c:redirect url="welcome.jsp"/>
            </c:when>
            <c:otherwise>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="login">
                    <div class="login-items">
                        <label for="Username">Username*</label>
                        <input id="Username" type="text" name="Username"
                               value="${inputUsername}"
                               >
                        <span class="error-area">${usernameMsg}</span>
                    </div>
                    <div class="login-items">
                        <label for="Password">Password*</label>
                        <input id="Password" type="password" name="Password">
                        <span class="error-area">${passMsg}</span>
                    </div>
                    <div class="form-btn">
                        <input type="submit" value="Login">
                        <input type="reset" value="Reset">
                    </div>
                    <span class="error-area">${message}</span>
                </form>
            </c:otherwise>
        </c:choose>

    </body>
</html>

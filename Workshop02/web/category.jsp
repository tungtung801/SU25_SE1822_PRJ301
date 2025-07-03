<%-- 
    Document   : category
    Created on : Jul 2, 2025, 8:40:41 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Categories</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <div class="container">
                    <div class="choose">
                        <form action="CategoryController" method="post">
                            <input type="hidden" name="action" value="viewQuiz">
                            <input type="submit" value="Quiz">
                        </form> 
                        <span> | </span>
                        <form action="CategoryController" method="post">
                            <input type="hidden" name="action" value="viewQuiz">
                            <input type="submit" value="Quiz">
                        </form>
                        <span> | </span>
                        <form action="CategoryController" method="post">
                            <input type="hidden" name="action" value="viewQuiz">
                            <input type="submit" value="Quiz">
                        </form>
                    </div>
                </div>
            </c:when>
        </c:choose>
    </body>
</html>

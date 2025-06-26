<%-- 
    Document   : login
    Created on : Jun 24, 2025, 10:08:17 PM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="sessionUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty sessionUser}"/>
<c:set var="username_err" value="${requestScope.username_err}"/>
<c:set var="password_err" value="${requestScope.password_err}"/>
<c:set var="message" value="${requestScope.message}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            
            .form-container {
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                width: 400px;
            }
            
            h1 {
                text-align: center;
                color: #333;
                margin-bottom: 30px;
            }
            
            .form-input {
                margin-bottom: 20px;
            }
            
            .form-input label {
                display: block;
                margin-bottom: 5px;
                color: #555;
                font-weight: bold;
            }
            
            .form-input input[type="text"],
            .form-input input[type="password"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
                box-sizing: border-box;
                margin-bottom: 5px;
            }
            
            .form-input input[type="text"]:focus,
            .form-input input[type="password"]:focus {
                outline: none;
                border-color: #4CAF50;
            }
            
            .form-btn {
                text-align: center;
                margin-top: 20px;
            }
            
            .form-btn input[type="submit"],
            .form-btn input[type="reset"] {
                padding: 10px 20px;
                margin: 0 5px;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
                width: 100%;
            }
            
            .form-btn input[type="submit"] {
                background-color: #4CAF50;
                color: white;
            }
            
            .form-btn input[type="submit"]:hover {
                background-color: #45a049;
            }
            
            .form-btn input[type="reset"] {
                background-color: #f44336;
                color: white;
            }
            
            .form-btn input[type="reset"]:hover {
                background-color: #da190b;
            }
            
            .err {
                color: red;
                font-size: 14px;
                display: block;
                margin-top: 5px;
            }
            
            .message-success {
                color: green;
                background-color: #d4edda;
                border: 1px solid #c3e6cb;
                padding: 10px;
                margin: 10px 0;
                border-radius: 4px;
                text-align: center;
            }
            
            .message-error {
                color: red;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                padding: 10px;
                margin: 10px 0;
                border-radius: 4px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn == false}">
                <div class="form-container">
                    <h1>Login</h1>
                    
                    <%-- Hiển thị message error nếu có --%>
                    <c:if test="${not empty message}">
                        <div class="message-error">
                            ${message}
                        </div>
                    </c:if>
                    
                    <form action="MainController" method="post">
                        <input type="hidden" name="action" value="login">
                        
                        <div class="form-input">
                            <label>Username*:</label>
                            <input type="text" name="Username">
                            <c:if test="${not empty username_err}">
                                <span class="err">${username_err}</span>
                            </c:if>
                        </div>
                        
                        <div class="form-input">
                            <label>Password*:</label>
                            <input type="password" name="Password">
                            <c:if test="${not empty password_err}">
                                <span class="err">${password_err}</span>
                            </c:if>
                        </div>
                        
                        <div class="form-btn">
                            <input type="submit" value="Login">
<!--                            <input type="reset" value="Reset">-->
                        </div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="welcome.jsp"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>
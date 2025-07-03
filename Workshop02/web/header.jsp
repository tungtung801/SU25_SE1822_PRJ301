

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="currentUser" value="${sessionScope.user}" />
<c:set var="isInstructor" value="${currentUser.role eq 'Instructor'}" />
<c:set var="isStudent" value="${currentUser.role eq 'Student'}" />
<link rel="stylesheet" href="assets/css/styleHeader.css"/>

<div class="header-container">
    <div class="welcome">
        <p>Exam Portal</p>
        
        <div class="info">
            <c:choose>
                <c:when test="${not empty currentUser}">
                    <h2>Name: ${currentUser.name}</h2>
                    <h3>Role: ${currentUser.role}</h3>
                    <a href="MainController?action=logout">Logout</a>
                </c:when>
                <c:otherwise>
                    
                </c:otherwise>
            </c:choose>
            
        </div>
    </div>
</div>

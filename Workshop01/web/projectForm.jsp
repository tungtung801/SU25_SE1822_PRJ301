<%-- 
    Document   : projectForm
    Created on : Jun 22, 2025, 12:11:20 PM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="util.AuthUtil" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isFounder" value="${currentUser.role eq 'Founder'}"/>
<c:set var="currentProject" value="${requestScope.project}"/>
<c:set var="id_error" value="${requestScope.id_error}"/>
<c:set var="name_error" value="${requestScope.name_error}"/>
<c:set var="status_error" value="${requestScope.status_error}"/>
<c:set var="date_error" value="${requestScope.date_error}"/>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="isChanging" value="${requestScope.isChanging}"/>
<c:set var="newProject" value="${requestScope.newProject}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project Form Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <c:choose>
                    <c:when test="${isFounder}">
                        <h1>${isChanging ? 'PROJECT UPDATING' : 'PROJECT ADDING'}</h1>
                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="${isChanging ? 'updateProject' : 'addProject'}">

                            <div class="project-items">
                                <label for="project_id">Project ID*</label>
                                <input type="text" id="project_id" name="project_id" required="required"
                                       value="${isChanging ? newProject.project_id : currentProject.project_id}"
                                       ${isChanging ? 'readonly' : ''}                                         
                                       >
                                <span class="error-area">${id_error}</span>
                            </div>
                            <div class="project-items">
                                <label for="project_name">Project Name*</label>
                                <input type="text" id="project_name" name="project_name" required="required"
                                       value="${isChanging ? newProject.project_name : currentProject.project_name}"
                                       ${isChanging ? 'readonly' : ''}
                                       >
                                <span class="error-area">${name_error}</span>
                            </div>
                            <div class="project-items">
                                <label for="description">Description</label>
                                <input type="text" id="description" name="description"
                                       value="${isChanging ? newProject.description : currentProject.description}"
                                       ${isChanging ? 'readonly' : ''}
                                       >
                            </div>
                            <div class="project-items">
                                <label for="status">Status*</label>
                                <select id="status" name="status" required="required">
                                    <option value="">-- Select Status --</option>
                                    <option value="Ideation" ${currentProject.status eq 'Ideation' ? 'selected' : ''}>Ideation</option>
                                    <option value="Development" ${currentProject.status eq 'Development' ? 'selected' : ''}>Development</option>
                                    <option value="Launch" ${currentProject.status eq 'Launch' ? 'selected' : ''}>Launch</option>
                                    <option value="Scaling" ${currentProject.status eq 'Scaling' ? 'selected' : ''}>Scaling</option>
                                </select>
                                <span class="error-area">${status_error}</span>
                            </div>
                            <div class="project-items">
                                <label for="estimated_launch">Project estimated_launch (yyyy-MM-dd)*</label>
                                <input type="text" id="estimated_launch" name="estimated_launch"
                                       value="${isChanging ? newProject.estimated_launch : currentProject.estimated_launch}"
                                       ${isChanging ? 'readonly' : ''}
                                       >
                                <span class="error-area">${date_error}</span>
                            </div>

                            <div class="form-btn">
                                <input type="submit" value="${isChanging ? 'Update Project' : 'Add Project'}">
                                <a href="welcome.jsp">Go back</a>
                                <span class="error-area">${message}</span>
                            </div>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <%=AuthUtil.getAccessDenied("projectForm.jsp")%><br>
                        <a href="welcome.jsp">Go back</a>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:redirect url="login.jsp"/>
            </c:otherwise>
        </c:choose>

    </body>
</html>

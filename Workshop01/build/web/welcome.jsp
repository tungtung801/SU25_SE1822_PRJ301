<%-- 
    Document   : welcome
    Created on : Jun 22, 2025, 11:29:41 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isFounder" value="${currentUser.role eq 'Founder'}"/>
<c:set var="projectList" value="${requestScope.list}"/>
<c:set var="keyword" value="${requestScope.keyword}"/>
<c:set var="error" value="${requestScope.error}"/>
<c:set var="searchingList" value="${requestScope.searchingList}"/>
<c:set var="isSearching" value="${requestScope.isSearching}"/>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <div class="container">
                    <div class="welcome">
                        <div class="welcome-left">
                            <h1>Welcome ${currentUser.name}</h1>
                            <h3>Login as: ${currentUser.role}</h3>
                        </div>
                        <div class="welcome-right">
                            <a style="color: red;" href="MainController?action=logout">Logout</a>                           
                            <c:if test="${isFounder}">
                                <a href="projectForm.jsp">Go to add project</a>
                                <div class="search-section">
                                    <label class="search-label">Search by name:</label>
                                    <form action="MainController" method="post" class="search-form">
                                        <input type="hidden" name="action" value="searchProject"/>
                                        <input type="text" name="keyword" value="${keyword}" 
                                               class="search-input" placeholder="Enter project name..."/>
                                        <input type="submit" value="Search Project" class="search-btn"/>
                                        <span class="error-area">${error}</span>
                                    </form>
                                </div>
                            </c:if>

                        </div>
                    </div>
                    <div class="main-content">
                        <c:choose>

                            <c:when test="${isSearching and empty searchingList}">
                                <p>No projects match the name "${keyword}"</p>
                                <form action="MainController" method="post">
                                    <input type="hidden" name="action" value="viewAllProject">
                                    <input type="submit" value="View All Projects"/>
                                </form>
                            </c:when>


                            <c:when test="${empty projectList and not isSearching}">
                                <form action="MainController" method="post">
                                    <input type="hidden" name="action" value="viewAllProject">
                                    <input type="submit" value="View Projects"/>
                                </form>
                            </c:when>


                            <c:otherwise>
                                <div class="table-container">

                                    <table>
                                        <thead>
                                            <tr> 
                                                <th>Project ID</th>
                                                <th>Project Name</th>
                                                <th>Description</th>
                                                <th>Status</th>
                                                <th>Estimated Launch</th>
                                                <c:if test="${isFounder}">
                                                    <th>Action</th>
                                                </c:if>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:choose>

                                                <c:when test="${isSearching and not empty searchingList}">
                                                    <c:forEach var="p" items="${searchingList}">
                                                        <tr>
                                                            <td>${p.project_id}</td>
                                                            <td>${p.project_name}</td>
                                                            <td>${p.description}</td>
                                                            <td>${p.status}</td>
                                                            <td>${p.estimated_launch}</td>
                                                            <td>
                                                                <form action="MainController" method="post">
                                                                    <input type="hidden" name="action" value="editProject">
                                                                    <input type="hidden" name="keyword" value="${keyword}" />
                                                                    <input type="hidden" name="project_id" value="${p.project_id}" />
                                                                    <input type="submit" value="Edit">
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:when>

                                                <c:otherwise>
                                                    <c:forEach var="p" items="${projectList}">
                                                        <tr>
                                                            <td>${p.project_id}</td>
                                                            <td>${p.project_name}</td>
                                                            <td>${p.description}</td>
                                                            <td>${p.status}</td>
                                                            <td>${p.estimated_launch}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="login.jsp"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>

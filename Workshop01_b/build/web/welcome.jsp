<%-- 
    Document   : welcome
    Created on : Jun 24, 2025, 11:02:04 PM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isFounder" value="${currentUser.role eq 'Founder'}"/>
<c:set var="projectList" value="${requestScope.pList}"/>
<c:set var="viewList" value="${requestScope.vList}"/>
<c:set var="isSearching" value="${requestScope.isSearching}"/>
<c:set var="isViewing" value="${requestScope.isViewing}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome</title>
        <style>
            /* ============ RESET & BASE ============ */
            *{
                margin:0;
                padding:0;
                box-sizing:border-box
            }
            body{
                font-family:"Segoe UI",Roboto,Arial,sans-serif;
                background:#f1f5f9;
                color:#333;
                min-height:100vh;
            }

            /* ============ WRAPPER ============ */
            .welcome-container{
                max-width:1200px;
                margin:40px auto;
                padding:0 20px 60px;
            }

            /* ============ HEADER ============ */
            .welcome-header{
                background:#4caf50;
                color:#fff;
                padding:25px 30px;
                border-radius:12px;
                display:flex;
                align-items:flex-start;
                justify-content:space-between;
                gap:20px;
                box-shadow:0 4px 12px rgba(0,0,0,.12);
            }
            .welcome-header h1{
                font-size:26px;
                letter-spacing:.5px;
                flex:1;
            }

            /* ============ HEADER BUTTONS AREA ============ */
            .welcome-header > div:last-child{
                display:flex;
                flex-direction:column;
                gap:10px;
                align-items:flex-end;
            }

            /* Logout button (đỏ, ở trên) */
            .welcome-header a{
                text-decoration:none;
                background:#f44336;
                color:#fff;
                padding:8px 18px;
                border-radius:6px;
                font-weight:600;
                transition:background .25s;
                font-size:14px;
            }
            .welcome-header a:hover{
                background:#d32f2f
            }

            /* View all button (xanh lá, ở dưới) */
            .welcome-header form{
                margin:0
            }
            .welcome-header input[type="submit"]{
                background:#2196f3;
                color:#fff;
                padding:8px 18px;
                border:none;
                border-radius:6px;
                font-weight:600;
                cursor:pointer;
                transition:background .25s;
                font-size:14px;
            }
            .welcome-header input[type="submit"]:hover{
                background:#1976d2
            }

            /* ============ ADD PROJECT BUTTON ============ */
            .add{
                margin:30px 0 20px
            }
            .add a{
                display:inline-block;
                background:#2196f3;
                color:#fff;
                padding:10px 22px;
                border-radius:6px;
                font-weight:600;
                text-decoration:none;
                transition:background .25s;
            }
            .add a:hover{
                background:#0b7dda
            }

            /* ============ SEARCH & VIEW BAR ============ */
            .SearchNView{
                background:#fff;
                padding:20px;
                border-radius:12px;
                box-shadow:0 2px 8px rgba(0,0,0,.08);
                display:flex;
                flex-wrap:wrap;
                gap:25px;
                align-items:center;
                margin-bottom:25px;
            }
            .SearchNView form{
                display:flex;
                flex-wrap:wrap;
                align-items:center;
                gap:12px;
            }
            .SearchNView input[type="text"]{
                padding:9px 14px;
                border:1px solid #cbd5e1;
                border-radius:6px;
                min-width:230px;
                font-size:14px;
            }
            .SearchNView input[type="text"]:focus{
                outline:none;
                border-color:#4caf50;
            }
            .SearchNView input[type="submit"]{
                background:#4caf50;
                color:#fff;
                border:none;
                padding:9px 18px;
                border-radius:6px;
                cursor:pointer;
                font-size:14px;
                transition:background .25s;
            }
            .SearchNView input[type="submit"]:hover{
                background:#43a047
            }

            /* ============ TITLES ============ */
            h3{
                margin:10px 0 15px;
                font-size:22px;
                color:#333
            }

            /* ============ TABLE ============ */
            table{
                width:100%;
                border-collapse:collapse;
                background:#fff;
                border-radius:12px;
                overflow:hidden;
                box-shadow:0 2px 8px rgba(0,0,0,.08);
            }
            thead{
                background:#4caf50;
                color:#fff
            }
            thead td{
                padding:14px 18px;
                font-weight:600;
                text-align:left
            }
            tbody td{
                padding:12px 18px;
                border-bottom:1px solid #e2e8f0
            }
            tbody tr:hover{
                background:#f8fafc
            }
            tbody tr:last-child td{
                border-bottom:none
            }

            /* edit button */
            table form{
                display:inline
            }
            table input[type="submit"]{
                background:#ff9800;
                color:#fff;
                border:none;
                padding:6px 14px;
                border-radius:6px;
                cursor:pointer;
                font-size:13px;
                transition:background .25s;
            }
            table input[type="submit"]:hover{
                background:#e68900
            }

            /* ============ INFO / ERROR MESSAGE ============ */
            p{
                background:#fff3cd;
                color:#856404;
                padding:15px 20px;
                border:1px solid #ffeaa7;
                border-radius:8px;
                margin-top:25px;
                font-size:15px;
            }

            /* ============ RESPONSIVE ============ */
            @media(max-width:768px){
                .welcome-header{
                    flex-direction:column;
                    text-align:center;
                    align-items:center;
                }
                .welcome-header > div:last-child{
                    align-items:center;
                }
                .SearchNView{
                    flex-direction:column
                }
                .SearchNView form{
                    width:100%
                }
                .SearchNView input[type="text"]{
                    width:100%
                }
                table{
                    font-size:14px
                }
                thead td,tbody td{
                    padding:10px
                }
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <div class="welcome-container">
                    <div class="welcome-header">
                        <h1>WELCOME ${currentUser.name}</h1>
                        <a href="MainController?action=logout" class="logout-btn">Logout</a>

                        <form action="MainController" method="post">
                            <input type="hidden" name="action" value="viewProject">                               
                            <input type="submit" value="View all projects" class="view-btn">
                        </form>
                    </div>
                    <c:if test="${isFounder}">
                        <div class="add">
                            <a href="projectForm.jsp">Go to add project</a>
                        </div>
                        <div class="SearchNView">
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="searchProject">
                                Search by project name: <input type="text" name="keyword" value="${requestScope.keyword}">
                                <input type="submit" value="Search">
                            </form> 
                        </div>    
                    </c:if>

                    <c:if test="${not empty projectList or not empty viewList}">
                        <h3>
                            <c:choose>
                                <c:when test="${isSearching}">
                                    Search results:
                                </c:when>
                                <c:otherwise>
                                    All projects:
                                </c:otherwise>
                            </c:choose> 
                        </h3>

                        <table border="2">
                            <thead>
                                <tr>
                                    <td>Project ID</td>
                                    <td>Project Name</td>
                                    <td>Description</td>
                                    <td>Status</td>
                                    <td>Estimated Launch</td>
                                    <c:if test="${isFounder and isSearching}">
                                        <td>Action</td>
                                    </c:if>
                                </tr>
                            </thead>
                            <c:if test="${not empty projectList and isSearching}">
                                <tbody>
                                    <c:forEach var="p" items="${projectList}">
                                        <tr>
                                            <td>${p.project_id}</td>  
                                            <td>${p.project_name}</td>  
                                            <td>${p.description}</td>  
                                            <td>${p.status}</td>  
                                            <td>${p.estimated_launch}</td>  
                                            <td>
                                                <form action="MainController" method="post">
                                                    <input type="hidden" name="action" value="editProject">
                                                    <input type="hidden" name="project_id" value="${p.project_id}">
                                                    <input type="submit" value="Edit">
                                                </form>
                                            </td>  
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </c:if>

                            <c:if test="${not empty viewList and isViewing}">
                                <tbody>
                                    <c:forEach var="v" items="${viewList}">
                                        <tr>
                                            <td>${v.project_id}</td>  
                                            <td>${v.project_name}</td>  
                                            <td>${v.description}</td>  
                                            <td>${v.status}</td>  
                                            <td>${v.estimated_launch}</td>  
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </c:if>

                    <!------------------------ BÁO LỖI  -------------------------------->
                    <c:if test="${empty projectList and isSearching}">
                        <p>No projects found matching your search!</p>
                    </c:if>

                    <c:if test="${empty viewList and isViewing}">
                        <p>No projects available!</p>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="login.jsp"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>

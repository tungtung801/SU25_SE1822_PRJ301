<%-- 
    Document   : projectForm
    Created on : Jun 25, 2025, 6:38:20 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="util.AuthUtil" %>
<c:set var="currentProject" value="${isEditing ? requestScope.updateProject : requestScope.newProject}"/>
<c:set var="id_err" value="${requestScope.id_err}"/>
<c:set var="name_err" value="${requestScope.name_err}"/>
<c:set var="status_err" value="${requestScope.status_err}"/>
<c:set var="launch_err" value="${requestScope.launch_err}"/>
<c:set var="message" value="${requestScope.message}"/>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="isEditing" value="${requestScope.isEditing}"/>
<c:set var="statusUpdate_err" value="${requestScope.statusUpdate_err}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project page</title>
        <style>
            /* Reset nhỏ gọn */
            *,
            *::before,
            *::after {
                box-sizing: border-box;
                margin: 0;
                padding: 0;
            }

            body {
                font-family: "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
                background: #f4f6f9;
                padding: 30px 12px;
                color: #333;
            }

            /* Tiêu đề */
            h1 {
                text-align: center;
                margin-bottom: 24px;
                color: #212529;
            }

            /* Hộp chứa form */
            .form-container {
                max-width: 640px;      /* Giới hạn rộng */
                margin: 0 auto;        /* Canh giữa */
                background: #ffffff;
                border-radius: 10px;
                padding: 32px;
                box-shadow: 0 6px 18px rgba(0, 0, 0, 0.08);
            }

            /* Từng dòng input / label */
            .form-items {
                display: flex;
                flex-direction: column;
                margin-bottom: 18px;
            }

            .form-items label {
                font-weight: 600;
                margin-bottom: 6px;
            }

            /* Input, select, textarea chung */
            .form-items input[type="text"],
            .form-items select,
            .form-items textarea {
                padding: 10px 14px;
                border: 1px solid #ced4da;
                border-radius: 6px;
                font-size: 15px;
                transition: border-color 0.25s ease;
            }

            .form-items input[type="text"]:focus,
            .form-items select:focus,
            .form-items textarea:focus {
                outline: none;
                border-color: #4c7df3;
                box-shadow: 0 0 0 3px rgba(76, 125, 243, 0.15);
            }

            .form-items textarea {
                resize: vertical;
                min-height: 120px;
            }

            /* Trạng thái readonly */
            input[readonly],
            textarea[readonly],
            select[disabled] {
                background: #e9ecef;
                color: #6c757d;
                cursor: not-allowed;
            }

            /* Thông báo lỗi dưới input */
            .err {
                color: #dc3545;
                font-size: 14px;
                margin-top: 6px;
            }

            /* Khu vực nút */
            .form-btn {
                display: flex;
                flex-wrap: wrap;
                gap: 12px;
                margin-top: 10px;
            }

            .form-btn input[type="submit"],
            .form-btn input[type="reset"] {
                border: none;
                border-radius: 6px;
                padding: 10px 24px;
                font-size: 15px;
                color: #fff;
                cursor: pointer;
                transition: background 0.25s ease;
            }

            .form-btn input[type="submit"] {
                background: #28a745;
            }
            .form-btn input[type="submit"]:hover {
                background: #1e7e34;
            }

            .form-btn input[type="reset"] {
                background: #6c757d;
            }
            .form-btn input[type="reset"]:hover {
                background: #545b62;
            }

            /* Link quay lại */
            .form-btn a {
                display: inline-block;
                align-self: center;
                color: #007bff;
                text-decoration: none;
                margin-left: auto;      /* đẩy link sang bên phải */
                transition: color 0.25s ease;
            }
            .form-btn a:hover {
                color: #0056b3;
                text-decoration: underline;
            }

            /* Message (giữ nguyên) */
            .message-success {
                color: #155724;
                background-color: #d4edda;
                border: 1px solid #c3e6cb;
                padding: 12px 16px;
                border-radius: 6px;
                margin-bottom: 20px;
            }
            .message-error {
                color: #721c24;
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                padding: 12px 16px;
                border-radius: 6px;
                margin-bottom: 20px;
            }

            /* Responsive: dưới 560px nút xếp dọc */
            @media (max-width: 560px) {
                .form-btn {
                    flex-direction: column;
                    align-items: stretch;
                }
                .form-btn a {
                    margin-left: 0;     /* bỏ “auto” để nằm dưới */
                }
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <c:choose>
                    <c:when test="${currentUser.role eq 'Founder'}">
                        <h1>
                            <c:choose>
                                <c:when test="${isEditing}">
                                    Update Project
                                </c:when>
                                <c:otherwise>
                                    Add New Project
                                </c:otherwise>
                            </c:choose>
                        </h1>

                        <%-- Hiển thị message --%>
                        <c:if test="${not empty message}">
                            <div class="${message.contains('successfully') ? 'message-success' : 'message-error'}">
                                ${message}
                            </div>
                        </c:if>

                        <div class="form-container">
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="${isEditing ? 'updateProject' : 'addProject'}">
                                <div class="form-items">
                                    Project ID: <input type="text" name="id" required="required" 
                                                       value="${currentProject.project_id}" 
                                                       ${isEditing ? 'readonly' : ''}>
                                    <span class="err">${id_err}</span>
                                </div>
                                <div class="form-items">
                                    Project Name: <input type="text" name="name" required="required" 
                                                         value="${currentProject.project_name}" ${isEditing ? 'readonly' : ''}>
                                    <span class="err">${name_err}</span>
                                </div>
                                <div class="form-items">
                                    Description: <textarea name="description" rows="4" cols="50" ${isEditing ? 'readonly' : ''}>${currentProject.description}</textarea>
                                </div>
                                <div class="form-items">
                                    Status: <select name="status" required="required">
                                        <option value="">-- Select status --</option>
                                        <option value="Ideation" ${currentProject.status == 'Ideation' ? 'selected' : ''}>Ideation</option>
                                        <option value="Development" ${currentProject.status == 'Development' ? 'selected' : ''}>Development</option>
                                        <option value="Launch" ${currentProject.status == 'Launch' ? 'selected' : ''}>Launch</option>
                                        <option value="Scaling" ${currentProject.status == 'Scaling' ? 'selected' : ''}>Scaling</option>
                                    </select>
                                    <span class="err">${isEditing ? statusUpdate_err : status_err}</span>
                                </div>
                                <div class="form-items">
                                    Estimated launch (yyyy-MM-dd): 
                                    <input type="text" name="estimated_launch" required="required" 
                                           value="${currentProject.estimated_launch}" ${isEditing ? 'readonly' : ''}>
                                    <span class="err">${launch_err}</span>
                                </div>
                                <div class="form-btn">
                                    <input type="submit" value="${isEditing ? 'Update' : 'Add'}">
                                    <input type="reset" value="Reset">
                                    <a href="MainController?action=viewProject">Back to Project List</a>
                                </div>
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%=AuthUtil.getAccessdenied("Adding new project")%>
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
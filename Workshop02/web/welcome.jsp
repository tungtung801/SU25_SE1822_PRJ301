<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="currentUser" value="${sessionScope.user}"/>
<c:set var="isLoggedIn" value="${not empty currentUser}"/>
<c:set var="cateList" value="${requestScope.cateList}"/>
<c:set var="examList" value="${requestScope.examList}"/>
<c:set var="showErr" value="${requestScope.showErr}"/>
<c:set var="keyword_err" value="${requestScope.keyword_err}"/>
<c:set var="cate_id" value="${requestScope.cate_id}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Portal</title>
        <link rel="stylesheet" href="assets/css/styleWelcome.css">
    </head>
    <body>
        <c:choose>
            <c:when test="${isLoggedIn}">
                <div class="main">
                    <div class="header">
                        <%@include file="header.jsp" %>
                    </div>
                    <!-- =================================================== SHOW ALL CATEGORY ==================================================-->
                    <div class="main-content">
                        <div class="show">  
                            <h3>All Exam Categories</h3>
                            <form action="CategoryController" method="post">
                                <input type="hidden" name="action" value="viewCategories">
                            </form>

                            <c:choose>
                                <c:when test="${not empty cateList}">
                                    <table border="1">
                                        <thead>
                                            <tr>
                                                <th>Exam Category Name</th>
                                                <th>Description</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="c" items="${cateList}">
                                                <tr>
                                                    <td>${c.category_name}</td>
                                                    <td>${c.description}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 
                                </c:when>
                                <c:otherwise>
                                    <p>There are no categories available!</p>
                                    <c:if test="${not empty requestScope.showErr}">
                                        <p style="color: red;">${requestScope.showErr}</p>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- =================================================== VIEW ALL EXAM BY CATEGORY ==================================================-->
                        <div class="view-by-category">
                            <h3>
                                <c:choose>
                                    <c:when test="${currentUser.role eq 'Instructor'}">
                                        <h3>View | Add Exam By Categories</h3>
                                        <p>For instructor: Please choose one from category to create new exam *</p>
                                    </c:when>
                                    <c:otherwise>
                                        <h3>View Exam By Categories</h3>
                                    </c:otherwise>
                                </c:choose>
                            </h3>

                            <form action="ExamController" method="post">
                                <input type="hidden" name="action" value="viewExamByCate">
                                <select name="cate_keyword" onchange="this.form.submit()">
                                    <option value="">-- Choose one category --</option>
                                    <c:forEach var="c" items="${cateList}">
                                        <option value="${c.category_id}" ${c.category_id == cate_id ? 'selected' : ''}>${c.category_name}</option>
                                    </c:forEach>
                                </select>
                                <c:if test="${not empty keyword_err}">
                                    <p style="color: red;">${keyword_err}</p>
                                </c:if>

                                <c:if test="${not empty viewMessage}">
                                    <p style="color: red;">${viewMessage}</p>
                                </c:if>
                            </form>

                            <!-- ======================== ADD EXAM BY CATEGORY ========================-->

                            <div class="add">
                                <c:if test="${currentUser.role eq 'Instructor' and not empty cate_id}">
                                    <form action="ExamController" method="post">
                                        <input type="hidden" name="action" value="addExam">
                                        <input type="hidden" name="category_id" value="${cate_id}">
                                        <input type="submit" value="Add New Exam">
                                    </form> 
                                </c:if>
                            </div>
                            <!-- =================== END OF ADD EXAM BY CATEGORY ========================-->
                            <h3>Results:</h3>
                            <c:choose>
                                <c:when test="${not empty examList}">
                                    <table border="1">
                                        <thead>
                                            <tr>
                                                <th>Exam Tittle</th>
                                                <th>Subject</th>
                                                <th>Total Marks</th>
                                                <th>Duration</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="e" items="${examList}">
                                                <tr>
                                                    <td>${e.exam_tittle}</td>
                                                    <td>${e.subject}</td>
                                                    <td>${e.total_marks}</td>
                                                    <td>${e.duration}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table> 
                                </c:when>
                                <c:otherwise>
                                    <p>There are no exam available!</p>
                                    <c:if test="${not empty requestScope.showErr}">
                                        <p style="color: red;">${requestScope.showErr}</p>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>  
                        </div>
                        <!-- =========================================== END OF VIEW ALL EXAM BY CATEGORY ===========================================-->
                    </div>

                    <div class="footer">
                        <%@include file="footer.jsp" %>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="login.jsp"/>
            </c:otherwise>
        </c:choose>
    </body>
</html>
<%-- 
    Document   : examForm
    Created on : Jul 3, 2025, 11:11:51 AM
    Author     : Tung Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="cate_id" value="${requestScope.cate_id}"/>
<c:set var="category" value="${requestScope.category}"/>
<c:set var="addMessage" value="${requestScope.addMessage}"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Create New Exam</title>
        <link rel="stylesheet" href="assets/css/styleExamForm.css"/>
    </head>
    <body>
        <div class="container">
            <h2>Create New Exam</h2>


            <div class="form-container">
                <form action="ExamController" method="post">
                    <input type="hidden" name="action" value="submitAddExam"/>
                    <input type="hidden" name="cate_id" value="${cate_id}"/>

                    <p><strong>Category: ${category.category_name}</strong></p>

                    <label>Exam Title:</label>
                    <input type="text" name="exam_tittle" required/><br/>

                    <label>Subject:</label>
                    <input type="text" name="subject" required/><br/>

                    <label>Total Marks:</label>
                    <input type="number" name="total_marks" required/><br/>

                    <label>Duration (minutes):</label>
                    <input type="number" name="duration" required/><br/>

                    <input type="submit" value="Submit Exam To Server"/>
                    <c:if test="${not empty addMessage}">
                        <p style="color: red;">${addMessage}</p>
                    </c:if>
                </form>

                <div class="form-btn">
                    <a href="ExamController?action=viewExamByCate&cate_keyword=${category.category_id}">Back</a> 
                </div>
            </div>



        </div>

    </body>
</html>

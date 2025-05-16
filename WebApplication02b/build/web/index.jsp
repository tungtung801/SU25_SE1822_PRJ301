<%-- 
    Document   : index
    Created on : May 16, 2025, 10:43:25 AM
    Author     : Tung Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/style.css"/>
    </head>
    <body>
    <form class="main" action="#">
        <h1 class="tittle">Login Form</h1>

        <div class="login-required">
            Textbox <input type="text" name="txtText" value="" size="5"/><br/>
            Password <input type="password" name="txtPassword" value=""/><br/>
            Hidden <input type="hidden" name="txtHidden" value=""/><br/>
        </div>

        <div class="personal-info">
            Male <input type="checkbox" name="txtCheck" value="ON" checked="checked"/><br/>
            Status
            <div class="info-choose">
                <input type="radio" name="txtStatus" value="Single" checked="checked"/>Single<br/>
                <input type="radio" name="txtStatus" value="Married"/>Married<br/>
                <input type="radio" name="txtStatus" value="Divorsed"/>Divorsed<br/>
            </div>
        </div>

        <div class="chooses">
            ComboBox
            <select name="txtCombo">
                <option value="JSP and Servlet">JSP and Servlet</option>
                <option value="EJB">EJB</option>
                <option value="Core Jave">Core Jave</option>
            </select>

            Multiple
            <select name="txtList" multiple="multiple" size="3">
                <option value="JSP and Servlet" selected>JSP and Servlet</option>
                <option value="EJB" selected>EJB</option>
                <option value="Core Jave">Core Java</option>
            </select>
        </div>

        <div class="demo-text">
            TextArea
            <textarea name="txtArea" rows="4" cols="20">
                This is a from parameters demo!!!!
            </textarea><br/>
        </div>

        <div class="foot-btn">
            <input type="submit" name="txtB"/>
            <input type="submit" value="Register" name="action"/>
            <input type="reset" name="txtB"/>
            <input type="button" value="JavaScript" name="txtB" onclick=""/>
        </div>
    </form>
    </body>
</html>

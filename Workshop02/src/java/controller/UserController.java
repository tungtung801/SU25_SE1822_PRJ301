/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;
import model.UserDAO;
import model.UserDTO;
import util.AuthUtil;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static String LOGIN_PAGE = "login.jsp";
    private static String WELCOME_PAGE = "welcome.jsp";
    UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            if ("login".equals(action)) {
                url = handleLogin(request, response);
            } else if ("logout".equals(action)) {
                url = handleLogout(request, response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        if (AuthUtil.isLoggedIn(request)) {
            url = WELCOME_PAGE;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String username_err = null;
        if (username == null || username.trim().isEmpty()) {
            username_err = "Username is empty!";
        }
        String password_err = null;
        if (password == null || password.trim().isEmpty()) {
            password_err = "Password is empty!";
        }

        String errMsg = "";
        if (username_err != null && password_err != null) {
            request.setAttribute("username_err", username_err);
            request.setAttribute("password_err", password_err);
        } else {
            UserDTO loginUser = null;
            if (udao.login(username, password)) {
                loginUser = udao.getUserByUsername(username);
                HttpSession session = request.getSession();
                session.setAttribute("user", loginUser);
                url = WELCOME_PAGE;
            } else {
                errMsg = "Please check again Username and Password!";
                request.setAttribute("errMsg", errMsg);
                url = LOGIN_PAGE;
            }
        }
        CategoryDAO cdao = new CategoryDAO();
        List<CategoryDTO> cateList = cdao.getAllCategories();
        request.setAttribute("cateList", cateList);
        return url;
    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserDTO currentUser = (UserDTO) session.getAttribute("user");
            if (currentUser != null) {
                session.invalidate();
            }
        }
        return LOGIN_PAGE;
    }

}

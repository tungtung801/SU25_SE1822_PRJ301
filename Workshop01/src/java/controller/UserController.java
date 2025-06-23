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
import model.UserDAO;
import model.UserDTO;
import util.AuthUtil;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";
    UserDAO udao = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = LOGIN_PAGE;
        String getAction = request.getParameter("action");
        try {
            if ("login".equals(getAction)) {
                url = handleLogin(request, response);
            } else if ("logout".equals(getAction)) {
                url = handleLogout(request, response);
            }
        } catch (Exception e) {
            System.out.println("Invalid action: " + e.getMessage());
            e.printStackTrace();
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
        HttpSession session = request.getSession();

        String username = request.getParameter("Username");
        String password = request.getParameter("Password");
        String message = "";
        String usernameMsg = "";
        String passMsg = "";

        if (username == null || username.trim().isEmpty()) {
            usernameMsg = "Username cannot be null!";
        } else {
            request.setAttribute("inputUsername", username);
        }

        if (message.isEmpty() && (password == null || password.trim().isEmpty())) {
            passMsg = "Password cannot be null!";
        }

        if (message.isEmpty()) {
            if (udao.login(username, password)) {
                url = WELCOME_PAGE;
                session.setAttribute("user", udao.getUserByUsername(username));
            } else {
                message = "Login fail, check again username and password!";
            }
        }

        request.setAttribute("usernameMsg", usernameMsg);
        request.setAttribute("passMsg", passMsg);
        request.setAttribute("message", message);
        return url;

    }

    private String handleLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                session.invalidate();
            }
        }
        return LOGIN_PAGE;
    }

}

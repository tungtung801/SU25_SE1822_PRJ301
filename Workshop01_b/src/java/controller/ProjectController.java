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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.ProjectDAO;
import model.ProjectDTO;
import util.AuthUtil;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "ProjectController", urlPatterns = {"/ProjectController"})
public class ProjectController extends HttpServlet {

    private static final String PROJECT_PAGE = "projectForm.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    ProjectDAO pdao = new ProjectDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String getAction = request.getParameter("action");
        String url = PROJECT_PAGE;
        try {
            if ("addProject".equals(getAction)) {
                url = handleProjectAdding(request, response);
            } else if ("searchProject".equals(getAction)) {
                url = handleProjectSearching(request, response);
            } else if ("viewProject".equals(getAction)) {
                url = handleProjectViewing(request, response);
            } else if ("editProject".equals(getAction)) {
                url = handleProjectEditing(request, response);
            } else if ("updateProject".equals(getAction)) {
                url = handleProjectUpdating(request, response);
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

    private String handleProjectAdding(HttpServletRequest request, HttpServletResponse response) {
        String url = PROJECT_PAGE;
        if (!AuthUtil.isFounder(request)) {
            url = WELCOME_PAGE;
        }
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String estimated_launch = request.getParameter("estimated_launch");

        String id_err = null;
        int id_value = 0;
        String name_err = null;
        String status_err = null;
        String launch_err = null;
        String message = null;

        if (id == null || id.trim().isEmpty()) {
            id_err = "ID is empty!";
        }
        try {
            id_value = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Cannot casting id to int!");
        }
        if (pdao.getProjectById(id_value) != null) {
            id_err = "This ID is alreadry exist!";
        }

        if (name == null || name.trim().isEmpty()) {
            name_err = "Name is empty!";
        }

        if (status == null || status.trim().isEmpty()) {
            status_err = "Status is empty!";
        }

        LocalDate launch_casting = null;
        Date launch_value = null;
        if (estimated_launch == null || estimated_launch.trim().isEmpty()) {
            launch_err = "Estimated launch is empty!";
        }
        try {
            launch_casting = LocalDate.parse(estimated_launch);
            if (launch_casting.isBefore(LocalDate.now())) {
                launch_err = "Date must in future!";
            } else {
                launch_value = Date.valueOf(launch_casting);
            }
        } catch (Exception e) {
            launch_err = "Invalid date format! Please use yyyy-MM-dd (e.g., 2025-12-31)";
            System.out.println("Cannot casting Date to LocalDate!");
        }

        ProjectDTO newProject = new ProjectDTO(id_value, name, description, status, launch_value);

        if (id_err != null || name_err != null || status_err != null || launch_err != null) {
            message = "Cannot add project. Check again all fields!";
            request.setAttribute("newProject", newProject);
        } else {
            boolean addSuccess = pdao.addProject(newProject);

            if (addSuccess) {
                message = "Add project successfully!";
            } else {
                message = "Failed to add project to database!";
                request.setAttribute("newProject", newProject);
            }
        }

        request.setAttribute("id_err", id_err);
        request.setAttribute("name_err", name_err);
        request.setAttribute("status_err", status_err);
        request.setAttribute("launch_err", launch_err);
        request.setAttribute("message", message);

        return url;
    }

    private String handleProjectSearching(HttpServletRequest request, HttpServletResponse response) {
        String url = WELCOME_PAGE;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        String keyword = request.getParameter("keyword");
        List<ProjectDTO> projectList = new ArrayList<>();
        projectList = pdao.getProjectByName(keyword);
        if (projectList != null) {
            request.setAttribute("pList", projectList);
        }
        request.setAttribute("keyword", keyword);
        request.setAttribute("isSearching", true);
        return url;
    }

    private String handleProjectViewing(HttpServletRequest request, HttpServletResponse response) {
        String url = WELCOME_PAGE;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        List<ProjectDTO> viewList = new ArrayList<>();
        viewList = pdao.getAllProject();
        if (viewList != null) {
            request.setAttribute("vList", viewList);
        }
        request.setAttribute("isViewing", true);
        return url;
    }

    private String handleProjectEditing(HttpServletRequest request, HttpServletResponse response) {
        String url = PROJECT_PAGE;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtil.isFounder(request)) {
            url = WELCOME_PAGE;
        } else {
            String id = request.getParameter("project_id");
            String keyword = request.getParameter("keyword");

            int id_value = 0;

            try {
                id_value = Integer.parseInt(id);
            } catch (Exception e) {
                System.out.println("Cannot casting id to int!");
            }
            ProjectDTO updateProject = pdao.getProjectById(id_value);
            request.setAttribute("updateProject", updateProject);
            request.setAttribute("keyword", keyword);
            request.setAttribute("isEditing", true);
            return url = PROJECT_PAGE;
        }

        return handleProjectSearching(request, response);
    }

    private String handleProjectUpdating(HttpServletRequest request, HttpServletResponse response) {
        String url = PROJECT_PAGE;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
            return url;
        }
        if (!AuthUtil.isFounder(request)) {
            url = WELCOME_PAGE;
            return url;
        }

        String id = request.getParameter("id");
        String status = request.getParameter("status");

        String message = null;
        String status_err = null;

        int id_value = 0;
        try {
            id_value = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Cannot casting id to int!");
            message = "Invalid project ID!";
        }

        // Validate status
        if (status == null || status.trim().isEmpty()) {
            status_err = "Status is required!";
        }

        // Lấy project hiện tại
        ProjectDTO currentProject = pdao.getProjectById(id_value);

        if (status_err == null && currentProject != null) {
            // Chỉ update status
            boolean success = pdao.updateStatus(id_value, status);

            if (success) {
                message = "Status updated successfully!";
                // Load lại project sau khi update để hiển thị data mới
                currentProject = pdao.getProjectById(id_value);
            } else {
                message = "Update failed! Please try again.";
            }
        } else if (currentProject == null) {
            message = "Project not found!";
        } else {
            message = "Cannot update status! Please check your input.";
        }

        // Set attributes để hiển thị lại form với data và message
        request.setAttribute("updateProject", currentProject);
        request.setAttribute("isEditing", true);
        request.setAttribute("message", message);
        request.setAttribute("statusUpdate_err", status_err);

        return url;
    }

}

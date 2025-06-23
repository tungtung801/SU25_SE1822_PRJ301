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
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String WELCOME_PAGE = "welcome.jsp";
    ProjectDAO pdao = new ProjectDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = PROJECT_PAGE;
        String getAction = request.getParameter("action");
        try {
            if ("addProject".equals(getAction)) {
                url = handleProjectAdding(request, response);
            } else if ("viewAllProject".equals(getAction)) {
                url = handlePrjectViewing(request, response);
            } else if ("searchProject".equals(getAction)) {
                url = handlePrjectSearching(request, response);
            } else if ("editProject".equals(getAction)) {
                url = handleProjectEditing(request, response);
            }else if ("updateProject".equals(getAction)) {
                url = handleProjectUpdating(request, response);
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

    private String handleProjectAdding(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtil.isLoggedIn(request)) {
            if (AuthUtil.isFounder(request)) {
                String id = request.getParameter("project_id");
                String name = request.getParameter("project_name");
                String descrption = request.getParameter("description");
                String status = request.getParameter("status");
                String estimated_launch = request.getParameter("estimated_launch");

                String id_error = "";
                String name_error = "";
                String status_error = "";
                String date_error = "";
                String message = "";

                boolean hasError = false;

                int id_value = 0;
                try {
                    id_value = Integer.parseInt(id);
                    if (id_value <= 0) {
                        id_error = "ID must greater than zero!";
                    } else if (pdao.isProjectExist(id_value)) {
                        id_error = "ID already exist!";
                    }
                } catch (Exception e) {
                    System.out.println("Error while casting id: " + e.getMessage());
                }

                if (name == null || name.trim().isEmpty()) {
                    name_error = "Name cannot be empty!";
                }

                if (status == null || status.trim().isEmpty()) {
                    status_error = "Status cannot be empty!";
                }

                Date estimated_launch_value = null;
                try {
                    if (estimated_launch == null || estimated_launch.trim().isEmpty()) {
                        date_error = "Estimated launch date cannot be empty!";
                    } else {
                        LocalDate estimated_launch_casting = LocalDate.parse(estimated_launch);
                        if (estimated_launch_casting.isBefore(LocalDate.now())) {
                            date_error = "Estimated launch date must in future!";
                        } else {
                            estimated_launch_value = Date.valueOf(estimated_launch_casting);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date format, (yyyy-MM-dd expected) ! " + e.getMessage());
                }

                ProjectDTO project = new ProjectDTO(id_value, name, descrption, status, estimated_launch_value);
                request.setAttribute("project", project);

                if (pdao.addProject(project)) {
                    message = "Project added SUCCESSFULLY!";
                    request.setAttribute("project", null);
                } else {
                    message = "Project added FAILED!";
                }

                request.setAttribute("id_error", id_error);
                request.setAttribute("name_error", name_error);
                request.setAttribute("status_error", status_error);
                request.setAttribute("date_error", date_error);
                request.setAttribute("message", message);

                return PROJECT_PAGE;
            } else {
                AuthUtil.getAccessDenied(PROJECT_PAGE);
            }
        }
        return LOGIN_PAGE;
    }

    private String handlePrjectViewing(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtil.isLoggedIn(request)) {
            List<ProjectDTO> list = pdao.getAllProject();
            request.setAttribute("list", list);
            return WELCOME_PAGE;
        }
        return LOGIN_PAGE;
    }

    private String handlePrjectSearching(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtil.isLoggedIn(request)) {
            if (AuthUtil.isFounder(request)) {
                String keyword = request.getParameter("keyword");

                List<ProjectDTO> searchingList = pdao.getProjectByName(keyword);

                request.setAttribute("searchingList", searchingList);
                request.setAttribute("keyword", keyword);
                request.setAttribute("isSearching", true);
                return WELCOME_PAGE;
            }
        }
        return LOGIN_PAGE;
    }

    private String handleProjectUpdating(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtil.isLoggedIn(request)) {
            if (AuthUtil.isFounder(request)) {
                String id = request.getParameter("project_id");
                String name = request.getParameter("project_name");
                String descrption = request.getParameter("description");
                String status = request.getParameter("status");
                String estimated_launch = request.getParameter("estimated_launch");

                String id_error = "";
                String name_error = "";
                String status_error = "";
                String date_error = "";
                String message = "";

                boolean hasError = false;

                int id_value = 0;
                try {
                    id_value = Integer.parseInt(id);
                    if (id_value <= 0) {
                        id_error = "ID must greater than zero!";
                    } else if (pdao.isProjectExist(id_value)) {
                        id_error = "ID already exist!";
                    }
                } catch (Exception e) {
                    System.out.println("Error while casting id: " + e.getMessage());
                }

                if (name == null || name.trim().isEmpty()) {
                    name_error = "Name cannot be empty!";
                }

                if (status == null || status.trim().isEmpty()) {
                    status_error = "Status cannot be empty!";
                }

                Date estimated_launch_value = null;
                try {
                    if (estimated_launch == null || estimated_launch.trim().isEmpty()) {
                        date_error = "Estimated launch date cannot be empty!";
                    } else {
                        LocalDate estimated_launch_casting = LocalDate.parse(estimated_launch);
                        if (estimated_launch_casting.isBefore(LocalDate.now())) {
                            date_error = "Estimated launch date must in future!";
                        } else {
                            estimated_launch_value = Date.valueOf(estimated_launch_casting);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Invalid date format, (yyyy-MM-dd expected) ! " + e.getMessage());
                }

                ProjectDTO newProject = new ProjectDTO(id_value, name, descrption, status, estimated_launch_value);
                request.setAttribute("project", newProject);

                if (pdao.update(newProject)) {
                    message = "Project updated SUCCESSFULLY!";
                    request.setAttribute("newProject", newProject);
                } else {
                    message = "Project updated FAILED!";
                }

                request.setAttribute("id_error", id_error);
                request.setAttribute("name_error", name_error);
                request.setAttribute("status_error", status_error);
                request.setAttribute("date_error", date_error);
                request.setAttribute("message", message);
                request.setAttribute("isChanging", true);

                return PROJECT_PAGE;
            } else {
                AuthUtil.getAccessDenied(PROJECT_PAGE);
            }
        }
        return LOGIN_PAGE;
    }

    private String handleProjectEditing(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("keyword");
        String id = request.getParameter("id");
        int id_value = 0;
        try {
            id_value = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Error casting in edit(): "+e.getMessage());
        }
        if (AuthUtil.isFounder(request)) {
            ProjectDTO newProject = pdao.getProjectById(id_value);
            request.setAttribute("newProject", newProject);
            request.setAttribute("keyword", keyword);
            request.setAttribute("isChanging", true);
            return "projectForm.jsp";
        }
        return handlePrjectSearching(request, response);
    }

//private String handleProjectUpdating(HttpServletRequest request, HttpServletResponse response) {
//    if (AuthUtil.isLoggedIn(request) && AuthUtil.isFounder(request)) {
//        String id = request.getParameter("project_id");
//        String status = request.getParameter("status");
//        String name = request.getParameter("project_name"); // Thêm các trường khác
//        String description = request.getParameter("description"); // Thêm các trường khác
//        String estimated_launch = request.getParameter("estimated_launch"); // Thêm các trường khác
//
//        String status_error = "";
//        String message = "";
//
//        // Khai báo các biến lỗi khác để có thể hiển thị
//        String name_error = "";
//        String date_error = "";
//
//        try {
//            int id_value = Integer.parseInt(id);
//            Date estimated_launch_value = null; // Khởi tạo để dùng
//
//            // --- Bắt đầu phần validate dữ liệu cho các trường khác (nếu cần update) ---
//            // Bạn có thể giữ nguyên logic validate từ handleProjectAdding
//            // Hoặc nếu bạn muốn update từng phần, thì chỉ validate cái nào được gửi lên
//            // Hiện tại form của bạn đang set readonly cho các trường khác khi isChanging là true
//            // nên có thể những trường này sẽ không được gửi lên đầy đủ.
//            // Điều này dẫn đến việc bạn chỉ update status.
//
//            // Kiểm tra status có rỗng không
//            if (status == null || status.trim().isEmpty()) {
//                status_error = "Status cannot be empty!";
//            }
//
//            // Nếu không có lỗi về status và muốn update toàn bộ project
//            if (status_error.isEmpty()) {
//                ProjectDTO projectToUpdate = new ProjectDTO(id_value, name, description, status, estimated_launch_value);
//
//                // Nếu bạn chỉ muốn update status, thì chỉ gọi updateStatus
//                // Nếu bạn muốn update toàn bộ project, thì hàm update của DAO phải nhận đầy đủ DTO
//                if (pdao.updateStatus(id_value, status)) { // Giả sử updateStatus chỉ update 1 trường
//                    message = "Status updated successfully!";
//                    List<ProjectDTO> list = pdao.getAllProject();
//                    request.setAttribute("list", list);
//                    return WELCOME_PAGE;
//                } else {
//                    message = "Update failed!";
//                    ProjectDTO project = pdao.getProjectById(id_value);
//                    request.setAttribute("newProject", project); // <--- Chỗ này quan trọng: set vào newProject
//                    request.setAttribute("isChanging", true);
//                    request.setAttribute("message", message);
//                    request.setAttribute("status_error", status_error); // Truyền lỗi status nếu có
//                    return PROJECT_PAGE;
//                }
//            } else { // Có lỗi status
//                 message = "Update failed!";
//                 ProjectDTO project = pdao.getProjectById(id_value); // Lấy lại project để hiển thị
//                 request.setAttribute("newProject", project);
//                 request.setAttribute("isChanging", true);
//                 request.setAttribute("message", message);
//                 request.setAttribute("status_error", status_error);
//                 return PROJECT_PAGE;
//            }
//        } catch (NumberFormatException e) {
//            System.out.println("Error parsing project ID: " + e.getMessage());
//            request.setAttribute("message", "Invalid Project ID format!");
//            request.setAttribute("isChanging", true); // Vẫn ở chế độ edit
//            // Có thể cần lấy lại project nếu có ID hợp lệ ban đầu
//            return PROJECT_PAGE;
//        } catch (Exception e) {
//            System.out.println("Error updating: " + e.getMessage());
//            request.setAttribute("message", "An unexpected error occurred during update.");
//            request.setAttribute("isChanging", true); // Vẫn ở chế độ edit
//            return PROJECT_PAGE;
//        }
//    }
//    return LOGIN_PAGE;
//}

}

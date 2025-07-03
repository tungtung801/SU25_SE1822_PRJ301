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
import java.util.ArrayList;
import java.util.List;
import model.CategoryDAO;
import model.CategoryDTO;
import model.ExamDAO;
import model.ExamDTO;
import util.AuthUtil;

/**
 *
 * @author Tung Nguyen
 */
@WebServlet(name = "ExamController", urlPatterns = {"/ExamController"})
public class ExamController extends HttpServlet {

    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String EXAM_FORM = "examForm.jsp";
    ExamDAO edao = new ExamDAO();
    CategoryDAO cdao = new CategoryDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = WELCOME_PAGE;
        try {
            String action = request.getParameter("action");
            if ("viewExamByCate".equals(action)) {
                url = handleViewExamByCate(request, response);
            } else if ("addExam".equals(action)) {
                url = handleExamAddingByCate(request, response);
            } else if ("submitAddExam".equals(action)) {
                url = handleExamSubmitingByCate(request, response);
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

    private String handleViewExamByCate(HttpServletRequest request, HttpServletResponse response) {
        String url = WELCOME_PAGE;

        if (!AuthUtil.isLoggedIn(request)) {
            return LOGIN_PAGE;
        }

        String cate_keyword = request.getParameter("cate_keyword");
        String keyword_err = null;
        String viewMessage = null;

        if (cate_keyword == null || cate_keyword.trim().isEmpty()) {
            keyword_err = "Cannot view, please choose one category!";
        } else {
            try {
                int cate_id = Integer.parseInt(cate_keyword);
                List<ExamDTO> examList = edao.getExamByCate(cate_id);
                request.setAttribute("examList", examList);

                // luu lai keyword
                request.setAttribute("cate_id", cate_id);
            } catch (NumberFormatException e) {
                keyword_err = "Invalid category ID format!";
            }
        }

        showAllCategories(request, response);

        if (keyword_err != null) {
            request.setAttribute("keyword_err", keyword_err);
        }

        return url;
    }

    private String handleExamAddingByCate(HttpServletRequest request, HttpServletResponse response) {
        String url = EXAM_FORM;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }
        if (!AuthUtil.isInstructor(request)) {
            url = WELCOME_PAGE;
        }
        String category_id = request.getParameter("category_id");
        String category_id_err = "";

        if (category_id == null || category_id.trim().isEmpty()) {
            category_id_err = "Please choose one from the category list!";
        } else {
            try {
                int category_id_value = Integer.parseInt(category_id);
                request.setAttribute("cate_id", category_id_value);
                CategoryDTO category = null;
                category = cdao.getCateById(category_id_value);
                request.setAttribute("category", category);
            } catch (Exception e) {
                category_id_err = "Invalid category ID format!";
            }
        }
        showAllCategories(request, response);

        return url;
    }

    private String handleExamSubmitingByCate(HttpServletRequest request, HttpServletResponse response) {
        String url = EXAM_FORM;
        if (!AuthUtil.isLoggedIn(request)) {
            url = LOGIN_PAGE;
        }

        if (!AuthUtil.isInstructor(request)) {
            url = WELCOME_PAGE;
        }

        String cate_id = request.getParameter("cate_id");
        String exam_tittle = request.getParameter("exam_tittle");
        String subject = request.getParameter("subject");
        String total_marks = request.getParameter("total_marks");
        String duration = request.getParameter("duration");
        String addMessage = "";

        if ((cate_id == null || cate_id.trim().isEmpty())
                || (exam_tittle == null || exam_tittle.trim().isEmpty())
                || (subject == null || subject.trim().isEmpty())
                || (total_marks == null || total_marks.trim().isEmpty())
                || (duration == null || duration.trim().isEmpty())) {
            request.setAttribute("addMessage", "All fields are required, please check again!");
            return url;
        } else {
            try {
                int category_id_value = Integer.parseInt(cate_id);
                int total_marks_value = Integer.parseInt(total_marks);
                int duration_value = Integer.parseInt(duration);

                CategoryDTO category = null;
                category = cdao.getCateById(category_id_value);

                if (category != null) {
                    request.setAttribute("category", category);
                }

                ExamDTO newExam = new ExamDTO(exam_tittle, subject, category_id_value, total_marks_value, duration_value);
                boolean inserted = edao.addNewExam(newExam);

                if (inserted) {
                    request.setAttribute("addMessage", "New Exam submitted successfully!");
                } else {
                    request.setAttribute("addMessage", "Cannot submit new Exam to server!");
                }


            } catch (NumberFormatException e) {
                request.setAttribute("inputError", "Invalid format of category_id, total_marks, duration!");
            }
        }

        return url;
    }

    private List<CategoryDTO> showAllCategories(HttpServletRequest request, HttpServletResponse response) {
        cdao = new CategoryDAO();
        List<CategoryDTO> cateList = cdao.getAllCategories();
        request.setAttribute("cateList", cateList);
        return cateList;
    }
}

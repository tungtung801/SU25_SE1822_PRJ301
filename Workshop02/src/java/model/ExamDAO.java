/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.DbUtil;

/**
 *
 * @author Tung Nguyen
 */
public class ExamDAO {

    public ExamDAO() {
    }
    
    private static final String GET_ALL = "SELECT * FROM [dbo].[tblExams] e"
            + " JOIN [dbo].[tblExamCategories] ec ON ec.category_id = e.category_id";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<ExamDTO> getAllExam(){
        List<ExamDTO> examList = new ArrayList<>();
        ExamDTO exam = null;
        
        String sql = GET_ALL;
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                exam = new ExamDTO();
                exam.setExam_id(rs.getInt("exam_id"));
                exam.setExam_tittle(rs.getString("exam_tittle"));
                exam.setSubject(rs.getString("subject"));
                exam.setCategory_id(rs.getInt("category_id"));
                exam.setTotal_marks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("duration"));
                
                examList.add(exam);
            }
        } catch (Exception e) {
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return examList;
    }
    
    public List<ExamDTO> getExamByCate(int id){
        List<ExamDTO> examList = new ArrayList<>();
        ExamDTO exam = null;
        
        String sql = GET_ALL + " WHERE e.category_id = ?";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                exam = new ExamDTO();
                exam.setExam_id(rs.getInt("exam_id"));
                exam.setExam_tittle(rs.getString("exam_tittle"));
                exam.setSubject(rs.getString("subject"));
                exam.setCategory_id(rs.getInt("category_id"));
                exam.setTotal_marks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("duration"));
                
                examList.add(exam);
            }
        } catch (Exception e) {
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return examList;
    }
    
    public boolean addNewExam (ExamDTO newExam){
        boolean isAdded = false;
        
        String sql = "INSERT INTO [dbo].[tblExams] (exam_tittle, subject, category_id, total_marks, duration) VALUES (?,?,?,?,?)";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newExam.getExam_tittle());
            ps.setString(2, newExam.getSubject());
            ps.setInt(3, newExam.getCategory_id());
            ps.setInt(4, newExam.getTotal_marks());
            ps.setInt(5, newExam.getDuration());
            
            int rowAffected = ps.executeUpdate();
            isAdded = rowAffected > 0;
        } catch (Exception e) {
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return isAdded;
    }
}

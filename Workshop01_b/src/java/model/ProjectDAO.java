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
public class ProjectDAO {

    public ProjectDAO() {
    }
    
    private static final String GET_ALL = "SELECT * FROM tblStartupProjects";
    private static final String CREATE = "INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE tblStartupProjects SET status = ? WHERE project_id = ?";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<ProjectDTO> getAllProject(){
        List<ProjectDTO> projectList = new ArrayList<>();
        ProjectDTO project = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(GET_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
                project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated_launch(rs.getDate("estimated_launch"));
                
                projectList.add(project);
            }
        } catch (Exception e) {
            System.out.println("Cannot add project to list: "+e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return projectList;
    }
    
    public List<ProjectDTO> getProjectByName(String project_name){
        List<ProjectDTO> projectList = new ArrayList<>();
        ProjectDTO project = null;
        String query = GET_ALL + " WHERE project_name LIKE ?";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%"+project_name+"%");
            rs = ps.executeQuery();
            
            while(rs.next()){
                project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated_launch(rs.getDate("estimated_launch"));
                
                projectList.add(project);
            }
        } catch (Exception e) {
            System.out.println("Cannot add project to list: "+e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return projectList;
    }
    
    public ProjectDTO getProjectById(int project_id){
        ProjectDTO project = null;
        String query = GET_ALL + " WHERE project_id =  ?";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, project_id);
            rs = ps.executeQuery();
            
            while(rs.next()){
                project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated_launch(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            System.out.println("Cannot add project to list: "+e.getMessage());
            e.printStackTrace();
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return project;
    }
        
    public boolean addProject(ProjectDTO project){
       boolean isSuccess = false;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(CREATE);
            ps.setInt(1, project.getProject_id());
            ps.setString(2, project.getProject_name());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());
            ps.setDate(5, project.getEstimated_launch());
            
            int rowAffected = ps.executeUpdate();
            return isSuccess = rowAffected > 0;
        } catch (Exception e) {
            System.out.println("Error in add project");
        } finally {
            DbUtil.closeResources(conn, ps);
        }
        return isSuccess;
    }
    
    public boolean updateStatus(int project_id, String status){
        boolean isSuccess = false;
        ProjectDTO updateProject = getProjectById(project_id);
        if(updateProject != null){
            updateProject.setStatus(status);
            return isSuccess = updateProject(updateProject);
        }
        return isSuccess;
    }
    
    public boolean updateProject(ProjectDTO updateProject){
        boolean isSuccess = false;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(UPDATE);
            ps.setString(1, updateProject.getStatus());
            ps.setInt(2, updateProject.getProject_id());
            
            int rowAffected = ps.executeUpdate();
            return isSuccess = rowAffected > 0;
        } catch (Exception e) {
            System.out.println("Cannot update project to DB: "+e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps);
        }
        return isSuccess;
    }
}

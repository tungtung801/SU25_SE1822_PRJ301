/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import sun.security.pkcs11.Secmod;
import util.DbUtil;

/**
 *
 * @author Tung Nguyen
 */
public class ProjectDAO {

    public ProjectDAO() {
    }

    private static final String GET_ALL_PROJECT = "SELECT * FROM tblStartupProjects";
    private static final String ADD_PROJECT = "INSERT INTO tblStartupProjects (project_id, project_name, description, status, estimated_launch) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_PROJECT = "UPDATE tblStartupProjects SET status = ? WHERE project_id = ?";

    public List<ProjectDTO> getAllProject() {
        List<ProjectDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(GET_ALL_PROJECT);
            rs = ps.executeQuery();

            while (rs.next()) {
                ProjectDTO project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimated_launch(rs.getDate("estimated_launch"));
                list.add(project);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllProject(): " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return list;
    }

    public boolean addProject(ProjectDTO project) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(ADD_PROJECT);
            ps.setInt(1, project.getProject_id());
            ps.setString(2, project.getProject_name());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());
            ps.setDate(5, project.getEstimated_launch());

            int rowAffected = ps.executeUpdate();
            return isSuccess = (rowAffected > 0);
        } catch (Exception e) {
            System.out.println("Error in addProject(): " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps);
        }
        return isSuccess;
    }

    public ProjectDTO getProjectById(int project_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ProjectDTO project = null;
        String query = GET_ALL_PROJECT + " WHERE project_id = ?";

        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, project_id);
            rs = ps.executeQuery();

            if (rs.next()) {
                project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated_launch(rs.getDate("date"));
            }

        } catch (Exception e) {
            System.out.println("Error in addProject(): " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps);
        }
        return project;
    }

    public boolean isProjectExist(int project_id) {
        return getProjectById(project_id) != null;
    }

    public List<ProjectDTO> getProjectByName(String project_name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ProjectDTO> list = new ArrayList<>();
        String query = GET_ALL_PROJECT + " WHERE project_name LIKE ?"; // Sửa thành LIKE

        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + project_name + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                ProjectDTO project = new ProjectDTO();
                project.setProject_id(rs.getInt("project_id"));
                project.setProject_name(rs.getString("project_name"));
                project.setDescription(rs.getString("description"));
                project.setStatus(rs.getString("status"));
                project.setEstimated_launch(rs.getDate("estimated_launch"));

                list.add(project);
            }

        } catch (Exception e) {
            System.out.println("Error in getProjectByName(): " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return list;
    }

    public boolean updateStatus(int project_id, String status) {
        ProjectDTO project = getProjectById(project_id);
        if (project != null) {
            project.setStatus(status);
            return update(project);
        }
        return false;
    }

    public boolean update(ProjectDTO project) {
        boolean isSuccess = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(UPDATE_PROJECT);
            ps.setString(1, project.getStatus());
            ps.setInt(2, project.getProject_id());

            int rowAffected = ps.executeUpdate();
            return isSuccess = rowAffected > 0;

        } catch (Exception e) {
            System.out.println("Error in update(): " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps);
        }
        return isSuccess;
    }

}

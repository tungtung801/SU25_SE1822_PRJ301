/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DbUtil;

/**
 *
 * @author Tung Nguyen
 */
public class UserDAO {

    public UserDAO() {
    }

    private static final String GET_ALL_USER = "SELECT * FROM tblUsers";
    private static final String GET_USER_BY_USERNAME = GET_ALL_USER + " WHERE Username = ?";

    public UserDTO getUserByUsername(String username) {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(GET_USER_BY_USERNAME);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUsername(rs.getString("Username"));
                user.setName(rs.getString("Name"));
                user.setPassword(rs.getString("Password"));
                user.setRole(rs.getString("Role"));
            }
        } catch (Exception e) {
            System.out.println("Error in getUserByUsername: " + e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        return user;
    }
    
    public boolean login(String username, String password){
        UserDTO user = getUserByUsername(username);
        if(user != null){
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

}

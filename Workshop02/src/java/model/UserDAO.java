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
    
    private static final String WELCOME_PAGE = "welcome.jsp";
    private static final String GET_ALL = "SELECT * FROM [dbo].[tblUsers]";
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public UserDTO getUserByUsername(String username){
        UserDTO user = null;
        String sql = GET_ALL + " WHERE username = ?";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if(rs.next()){
                user = new UserDTO();
                user.setUsername(rs.getString("username"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
            }
            
        } catch (ClassNotFoundException | SQLException e) {
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

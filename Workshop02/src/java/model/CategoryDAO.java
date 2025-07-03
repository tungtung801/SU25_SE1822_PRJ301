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
public class CategoryDAO {

    public CategoryDAO() {
    }
    
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public List<CategoryDTO> getAllCategories(){
        List<CategoryDTO> cateList = new ArrayList<>();
        CategoryDTO category = null;
        String sql = "SELECT * FROM [dbo].[tblExamCategories]";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                category = new CategoryDTO();
                category.setCategory_id(rs.getInt("category_id"));
                category.setCategory_name(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
                
                cateList.add(category);
            }
        } catch (Exception e) {
            System.out.println("error in get all category(): "+e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        
        return cateList;
    }
    
    public CategoryDTO getCateById(int category_id){
        CategoryDTO category = null;
        String sql = "SELECT * FROM [dbo].[tblExamCategories] WHERE category_id = ?";
        
        try {
            conn = DbUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, category_id);
            rs = ps.executeQuery();
            
            if(rs.next()){
                category = new CategoryDTO();
                category.setCategory_id(rs.getInt("category_id"));
                category.setCategory_name(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            System.out.println("error in get all category(): "+e.getMessage());
        } finally {
            DbUtil.closeResources(conn, ps, rs);
        }
        
        return category;
    }
    
}

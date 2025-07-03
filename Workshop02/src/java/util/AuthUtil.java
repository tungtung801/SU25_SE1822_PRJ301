/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import model.UserDTO;

/**
 *
 * @author Tung Nguyen
 */
public class AuthUtil {

    public AuthUtil() {
    }
    
    public static UserDTO getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserDTO currentUser = null;
        if(session != null){
            currentUser = (UserDTO)session.getAttribute("user");
        }
        return currentUser;
    }
    
    public static boolean isLoggedIn(HttpServletRequest request){
        return getCurrentUser(request) != null;
    }
    
    public static boolean hasRole(HttpServletRequest request, String role){
        return getCurrentUser(request).getRole().equals(role);
    }
    
    public static boolean isInstructor(HttpServletRequest request){
        return hasRole(request, "Instructor");
    }
    
    public static boolean isStudent(HttpServletRequest request){
        return hasRole(request, "Student");
    }
}

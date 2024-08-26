package Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class CloseUtil {
    public static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } 
            catch(SQLException e) { e.printStackTrace(); }
        }
    }

    public static void close(PreparedStatement ps) {
        if(ps != null) {
            try { ps.close(); } 
            catch(SQLException e) { e.printStackTrace(); }
        }
    }

    public static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } 
            catch(SQLException e) { e.printStackTrace(); }
        }
    }
}

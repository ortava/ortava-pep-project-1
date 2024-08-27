package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.CloseUtil;
import Util.ConnectionUtil;


public class AccountDAO {
    public Account insertAccount(Account account) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int generatedAccountId = rs.getInt("account_id");
                return new Account(generatedAccountId, account.getUsername(), account.getPassword());
            }            
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(rs);
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return null;
    }

    public Account getAccountByUserName(String username) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            rs = ps.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(rs);
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return null;
    }

    public Account getAccountByAccountId(int accountId) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(rs);
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return null;
    }
}

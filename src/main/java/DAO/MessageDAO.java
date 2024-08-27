package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.CloseUtil;
import Util.ConnectionUtil;

public class MessageDAO {
    /**
     * @param message The new message to be added (not including message_id).
     * @return The newly added message with its generated message_id. Return null if message could not be inserted.
     */
    public Message insertMessage(Message message) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "INSERT INTO message "
                        + "(posted_by, message_text, time_posted_epoch) "
                        + "VALUES (?, ?, ?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int generatedMessageId = rs.getInt("message_id");
                return new Message(
                    generatedMessageId,
                    message.getPosted_by(), 
                    message.getMessage_text(), 
                    message.getTime_posted_epoch()
                    );
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

    /**
     * @return A list of all the message in the database.
     */
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM message;";
            ps = conn.prepareStatement(sql);

            rs = ps.executeQuery();
            while(rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")
                    ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(rs);
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return messages;
    }

    /**
     * @param messageId The ID of the message to be retrieved from the database.
     * @return The identified message. Return null if there is no matching message.
     */
    public Message getMessage(int messageId) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);

            rs = ps.executeQuery();
            if(rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")
                    );
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

    /**
     * @param messageId The ID of the message to be deleted from the database.
     * @return The number of rows affected by the delete operation.
     * Should be 1 if successful, 0 if no deletion occurred.
     */
    public int deleteMessageByMessageId(int messageId) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM message WHERE message_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, messageId);

            return ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return 0;
    }

    /**
     * @param messageId The ID of the message to be updated.
     * @param messageText The new message text.
     * @return The number of rows affected by the update operation.
     * Should be 1 if successful, 0 if no update occurred.
     */
    public int updateMessageText(int messageId, String messageText) {
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            ps = conn.prepareStatement(sql);
            ps.setString(1, messageText);
            ps.setInt(2, messageId);

            return ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return 0;
    }

    /**
     * @param accountId The ID of the account who's messages will be retrieved from the database.
     * @return A list of messages authored by the account with the given ID.
     */
    public List<Message> getAllMessagesByAccount(int accountId) {
        List<Message> messages = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch "
                        + "FROM message INNER JOIN account ON posted_by = account_id "
                        + "WHERE account_id = ?;";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            rs = ps.executeQuery();
            while(rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch")
                    ));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(rs);
            CloseUtil.close(ps);
            CloseUtil.close(conn);
        }
        return messages;
    }
}

package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }
    
    /**
     * @param message The new message to be added (not including message_id).
     * @return If successful, the newly added message (including message_id). Otherwise, return null.
     */
    public Message addMessage(Message message) {
        if(!message.getMessage_text().isBlank()
        && message.getMessage_text().length() <= 255
        && accountDAO.getAccountByAccountId(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }
    
    /**
     * @return A list of all message in the database.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * @param messageId The ID of the message to be retrieved.
     * @return The message identified by it's ID.
     */
    public Message getMessage(int messageId) {
        return messageDAO.getMessage(messageId);
    }

    /**
     * @param messageId The ID of the message to be deleted.
     * @return If successful, the now-deleted message. Otherwise, return null.
     */
    public Message removeMessage(int messageId) {
        Message deletedMessage = getMessage(messageId);
        if(messageDAO.deleteMessageByMessageId(messageId) == 1) {
            return deletedMessage;
        } else {
            return null;
        }
    }

    /**
     * @param messageId The ID of the message to be updated.
     * @param messageText The new message text.
     * @return If successful, the updated message. Otherwise, return null.
     */
    public Message updateMessageText(int messageId, String messageText) {
        if(!messageText.isBlank()
        && messageText.length() <= 255
        && messageDAO.updateMessageText(messageId, messageText) == 1) {
            return messageDAO.getMessage(messageId);
        } else {
            return null;
        }
    }

    /**
     * @param accountId The ID of the account who's messages will be retrieved.
     * @return A list of messages authored by the account with the given ID.
     */
    public List<Message> getAllMessagesByAccount(int accountId) {
        return messageDAO.getAllMessagesByAccount(accountId);
    }
}

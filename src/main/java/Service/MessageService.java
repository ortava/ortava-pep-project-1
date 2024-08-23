package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO; // TODO: Ask - Should I avoid including an AccountDAO in the MessageService?

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public Message addMessage(Message message) {
        if(!message.getMessage_text().isEmpty()
        && message.getMessage_text().length() <= 255
        && accountDAO.getAccountById(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }
    
    // TODO: Ask - Common method names in different classes... recommendations on different names?
    // (retrieveAllMessages)?
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    // TODO: Same problem - identical method names in different classes.
    public Message getMessageByMessageId(int id) {
        return messageDAO.getMessageByMessageId(id);
    }

    public Message removeMessageByMessageId(int id) {
        Message deletedMessage = getMessageByMessageId(id);
        if(messageDAO.deleteMessageByMessageId(id) == 1) {
            return deletedMessage;
        } else {
            return null;
        }
    }
}

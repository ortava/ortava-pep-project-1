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

    public Message addMessage(Message message) {
        if(!message.getMessage_text().isBlank()
        && message.getMessage_text().length() <= 255
        && accountDAO.getAccountById(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

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

    public Message updateMessageTextByMessageId(int id, String messageText) {
        if(!messageText.isBlank()
        && messageText.length() <= 255
        && messageDAO.updateMessageTextByMessageId(id, messageText) == 1) {
            return messageDAO.getMessageByMessageId(id);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessagesByAccountId(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }
}

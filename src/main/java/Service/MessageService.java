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
        && accountDAO.getAccountByAccountId(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }
    }
    
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int messageId) {
        return messageDAO.getMessage(messageId);
    }

    public Message removeMessage(int messageId) {
        Message deletedMessage = getMessage(messageId);
        if(messageDAO.deleteMessageByMessageId(messageId) == 1) {
            return deletedMessage;
        } else {
            return null;
        }
    }

    public Message updateMessageText(int messageId, String messageText) {
        if(!messageText.isBlank()
        && messageText.length() <= 255
        && messageDAO.updateMessageText(messageId, messageText) == 1) {
            return messageDAO.getMessage(messageId);
        } else {
            return null;
        }
    }

    public List<Message> getAllMessagesByAccount(int accountId) {
        return messageDAO.getAllMessagesByAccount(accountId);
    }
}

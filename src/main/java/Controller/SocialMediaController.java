package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    ObjectMapper mapper;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
        mapper = new ObjectMapper();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByMessageId);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageId);
        app.patch("/messages/{message_id}", this::patchMessageTextByMessageId);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountId);

        return app;
    }

    /** 
     * Requirement #1
     * Handler to register a new account.
     * @param context handles information from HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
     */
    private void postRegisterAccountHandler(Context context) throws JsonProcessingException {
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null) {
            context.json(mapper.writeValueAsString(registeredAccount));
        } else {
            context.status(400);
        }
    }

    /** 
     * Requirement #2
     * Handler to process user logins.
     * @param context handles information from HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
     */
    private void postLoginHandler(Context context) throws JsonProcessingException {
        Account account = mapper.readValue(context.body(), Account.class);
        Account existingAccount = accountService.login(account);
        if(existingAccount != null) {
            context.json(mapper.writeValueAsString(existingAccount));
        } else {
            context.status(401);
        }
    }

    /**
     * Requirement #3
     * Handler to create a new message.
     * @param context handles information from HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
     */
    private void postCreateMessageHandler(Context context) throws JsonProcessingException {
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.addMessage(message);
        if(postedMessage != null) {
            context.json(mapper.writeValueAsString(postedMessage));
        } else {
            context.status(400);
        }
    }

    /**
     * Requirement #4
     * Handler to get all messages.
     * @param context handles information from HTTP requests and generates responses within Javalin
     */
    private void getAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

    /**
     * Requirement #5
     * Handler to get a message identified by its message_id.
     * @param context handles information from HTTP requests and generates responses within Javalin
     */
    private void getMessageByMessageId(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessage(messageId);
        if(message != null) {
            context.json(message);
        }
    }

    /**
     * Requirement #6
     * Handler to delete a message identified by its message_id.
     * @param context handles information from HTTP requests and generates responses within Javalin
     */
    private void deleteMessageByMessageId(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.removeMessage(messageId);
        if(message != null) {
            context.json(message);
        }
    }

    /**
     * Requirement #7
     * Handler to update a message text identified by its message_id.
     * @param context handles information from HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException if there is an issue converting JSON into an object.
     */
    private void patchMessageTextByMessageId(Context context) throws JsonProcessingException {
        String messageText = mapper.readTree(context.body())
                                    .get("message_text")
                                    .asText();
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageText(messageId, messageText);
        if(updatedMessage != null) {
            context.json(updatedMessage);
        } else {
            context.status(400);
        }
    }

    /**
     * Requirement #8
     * Handler to get all messages by a particular user identified by their account_id.
     * @param context handles information from HTTP requests and generates responses within Javalin
     */
    private void getAllMessagesByAccountId(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesByAccount(accountId));
    }
}
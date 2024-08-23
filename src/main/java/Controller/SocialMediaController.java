package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);

        return app;
    }

    private void postRegisterHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = context.body();
        Account account = mapper.readValue(jsonString, Account.class);
        Account registered = accountService.registerAccount(account);
        if(registered == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(registered));
            context.status(200);
        }
    }

    private void postLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = context.body();
        Account account = mapper.readValue(jsonString, Account.class);
        Account existing = accountService.login(account);
        if(existing == null) {
            context.status(401);
        } else {
            context.json(mapper.writeValueAsString(existing));
            context.status(200);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = context.body();
        Message message = mapper.readValue(jsonString, Message.class);
        Message postedMessage = messageService.addMessage(message);
        if(postedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(postedMessage));
            context.status(200);
        }
    }


}
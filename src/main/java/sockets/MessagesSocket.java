package sockets;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.JsonWebTokenCache;
import memcach.MessageSocketCache;
import model.account.Account;
import java.util.UUID;
import sockets.service_socket.SocketHandler;
import test.TestLog;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/messages-socket/{token}")
public class MessagesSocket {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();
    private MessageSocketCache cache = MessageSocketCache.getInstance();

    private SocketHandler handler = new SocketHandler ();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        testLog.sendToConsoleMessage("token : "+token);
        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) {
                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
                cache.addAccount(session, account.getId());
            }else{
                closeConnection(session, "204");
            }
        }else{
            closeConnection(session, "204");
        }
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("Connection with user: "+session+" is closed!");
        cache.deleteAccount(session);
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }

    @OnMessage
    public void onMessage(String j_message, Session session){

        testLog.sendToConsoleMessage("#INFO [SOCKET] message from the client: " + j_message);
//      id_message  id_dialog   id_outcoming_account    date_time  message  is_last

        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject)jsonParser.parse(j_message);
        String type = object.get("type").getAsString();
        JsonObject data = object.get("data").getAsJsonObject();
        if (type.equals("message")){
            handler.messageHandler(j_message, data);
        }
        if (type.equals("user_read")){
            handler.setMessageDialogAsRead(data);
        }

    }

    private void closeConnection(Session session, String reason){
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "204"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

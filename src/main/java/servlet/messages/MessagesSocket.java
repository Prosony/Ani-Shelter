package servlet.messages;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.JsonWebTokenCache;
import model.account.Account;
import test.TestLog;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/messages-socket/{token}")
public class MessagesSocket {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private static Map<Session, String> peers =  new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        testLog.sendToConsoleMessage("token : "+token);
        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) {
                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
                peers.put(session,token);
            }else{
                closeConnection(session, "204");
            }
        }else{
            closeConnection(session, "204");
        }

    }

    @OnClose
    public void onClose(Session peer){
        System.out.println("Connection with user: "+peer+" is closed!");
        peers.remove(peer);
    }

    @OnError
    public void onError(Throwable e){
        e.printStackTrace();
    }

    @OnMessage
    public String onMessage(String message, Session session){
        testLog.sendToConsoleMessage("#INFO [SOCKET] message from the client: " + message);

        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject)jsonParser.parse(message);
        String token = object.get("outcoming_token").getAsString();

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) {
                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
            }
        }
        return message;
    }

    private void closeConnection(Session session, String reason){
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "204"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

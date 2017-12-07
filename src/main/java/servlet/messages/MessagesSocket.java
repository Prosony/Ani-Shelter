package servlet.messages;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.JsonWebTokenCache;
import model.account.Account;
import model.message.Dialog;
import model.message.Messages;
import services.db.SelectQueryDB;
import test.TestLog;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/messages-socket/{token}")
public class MessagesSocket {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private static Map<Session, UUID> mapIdAccountBySessionSocket =  new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token){
        testLog.sendToConsoleMessage("token : "+token);
        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) {
                testLog.sendToConsoleMessage("#INFO [SOCKET] outcoming_account: "+account.getId());
                mapIdAccountBySessionSocket.put(session, account.getId());
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
        mapIdAccountBySessionSocket.remove(session);
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
        String idMessage = object.get("id_message").getAsString();
        String idDialog = object.get("id_dialog").getAsString();
        String idOutcomingAccount = object.get("id_outcoming_account").getAsString();
        Timestamp dateTime = Timestamp.valueOf(object.get("date_time").getAsString());
        String message = object.get("message").getAsString();
        String idLast = object.get("is_last").getAsString();

        UUID idDialogUUID = UUID.fromString(idDialog);
        Messages messages = new Messages(UUID.fromString(idMessage), idDialogUUID, UUID.fromString(idOutcomingAccount), dateTime, message);

        SelectQueryDB selectQueryDB = new SelectQueryDB();
        Dialog dialog = selectQueryDB.getDialogByIdDialog(idDialog);

        if (dialog != null){
            if (dialog.getIdOutcomingAccount().equals(UUID.fromString(idOutcomingAccount))){
                sendMessageByIdAccount(dialog.getIdIncomingAccount(), j_message);
            }else{
                sendMessageByIdAccount(dialog.getIdOutcomingAccount(), j_message);
            }
        }else{
            testLog.sendToConsoleMessage("#INFO [SOCKET] [onMessage: ERROR] dialog not found!");
        }
    }

    private void closeConnection(Session session, String reason){
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "204"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageByIdAccount(UUID idIncoming, String j_message){
        Session incoming = getSessionByIdAccount(mapIdAccountBySessionSocket.keySet(), idIncoming);
        if (incoming != null) {
            try {
                testLog.sendToConsoleMessage("#INFO [SOCKET] [sendMessageByIdAccount] send to account: "+idIncoming+", Session: "+incoming);
                incoming.getBasicRemote().sendText(j_message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            testLog.sendToConsoleMessage("#INFO [SOCKET] [sendMessageBySession: ERROR] session not found, may be user offline!");
        }
    }

    private Session getSessionByIdAccount(Set<Session> list, UUID idAccount){
        Session result = null;
        for (Session session : list){
            testLog.sendToConsoleMessage("#INFO [SOCKET] [getSessionByIdAccount] session"+ session +" idAccoount: "+mapIdAccountBySessionSocket.get(session));
        }
        for (Session session : list){
            if (idAccount.equals(mapIdAccountBySessionSocket.get(session))){
                testLog.sendToConsoleMessage("idAccount = mapIdAccountBySessionSocket.get(session)");
                testLog.sendToConsoleMessage(idAccount +" "+mapIdAccountBySessionSocket.get(session));
                result = session;
            }
        }
        return result;
    }
}

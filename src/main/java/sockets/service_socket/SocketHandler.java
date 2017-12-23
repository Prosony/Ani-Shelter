package sockets.service_socket;

import com.google.gson.JsonObject;
import memcach.MessageSocketCache;
import model.message.Dialog;
import model.message.Messages;
import services.db.InsertQueryDB;
import services.db.SelectQueryDB;
import test.TestLog;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

public class SocketHandler {

    private InsertQueryDB db = new InsertQueryDB();
    private TestLog testLog = TestLog.getInstance();
    private MessageSocketCache cache = MessageSocketCache.getInstance();

    public void messageHandler(String j_message, JsonObject jsonMessage){

        String idMessage = jsonMessage.get("id_message").getAsString();
        String idDialog = jsonMessage.get("id_dialog").getAsString();
        String idOutcomingAccount = jsonMessage.get("id_outcoming_account").getAsString();
        Timestamp dateTime = new Timestamp(jsonMessage.get("date_time").getAsLong());
        String message = jsonMessage.get("message").getAsString();
        boolean isRead = jsonMessage.get("is_read").getAsBoolean();
        UUID idDialogUUID = UUID.fromString(idDialog);
        Messages messages = new Messages(UUID.fromString(idMessage), idDialogUUID, UUID.fromString(idOutcomingAccount), dateTime, message, isRead);

        SelectQueryDB selectQueryDB = new SelectQueryDB();
        Dialog dialog = selectQueryDB.getDialogByIdDialog(idDialog);

        if (dialog != null){

            boolean add = db.insertMessage(messages);
            if (dialog.getIdOutcomingAccount().equals(UUID.fromString(idOutcomingAccount))){

                if (add){
                    sendMessageByIdAccountIncoming(dialog.getIdIncomingAccount(), j_message);
                }else{
                    testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessage: ERROR] messages was not add!");
                }
            }else{
                if (add) {
                    sendMessageByIdAccountIncoming(dialog.getIdOutcomingAccount(), j_message);
                }else{
                    testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessage: ERROR] messages was not add!");
                }
            }
        }else{
            testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessage: ERROR] dialog not found!");
        }
    }
    public void setMessageDialogAsRead(JsonObject data){
        String idDialog = data.get("id_dialog").getAsString();
        String idReader = data.get("id_reader").getAsString();
        
    }
    private void sendMessageByIdAccountIncoming(UUID idIncoming, String j_message){
        Session incoming = getSessionByIdAccount(cache.getAllSession(), idIncoming);
        if (incoming != null) {
            try {
                testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessageByIdAccountIncoming] send to account: "+idIncoming+", Session: "+incoming);
                incoming.getBasicRemote().sendText(j_message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessageBySession: ERROR] session not found, may be user offline!");
        }
    }

    private Session getSessionByIdAccount(Set<Session> list, UUID idAccount){
        Session result = null;
        cache.printAllOnline();
        for (Session session : list){
            if (idAccount.equals(cache.getAccountBySessionSocket(session))){
                testLog.sendToConsoleMessage("#INFO [MessageHandler] [getSessionByIdAccount] idAccount = mapIdAccountBySessionSocket.get(session)");
                result = session;
            }
        }
        return result;
    }

}

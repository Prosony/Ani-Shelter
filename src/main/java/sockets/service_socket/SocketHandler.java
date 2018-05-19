package sockets.service_socket;

import com.google.gson.JsonObject;
import memcache.SocketCache;
import model.message.Dialog;
import model.message.Messages;
import services.db.InsertQueryDB;
import services.db.SelectQueryDB;
import services.db.UpdateQueryDB;
import test.TestLog;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class SocketHandler {

    private InsertQueryDB insertDB = new InsertQueryDB();
    private TestLog testLog = TestLog.getInstance();
    private SocketCache cache = SocketCache.getInstance();
    private UpdateQueryDB updateDB = new UpdateQueryDB();

    public void messageHandler(String j_message, JsonObject jsonMessage){

        String idMessage = jsonMessage.get("id_message").getAsString();
        String idDialog = jsonMessage.get("id_dialog").getAsString();
        String idOutcomingAccount = jsonMessage.get("id_outcoming_account").getAsString();
        Timestamp dateTime = new Timestamp(jsonMessage.get("date").getAsLong());
        String message = jsonMessage.get("message").getAsString();
        boolean isRead = jsonMessage.get("is_read").getAsBoolean();
        UUID idDialogUUID = UUID.fromString(idDialog);

        Messages messages = new Messages(UUID.fromString(idMessage), idDialogUUID, UUID.fromString(idOutcomingAccount), dateTime, message, isRead);

        SelectQueryDB selectQueryDB = new SelectQueryDB();
        Dialog dialog = selectQueryDB.getDialogByIdDialog(idDialog);

        if (dialog != null){
            boolean add = insertDB.insertMessage(messages);
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

    /**
     * Sets the message as read
     * @param j_package
     * @param data
     */
    public void setMessageDialogAsRead(String j_package,JsonObject data){
        String idDialog = data.get("id_dialog").getAsString();
        String idReader = data.get("id_reader").getAsString();
        //TODO set to db isRead - true
        UpdateQueryDB update = new UpdateQueryDB();
        boolean isRead = update.updateStatusReadMessage(idDialog,idReader);

        if(isRead){
            SelectQueryDB select = new SelectQueryDB();
            Dialog dialog = select.getDialogByIdDialog(idDialog);
            UUID id;
            if (dialog.getIdOutcomingAccount().equals(UUID.fromString(idReader))){
                id = dialog.getIdIncomingAccount();
            }else{
                id = dialog.getIdOutcomingAccount();
            }
            ArrayList<Session> list = cache.getAllSessionById(id);
            if (list != null){
                for (Session session : list){
                    if (session != null && session.isOpen()){
                        try {
                            session .getBasicRemote().sendText(j_package);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }
    }


    private void sendMessageByIdAccountIncoming(UUID idIncoming, String j_message){ //Must send package to all
        ArrayList<Session> list = cache.getAllSessionById(idIncoming);
        if (list != null){
            for (Session session : list){
                if (session != null && session.isOpen()) {
                    try {
                        testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessageByIdAccountIncoming] send to account: "+idIncoming+", Session: "+session);
                        session.getBasicRemote().sendText(j_message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    testLog.sendToConsoleMessage("#INFO [MessageHandler] [sendMessageBySession: ERROR] session not found, may be user offline!");
                }
            }
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

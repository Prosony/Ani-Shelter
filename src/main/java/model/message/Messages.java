package model.message;

import java.sql.Timestamp;
import java.util.UUID;

public class Messages {
    private UUID idMessage;
    private UUID idDialog;
    private UUID idOutcomingAccount;

    private Timestamp date;
    private String message;
    private boolean isRead;

    public Messages(UUID idMessage, UUID idDialog, UUID idOutcomingAccount, Timestamp date, String message, boolean isRead) {
        this.idMessage = idMessage;
        this.idDialog = idDialog;
        this.idOutcomingAccount = idOutcomingAccount;
        this.date = date;
        this.message = message;
        this.isRead = isRead;
    }

    public UUID getIdMessage() {
        return idMessage;
    }
    public void setIdMessage(UUID idMessage) {
        this.idMessage = idMessage;
    }

    public UUID getIdDialog() {
        return idDialog;
    }
    public void setIdDialog(UUID idDialog) {
        this.idDialog = idDialog;
    }

    public UUID getIdOutcomigAccount() {
        return idOutcomingAccount;
    }
    public void setIdOutcomigAccount(UUID idOutcomigAccount) {
        this.idOutcomingAccount = idOutcomigAccount;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getData() {
        return date;
    }
    public void setData(Timestamp date) {
        this.date = date;
    }

    public boolean getIsRead() {
        return isRead;
    }
    public void setIsRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "{\n" +
                "idMessage:" +getIdMessage()+"\n"+
                "idDialog: " +getIdDialog()+"\n"+
                "idOutcomingAccount: " +getIdOutcomigAccount()+"\n"+
                "date : " +getData()+"\n"+
                "message: "+getMessage()+"\n" +
                "isRead: "+getIsRead()+"\n" +
                "}";
    }


}

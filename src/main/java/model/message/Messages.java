package model.message;

import java.sql.Timestamp;
import java.util.UUID;

public class Messages {
    private UUID idMessage;
    private UUID idDialog;
    private UUID idOutcomigAccount;

    private Timestamp date;
    private String message;

    public Messages(UUID idMessage, UUID idDialog, UUID idOutcomigAccount, Timestamp date, String message) {
        this.idMessage = idMessage;
        this.idDialog = idDialog;
        this.idOutcomigAccount = idOutcomigAccount;
        this.date = date;
        this.message = message;
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
        return idOutcomigAccount;
    }
    public void setIdOutcomigAccount(UUID idOutcomigAccount) {
        this.idOutcomigAccount = idOutcomigAccount;
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


}

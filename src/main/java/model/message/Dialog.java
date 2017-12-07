package model.message;

import java.util.UUID;

public class Dialog {

    private UUID idDialog;
    private UUID idOutcomingAccount;
    private UUID idIncomingAccount;

    public Dialog(UUID idDialog, UUID idOutcomingAccount, UUID idIncomingAccount){
        this.idDialog = idDialog;
        this.idOutcomingAccount = idOutcomingAccount;
        this.idIncomingAccount = idIncomingAccount;
    }

    public UUID getIdDialog() {
        return idDialog;
    }
    public void setIdDialog(UUID idDialog) {
        this.idDialog = idDialog;
    }

    public UUID getIdOutcomingAccount() {
        return idOutcomingAccount;
    }
    public void setIdOutcomingAccount(UUID idOutcomingAccount) {
        this.idOutcomingAccount = idOutcomingAccount;
    }

    public UUID getIdIncomingAccount() {
        return idIncomingAccount;
    }
    public void setIdIncomingAccount(UUID idIncomingAccount) {
        this.idIncomingAccount = idIncomingAccount;
    }

    @Override
    public String toString() {
        return "{\n" +
                "idDialog: "+idDialog.toString()+"\n" +
                "idOutcomingAccount: "+idOutcomingAccount.toString()+"\n" +
                "idIncomingAccount: "+idIncomingAccount.toString()+"\n" +
                "}";
    }
}

package model.message;

public class Dialog {
    private int id_this_dialog;
    private int id_from_account;
    private int id_to_account;

    public int getId_this_dialog() {
        return id_this_dialog;
    }
    public void setId_this_dialog(int id_this_dialog) {
        this.id_this_dialog = id_this_dialog;
    }


    public int getId_from_account() {
        return id_from_account;
    }
    public void setId_from_account(int id_from_account) {
        this.id_from_account = id_from_account;
    }

    public int getId_to_account() {
        return id_to_account;
    }
    public void setId_to_account(int id_to_account) {
        this.id_to_account = id_to_account;
    }


    @Override
    public String toString() {
        return "id_this_dialog: "+ id_this_dialog +" id_from_account: "+ id_from_account +" id_to_account: "+ id_to_account;
    }

}

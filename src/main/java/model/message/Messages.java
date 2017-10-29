package model.message;

public class Messages {
    private int id_this_message;
    private int id_dialog;
    private String message;
    private String time;


    public int getId_dialog() {
        return id_dialog;
    }
    public void setId_dialog(int id_dialog) {
        this.id_dialog = id_dialog;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }


    public int getId_this_message() {
        return id_this_message;
    }
    public void setId_this_message(int id_this_message) {
        this.id_this_message = id_this_message;
    }

    @Override
    public String toString() {
        return "id_this_message: "+ id_this_message +" message: "+ message +" "+ time;
    }
}

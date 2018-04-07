package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.message.Messages;
import test.TestLog;

import java.sql.*;
import java.util.UUID;

public class InsertQueryDB {

    private TestLog log = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    /**************************************************************************************************
     *                                          POST AD
     **************************************************************************************************/

    public void insertPostAd(UUID idPostAd, UUID idAccount, JsonObject jsonText, JsonObject jsonTags, JsonArray jsonPathImage, Timestamp timestamp){
        Connection connection = dataBaseService.retrieve();
        try {
            PreparedStatement aboutUpdate = connection.prepareStatement("insert into post_ad(id, id_account, json_text, json_tags, json_path_image, json_path_avatar, timestamp) " +
                    "VALUES (?,?,?,?,? ,?,?);");

            aboutUpdate.setString(1, String.valueOf(idPostAd));
            aboutUpdate.setString(2, String.valueOf(idAccount));
            aboutUpdate.setString(3, String.valueOf(jsonText));
            aboutUpdate.setString(4, String.valueOf(jsonTags));
            aboutUpdate.setString(5, String.valueOf(jsonPathImage));

            JsonArray jsonPathAvatar = new JsonArray();
            jsonPathAvatar.add(jsonPathImage.get(0));
            jsonPathAvatar.add(jsonPathImage.get(1));

            aboutUpdate.setString(6, String.valueOf(jsonPathAvatar));
            aboutUpdate.setTimestamp(7, timestamp);
            aboutUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.putback(connection);
    }
    /**************************************************************************************************
     *                                          Messages
     **************************************************************************************************/
    public boolean insertMessage(Messages message){
        Connection connection = dataBaseService.retrieve();
        try {
            PreparedStatement insertMessage = connection.prepareStatement("insert into messages(id_message, id_dialog, id_outcoming_account, timestamp, message, is_read) " +
                    "VALUES (?,?,?,?,?,?);");
            insertMessage.setString(1, String.valueOf(message.getIdMessage()));
            insertMessage.setString(2, String.valueOf(message.getIdDialog()));
            insertMessage.setString(3, String.valueOf(message.getIdOutcomigAccount()));
            insertMessage.setTimestamp(4, message.getData());
            insertMessage.setString(5, message.getMessage());
            insertMessage.setBoolean(6, message.getIsRead());
            insertMessage.executeUpdate();
            log.sendToConsoleMessage("#INFO [InsertQueryDB][insertMessage] [SUCCESS] message add to DB, idMessage: "+message.getIdMessage());
            dataBaseService.putback(connection);
            return true;
        } catch (SQLException e) {
            dataBaseService.putback(connection);
            e.printStackTrace();
        }
        return false;
    }
    /**************************************************************************************************
     *                                          Dialog
     **************************************************************************************************/
    public String insertDialog(String idAccountOutcoming, String idAccountIncoming){
        Connection connection = dataBaseService.retrieve();
        try {
            PreparedStatement insert = connection.prepareStatement("insert into dialogs(id_dialogs, id_account_outcoming, id_account_incoming) VALUES (?,?,?)");
            String idDialog = String.valueOf(UUID.randomUUID());
            insert.setString(1,idDialog);
            insert.setString(2,idAccountOutcoming);
            insert.setString(3,idAccountIncoming);
            insert.executeUpdate();
            return idDialog;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.putback(connection);
        return null;
    }
    /**************************************************************************************************
     *                                          Account
     **************************************************************************************************/
    public void insertAccount(String email ,String password, String name, String surname, String phone, String birthday, String pathToAvatar, String about, Timestamp dateCreateAccount){
        Connection connection = dataBaseService.retrieve();
        try {
            PreparedStatement insert = connection.prepareStatement("insert into account(id_account, email, password) VALUES (?,?,?)");
            String idAccount = String.valueOf(UUID.randomUUID());
            insert.setString(1, idAccount);
            insert.setString(2, email);
            insert.setString(3, password);
            int result = insert.executeUpdate();
            insert = connection.prepareStatement("INSERT INTO profile(id, date_create_account, name, surname, email, phone, birthday, path_avatar, about) VALUES " +
                    "(?,?,?,?,?,?,?,?,?)");
            insert.setString(1,idAccount);
            insert.setTimestamp(2, dateCreateAccount);
            insert.setString(3,name);
            insert.setString(4,surname);
            insert.setString(5,email);
            insert.setString(6,phone);
            insert.setString(7,birthday);
            insert.setString(8,pathToAvatar);
            insert.setString(9,about);
            insert.executeUpdate();
            log.sendToConsoleMessage("#INFO [InsertQueryDB][insertAccount] [SUCCESS] id account: "+idAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.putback(connection);
    }
}

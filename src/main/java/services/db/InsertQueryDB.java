package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import model.message.Messages;
import test.TestLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class InsertQueryDB {

    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    /**************************************************************************************************
     *                                          POST AD
     **************************************************************************************************/

    public void insertPostAd(UUID idPostAd, UUID idAccount, JsonObject jsonText, JsonObject jsonTags, JsonArray jsonPathImage, Timestamp timestamp){
        try {
            Connection connection = dataBaseService.retrieve();

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

            dataBaseService.putback(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**************************************************************************************************
     *                                          Messages
     **************************************************************************************************/
    public boolean insertMessage(Messages message){
        try {
            Connection connection = dataBaseService.retrieve();

            PreparedStatement insertMessage = connection.prepareStatement("insert into messages(id_message, id_dialog, id_outcoming_account, timestamp, message, is_read) " +
                    "VALUES (?,?,?,?,?,?);");
            insertMessage.setString(1, String.valueOf(message.getIdMessage()));
            insertMessage.setString(2, String.valueOf(message.getIdDialog()));
            insertMessage.setString(3, String.valueOf(message.getIdOutcomigAccount()));
            insertMessage.setTimestamp(4, message.getData());
            insertMessage.setString(5, message.getMessage());
            insertMessage.setBoolean(6, message.getIsRead());
            insertMessage.executeUpdate();
            testLog.sendToConsoleMessage("#INFO []InsertQueryDB] [insertMessage] [SUCCESS] message add to DB, idMessage: "+message.getIdMessage());
            dataBaseService.putback(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

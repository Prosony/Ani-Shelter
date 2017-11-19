package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import test.TestLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

public class InsertQueryDB {
    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public void insertPostAd(UUID idPostAd, UUID idAccount, JsonObject jsonText, JsonObject jsonTags, JsonArray jsonPathImage){
        try {
            Connection connection = dataBaseService.retrieve();

            PreparedStatement aboutUpdate = connection.prepareStatement("insert into post_ad(id, id_account, json_text, json_tags, json_path_image, json_path_avatar) " +
                    "VALUES (?,?,?,?,? ,?);");

            aboutUpdate.setString(1, String.valueOf(idPostAd));
            aboutUpdate.setString(2, String.valueOf(idAccount));
            aboutUpdate.setString(3, String.valueOf(jsonText));
            aboutUpdate.setString(4, String.valueOf(jsonTags));
            aboutUpdate.setString(5, String.valueOf(jsonPathImage));

            JsonArray jsonPathAvatar = new JsonArray();
            jsonPathAvatar.add(jsonPathImage.get(0));
            jsonPathAvatar.add(jsonPathImage.get(1));

            aboutUpdate.setString(6, String.valueOf(jsonPathAvatar));
            aboutUpdate.execute();

            dataBaseService.putback(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

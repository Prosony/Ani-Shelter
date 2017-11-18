package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import test.TestLog;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class InsertQueryDB {
    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public void insertPostAd(UUID idPostAd, UUID idAccount, JsonObject jsonText, JsonObject jsonTags, JsonArray jsonPathImage){
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            boolean rsAccount = stmt.execute("insert into post_ad(id, id_account, json_text, json_tags, json_path_image) " +
                    "values('"+idPostAd+"','"+idAccount+"','"+jsonText+"','"+jsonTags+"','"+jsonPathImage+"');"); //TODO fix this shit, add builder
            if (rsAccount){
                testLog.sendToConsoleMessage("#INFO [InsertQueryDB] [insertPostAd] SUCCESS");
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

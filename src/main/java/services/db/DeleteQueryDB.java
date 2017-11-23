package services.db;

import test.TestLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class DeleteQueryDB {

    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public boolean deletePostAdByIdPostAd(UUID idPostAd){
        boolean result = false;
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            result = stmt.execute("delete from post_ad where post_ad.id = '"+idPostAd+"';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

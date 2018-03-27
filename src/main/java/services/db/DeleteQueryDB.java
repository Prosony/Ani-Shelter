package services.db;

import test.TestLog;

import java.sql.*;
import java.util.UUID;

public class DeleteQueryDB {

    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public boolean deletePostAdByIdPostAd(UUID idPostAd){
        boolean result = false;
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement delete = connection.prepareStatement("delete from post_ad where post_ad.id = ?;");
            delete.setString(1, idPostAd.toString());
            result = delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.putback(connection);
        return result;
    }
}

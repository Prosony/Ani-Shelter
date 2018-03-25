package services.db;

import test.TestLog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateQueryDB {
    private DataBaseService dataBaseService = DataBaseService.getInstance();
    private TestLog log = new TestLog();
    public boolean updateStatusReadMessage(String idDialog, String idReader){
        //UPDATE messages SET  messages.is_read = 1, messages.timestamp = messages.timestamp WHERE messages.id_dialog = '36ef27fe-da81-11e7-9296-cec278b6b50a' and messages.id_outcoming_account != 'c6c89398-ac7e-4b5f-9cb6-ba2b1c49b639';
        Connection connection = dataBaseService.retrieve();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE messages SET  messages.is_read = ?, messages.timestamp = messages.timestamp WHERE messages.id_dialog = ? and messages.id_outcoming_account != ?;");
            statement.setString(1, String.valueOf(1));
            statement.setString(2, idDialog);
            statement.setString(3, idReader);
            statement.execute();
            log.sendToConsoleMessage("#INFO [UpdateQueryDB][updateStatusReadMessage][SUCCESS]");
            dataBaseService.putback(connection);

            return true;
        } catch (SQLException e) {
            log.sendToConsoleMessage("#INFO [UpdateQueryDB][updateStatusReadMessage][ERROR]");
            e.printStackTrace();
        }
        return false;
    }
}

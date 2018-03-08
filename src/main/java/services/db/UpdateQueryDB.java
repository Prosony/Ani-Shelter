package services.db;

public class UpdateQueryDB {
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public boolean updateStatusReadMessage(String idDialog, String idReader){
        //UPDATE messages SET  messages.is_read = 1, messages.timestamp = messages.timestamp WHERE messages.id_dialog = '36ef27fe-da81-11e7-9296-cec278b6b50a' and messages.id_outcoming_account != 'c6c89398-ac7e-4b5f-9cb6-ba2b1c49b639';
        return false;
    }
}

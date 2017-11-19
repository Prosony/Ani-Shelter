package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.AccountCache;
import model.account.Account;
import model.ad.PostAd;
import model.profile.Profile;
import test.TestLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class SelectQueryDB {

    private TestLog testLog = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();

    public Account getAccountByEmail(String email, String password){
        Account account = null;
        Profile profile = null;
        try {
            Connection connection = dataBaseService.retrieve();

            Statement stmt= connection.createStatement();
            ResultSet rsAccount;
            rsAccount = stmt.executeQuery("select * from account, profile where account.email = '"+email+"' AND account.password ='"+password+"' AND account.id_account = profile.id;");

            while (rsAccount.next()) {
                UUID id = UUID.fromString(rsAccount.getString("id_account"));
                email = rsAccount.getString("email");
                password = rsAccount.getString("password");

                account = new Account(id, email, password);
                String date = rsAccount.getString("date_create_account");
                String name =rsAccount.getString("name");
                String surname = rsAccount.getString("surname");
                String phone = rsAccount.getString("phone");
                String birthday = rsAccount.getString("birthday");
                String pathAvatar = rsAccount.getString("path_avatar");
                String about = rsAccount.getString("about");

                DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                profile = new Profile(id, LocalDate.parse(date, DATE_FORMAT), name, surname, email, phone, birthday, about, pathAvatar);

            }
            if (account != null && profile != null){

                AccountCache accountCache = AccountCache.getInstance();
                accountCache.addAccount(account, profile);
            }
                dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return account;
    }

    public Profile getProfileById(UUID id){
        Profile profile = null;
        try {
            Connection connection = dataBaseService.retrieve();

            Statement stmt= connection.createStatement();
            ResultSet rsAccount;
            rsAccount = stmt.executeQuery("select * from profile where profile.id = '"+id+"';");

            while (rsAccount.next()) {
                String date = rsAccount.getString("date_create_account");
                String name =rsAccount.getString("name");
                String surname = rsAccount.getString("surname");
                String email = rsAccount.getString("email");
                String phone = rsAccount.getString("phone");
                String birthday = rsAccount.getString("birthday");
                String pathAvatar = rsAccount.getString("path_avatar");
                String about = rsAccount.getString("about");
                DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                profile = new Profile(id, LocalDate.parse(date, DATE_FORMAT), name, surname, email, phone, birthday, about, pathAvatar);
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return profile;
    }

    public String getTagsByTitle(String title){
        String jsonTag = null;
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet rsAccount;
            rsAccount = stmt.executeQuery("select * from tags where tags.title = '"+title+"';");
            while (rsAccount.next()) {
                jsonTag =rsAccount.getString("json_tag");
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return jsonTag;
    }

    private void printAllConnection(){
        testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] [CONNECTION] "+dataBaseService.getAvailableConnections());

    }


    public ArrayList<PostAd> getAllPostAdByIdAccount(UUID idAccount){
        ArrayList<PostAd> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet data = stmt.executeQuery("select * from post_ad where post_ad.id_account = '"+idAccount+"';");
            while (data.next()) {
                UUID id = UUID.fromString(data.getString("id"));
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonText = (JsonObject) jsonParser.parse(data.getString("json_text"));
                JsonObject jsonTags = (JsonObject) jsonParser.parse(data.getString("json_tags"));
                JsonArray jsonPathImage  = (JsonArray)jsonParser.parse(data.getString("json_path_image"));
                JsonArray jsonPathAvatar  = (JsonArray)jsonParser.parse(data.getString("json_path_avatar"));
                PostAd postAd = new PostAd(id,idAccount,jsonText,jsonTags,jsonPathImage,jsonPathAvatar);
                list.add(postAd);
            }
            dataBaseService.putback(connection);
            if (!list.isEmpty()){
                return list;
            }else{
                testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] [DB] list is empty");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return null;
    }
}

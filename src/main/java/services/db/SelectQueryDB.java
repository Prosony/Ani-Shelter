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
    /**************************************************************************************************
     *                                          ACCOUNT -/- PROFILE
     **************************************************************************************************/

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
    /**************************************************************************************************
     *                                          TAGS -/- CATEGORY
     **************************************************************************************************/

    public String getTagCategoryByTitle(String title){ //TODO rewrite this shit
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
    public ArrayList<String> getTagsByTitle(String title){
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet rsAccount;
            rsAccount = stmt.executeQuery("select * from tags where tags.title = '"+title+"';");
            while (rsAccount.next()) {
                list.add(rsAccount.getString("json_tag"));
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return list;
    }
    /**************************************************************************************************
     *                                          POST AD
     **************************************************************************************************/

    public ArrayList<PostAd> getAllPostAdByIdAccount(UUID idAccount){
        ArrayList<PostAd> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet data = stmt.executeQuery("select * from post_ad where post_ad.id_account = '"+idAccount+"';");
            JsonParser jsonParser = new JsonParser();
            while (data.next()) {
                UUID id = UUID.fromString(data.getString("id"));
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
    public PostAd getPostAdByIdPostAd(UUID idPostAd){
        PostAd postAd = null;
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet data = stmt.executeQuery("select * from post_ad where post_ad.id = '"+idPostAd+"';");
            JsonParser jsonParser = new JsonParser();
            while (data.next()) {
                JsonObject jsonText = (JsonObject) jsonParser.parse(data.getString("json_text"));
                JsonObject jsonTags = (JsonObject) jsonParser.parse(data.getString("json_tags"));
                JsonArray jsonPathImage = (JsonArray) jsonParser.parse(data.getString("json_path_image"));
                JsonArray jsonPathAvatar = (JsonArray) jsonParser.parse(data.getString("json_path_avatar"));
                postAd = new PostAd(
                        UUID.fromString(data.getString("id")),UUID.fromString(data.getString("id_account")),jsonText,jsonTags,
                        jsonPathImage,jsonPathAvatar);
            }
            dataBaseService.putback(connection);
            return postAd;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return postAd;
    }
    public ArrayList<PostAd> getPostAdPeople(UUID idAccount){
        ArrayList<PostAd> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt= connection.createStatement();
            ResultSet data = stmt.executeQuery("select * from post_ad where post_ad.id_account != '"+idAccount+"';");
            list = getPostAdFromJson(data);
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        printAllConnection();
        return list;
    }

    public ArrayList<PostAd> getPostAdByTags(JsonObject tags) {

        ArrayList<PostAd> list = null;
        String query = buildQuery(tags);
        testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[getPostAdByTags] query: "+query);
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            Statement stmt = connection.createStatement();
            ResultSet data = stmt.executeQuery(query);
            list = getPostAdFromJson(data);
            dataBaseService.putback(connection);
            printAllConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
     /**************************************************************************************************
     *                                          DB SERVICE
     **************************************************************************************************/
    private void printAllConnection(){
        testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] [CONNECTION] "+dataBaseService.getAvailableConnections());
    }
    private ArrayList<PostAd> getPostAdFromJson(ResultSet data) throws SQLException {
        ArrayList<PostAd> list = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        while (data.next()) {
            JsonObject jsonText = (JsonObject) jsonParser.parse(data.getString("json_text"));
            JsonObject jsonTags = (JsonObject) jsonParser.parse(data.getString("json_tags"));
            JsonArray jsonPathImage = (JsonArray) jsonParser.parse(data.getString("json_path_image"));
            JsonArray jsonPathAvatar = (JsonArray) jsonParser.parse(data.getString("json_path_avatar"));
            list.add(new PostAd(
                    UUID.fromString(data.getString("id")), UUID.fromString(data.getString("id_account")), jsonText, jsonTags,
                    jsonPathImage, jsonPathAvatar));
        }
        return list;
    }

//            '"animals":"null",' +
//            '"group":"null",' +
//            '"breeds":"null",' +
//            '"age":"null",' +
//            '"own_tags":[]' +

    private String buildQuery(JsonObject tags){
        // select * from post_ad where post_ad.json_tags->'$[0].own_tags[*]' like CONCAT('%','middle size','%');

        StringBuilder builder = new StringBuilder();
        builder.append("select * from post_ad where ");
        testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] tags: \n"+tags);
        String animals = tags.get("animals").getAsString();
        String group = tags.get("group").getAsString();
        String age = tags.get("age").getAsString();
        boolean all = true;
        boolean first = true;
        testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] animals: \n"+tags+"\n group: "+group+"\n age: "+age);

        if (!animals.isEmpty()){
            builder.append("upper(post_ad.json_tags->'$[0].animals') LIKE upper('%").append(animals).append("%') ");
            all = false;
            first = false;
        }

        if (!group.isEmpty()){
            builder.append("and  upper(post_ad.json_tags->'$[0].group') LIKE upper('%").append(group).append("%') ");
            all = false;
        }
        if (!age.isEmpty()){
            testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] age: "+age);
            builder.append("and post_ad.json_tags->'$[0].age' = '").append(age).append("' ");
            all = false;
        }
        JsonArray ownTags = tags.get("own_tags").getAsJsonArray();
        if (!ownTags.toString().equals("[]")){//TODO rewrite this shit
            testLog.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] !ownTags.isJsonNull()");
            for (int index= 0; index < ownTags.size(); index++){
                if (first){
                    builder.append("UPPER(post_ad.json_tags->'$[0].own_tags[*]') like UPPER(CONCAT('%',").append(ownTags.get(index)).append(",'%')) ");
                    first = false;
                }else {
                    builder.append("and UPPER(post_ad.json_tags->'$[0].own_tags[*]') like UPPER(CONCAT('%',").append(ownTags.get(index)).append(",'%')) ");
                }
            }
            all = false;
        }
        if (all){
            return "select * from post_ad;";
        }else{
            builder.append(";");
            return builder.toString();
        }
    }
}

package services.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.AccountCache;
import model.account.Account;
import model.ad.PostAd;
import model.message.Dialog;
import model.message.Messages;
import model.profile.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.TestLog;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class SelectQueryDB {

    private TestLog log = new TestLog();
    private DataBaseService dataBaseService = DataBaseService.getInstance();
    /**************************************************************************************************
     *                                          ACCOUNT -/- PROFILE
     **************************************************************************************************/

    public Account getAccountByEmail(String email, String password){
        Account account = null;
        Profile profile = null;
        try {
            Connection connection = dataBaseService.retrieve();

            PreparedStatement select = connection.prepareStatement("select * from account, profile where account.email = ? AND account.password = ? AND account.id_account = profile.id;");
            select.setString(1,email);
            select.setString(2,password);
            ResultSet rsAccount;

            rsAccount = select.executeQuery();

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
        dataBaseService.getAvailableConnections();
        return account;
    }

    public Profile getProfileById(UUID id){
        Profile profile = null;
        try {
            Connection connection = dataBaseService.retrieve();

            PreparedStatement  select = connection.prepareStatement("select * from profile where profile.id = ?;");
            select.setString(1,id.toString());
            ResultSet rsAccount;
            rsAccount = select.executeQuery();

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
        dataBaseService.getAvailableConnections();
        return profile;
    }
    /**************************************************************************************************
     *                                          TAGS -/- CATEGORY
     **************************************************************************************************/

    public String getTagCategoryByTitle(String title){ //TODO rewrite this shit
        String jsonTag = null;
        try {
            Connection connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from tags where tags.title = ?;");
            select.setString(1,title);
            ResultSet data;
            data = select.executeQuery();
            while (data.next()) {
                jsonTag =data.getString("json_tag");
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.getAvailableConnections();
        return jsonTag;
    }
    public ArrayList<String> getTagsByTitle(String title){
        ArrayList<String> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from tags where tags.title = ?;");
            select.setString(1,title);
            ResultSet rsAccount;
            rsAccount = select.executeQuery();
            while (rsAccount.next()) {
                list.add(rsAccount.getString("json_tag"));
            }
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.getAvailableConnections();
        return list;
    }
    /**************************************************************************************************
     *                                          POST AD
     **************************************************************************************************/

    public ArrayList<PostAd> getAllPostAdByIdAccount(UUID idAccount){
        ArrayList<PostAd> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from post_ad where post_ad.id_account = ? ORDER BY post_ad.timestamp;");
            select.setString(1, idAccount.toString());
            ResultSet data = select.executeQuery();
            JsonParser jsonParser = new JsonParser();
            JSONParser parser = new JSONParser();
            while (data.next()) {
//                System.out.println("data.getString(\"json_text\")" + new String(data.getBytes("json_text"), "UTF-8"));
//                System.out.println("data.getString(\"json_tags\")" + new String(data.getBytes("json_tags"), "UTF-8"));
                UUID id = UUID.fromString(data.getString("id"));
                try {
                    JSONObject jsonText = (JSONObject) parser.parse( new String(data.getBytes("json_text"), "UTF-8"));
                    JSONObject jsonTags = (JSONObject) parser.parse(new String(data.getBytes("json_tags"), "UTF-8"));
                    JSONArray jsonPathImage = (JSONArray) parser.parse(data.getString("json_path_image"));
                    JSONArray jsonPathAvatar = (JSONArray) parser.parse(data.getString("json_path_avatar"));
                    PostAd postAd = new PostAd(id,idAccount,jsonText,jsonTags,jsonPathImage,jsonPathAvatar, data.getTimestamp("timestamp"));
                    list.add(postAd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dataBaseService.putback(connection);
            if (!list.isEmpty()){
                return list;
            }else{
                log.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] [DB] list is empty");
            }
        } catch (SQLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dataBaseService.getAvailableConnections();
        return null;
    }
    public PostAd getPostAdByIdPostAd(UUID idPostAd){
        PostAd postAd = null;
        try {
            Connection connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from post_ad where post_ad.id = ?;");
            select.setString(1, idPostAd.toString());
            ResultSet data = select.executeQuery();
            JSONParser jsonParser = new JSONParser();
            while (data.next()) {
                JSONObject jsonText = (JSONObject) jsonParser.parse(new String(data.getBytes("json_text"), "UTF-8"));
                JSONObject jsonTags = (JSONObject) jsonParser.parse(new String(data.getBytes("json_tags"), "UTF-8"));
                JSONArray jsonPathImage = (JSONArray) jsonParser.parse(data.getString("json_path_image"));
                JSONArray jsonPathAvatar = (JSONArray) jsonParser.parse(data.getString("json_path_avatar"));
                postAd = new PostAd(
                        UUID.fromString(data.getString("id")),UUID.fromString(data.getString("id_account")),jsonText,jsonTags,
                        jsonPathImage,jsonPathAvatar, data.getTimestamp("timestamp"));
            }
            dataBaseService.putback(connection);
            return postAd;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        dataBaseService.getAvailableConnections();
        return postAd;
    }
    public ArrayList<PostAd> getPostAdPeople(UUID idAccount){
        ArrayList<PostAd> list = new ArrayList<>();
        try {
            Connection connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from post_ad where post_ad.id_account != ? ORDER BY post_ad.timestamp;;");
            select.setString(1,idAccount.toString());
            ResultSet data = select.executeQuery();
            list = getPostAdFromJson(data);
            dataBaseService.putback(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataBaseService.getAvailableConnections();
        return list;
    }

    public ArrayList<PostAd> getPostAdByTags(JsonObject tags) {

        ArrayList<PostAd> list = null;
        String query = buildQuery(tags);
        log.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[getPostAdByTags] query: "+query);
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            Statement stmt = connection.createStatement();
            ResultSet data = stmt.executeQuery(query);
            list = getPostAdFromJson(data);
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**************************************************************************************************
     *                                          MESSAGES
     **************************************************************************************************/
    public ArrayList<Dialog> getAllDialogsByIdAccount(String idAccount){

        ArrayList<Dialog> list = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("SELECT * FROM dialogs WHERE dialogs.id_account_outcoming = ? OR dialogs.id_account_incoming = ?; ");
            select.setString(1, idAccount);
            select.setString(2, idAccount);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                list.add(new Dialog(UUID.fromString(result.getString("id_dialogs")),
                        UUID.fromString(result.getString("id_account_outcoming")), UUID.fromString(result.getString("id_account_incoming"))));
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
    public Dialog getDialogByIdDialog(String idDialog){
        Dialog dialog = null;
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("SELECT * FROM dialogs WHERE dialogs.id_dialogs = ?;");
            select.setString(1, idDialog);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                dialog = new Dialog(UUID.fromString(result.getString("id_dialogs")),
                        UUID.fromString(result.getString("id_account_outcoming")), UUID.fromString(result.getString("id_account_incoming")));
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dialog;
    }
    public ArrayList<Messages> getMessagesByIdDialog(String idDialog){

        ArrayList<Messages> list = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("SELECT * FROM messages WHERE messages.id_dialog = ? ORDER BY messages.timestamp;");
            select.setString(1, idDialog);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                list.add(new Messages(UUID.fromString(result.getString("id_message")),UUID.fromString(result.getString("id_dialog")),
                        UUID.fromString(result.getString("id_outcoming_account")), result.getTimestamp("timestamp"), result.getString("message"), result.getBoolean("is_read")
                        ));
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public Messages getMessageByIdMessage(String idMessage){

        Messages message = null;
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("SELECT * FROM messages WHERE messages.id_message= ? ;");
            select.setString(1, idMessage);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                message = new Messages(UUID.fromString(result.getString("id_message")),UUID.fromString(result.getString("id_dialog")),
                        UUID.fromString(result.getString("id_outcoming_account")), result.getTimestamp("timestamp"), result.getString("message")
                        , result.getBoolean("is_read"));
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public Messages getLastMessageByIdDialog(String idDialog){
        Messages message = null;
        Connection connection = null;
        try {
            connection = dataBaseService.retrieve();
            PreparedStatement select = connection.prepareStatement("select * from messages where messages.id_dialog = ? " +
                    "and messages.timestamp = (select MAX(messages.timestamp) from messages);");
            select.setString(1, idDialog);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                message = new Messages(UUID.fromString(result.getString("id_message")),UUID.fromString(result.getString("id_dialog")),
                        UUID.fromString(result.getString("id_outcoming_account")), result.getTimestamp("timestamp"), result.getString("message"),
                        result.getBoolean("is_read")
                );
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public int getCountUnreadMessages(String idAccount){
        int count = 0;
        ArrayList<Dialog> list = getAllDialogsByIdAccount(idAccount);
        try {
            Connection connection = dataBaseService.retrieve();
            Statement stmt = connection.createStatement();
            StringBuilder builder = new StringBuilder();
            builder.append("select COUNT(*) from messages where is_read = false AND messages.id_outcoming_account != '").append(idAccount).append("' and (");

            boolean first = true;
            for (Dialog aList : list) {
                if (first){
                    builder.append(" messages.id_dialog = '").append(aList.getIdDialog()).append("' ");
                    first =false;
                }else{
                    builder.append(" OR messages.id_dialog = '").append(aList.getIdDialog()).append("' ");
                }
            }
            builder.append(");");
            PreparedStatement select = connection.prepareStatement(builder.toString());
            ResultSet result = select.executeQuery();
            while (result.next()) {
                count = result.getInt("COUNT(*)");
            }
            dataBaseService.putback(connection);
            dataBaseService.getAvailableConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
     /**************************************************************************************************
     *                                          DB SERVICE
     **************************************************************************************************/

    private ArrayList<PostAd> getPostAdFromJson(ResultSet data) throws SQLException{
        ArrayList<PostAd> list = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        while (data.next()) {
//            JSONObject jsonText = null;
            try {
                JSONObject jsonText = (JSONObject) jsonParser.parse(new String(data.getBytes("json_text"), "UTF-8"));
                JSONObject jsonTags = (JSONObject) jsonParser.parse(new String(data.getBytes("json_tags"), "UTF-8"));
                JSONArray jsonPathImage = (JSONArray) jsonParser.parse(data.getString("json_path_image"));
                JSONArray jsonPathAvatar = (JSONArray) jsonParser.parse(data.getString("json_path_avatar"));
                list.add(new PostAd(
                        UUID.fromString(data.getString("id")), UUID.fromString(data.getString("id_account")), jsonText, jsonTags,
                        jsonPathImage, jsonPathAvatar, data.getTimestamp("timestamp")));
            } catch (ParseException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    private String buildQuery(JsonObject tags){

        //TODO user can fin own post by tags, need fix this bug!
        // select * from post_ad where post_ad.json_tags->'$[0].own_tags[*]' like CONCAT('%','middle size','%');
        //TODO ORDER BY messages.timestamp;

        StringBuilder builder = new StringBuilder();
        builder.append("select * from post_ad where ");
        log.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] tags: \n"+tags);
        String animals = tags.get("animals").getAsString();
        String group = tags.get("group").getAsString();
        String breeds = tags.get("breeds").getAsString();
        String age = tags.get("age").getAsString();
        boolean all = true;
        boolean first = true;
        log.sendToConsoleMessage("#INFO [SelectQueryDB] [buildQuery] animals: \n"+tags+"\n group: "+group+"\n age: "+age);

        if (!animals.isEmpty()){
            builder.append("upper(post_ad.json_tags->'$[0].animals') LIKE upper('%").append(animals).append("%') ");
            all = false;
            first = false;
        }

        if (!group.isEmpty()){
            builder.append("and  upper(post_ad.json_tags->'$[0].group') LIKE upper('%").append(group).append("%') ");
            all = false;
        }
        if (!breeds.isEmpty()){
            builder.append("and  upper(post_ad.json_tags->'$[0].breeds') LIKE upper('%").append(breeds).append("%') ");
        }
        if (!age.isEmpty()){
            log.sendToConsoleMessage("#INFO [SelectQueryDB] [buildQuery] age: "+age);
            builder.append("and post_ad.json_tags->'$[0].age' = '").append(age).append("' ");
            all = false;
        }
        JsonArray ownTags = tags.get("own_tags").getAsJsonArray();
        if (!ownTags.toString().equals("[]")){//TODO rewrite this shit
            log.sendToConsoleMessage("#INFO CLASS[SelectQueryDB] METHOD[buildQuery] !ownTags.isJsonNull()");
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

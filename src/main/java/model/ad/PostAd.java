package model.ad;
/**
 * Simple generic for ad post
 * @author Prosony
 * @since 0.0.1
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.util.UUID;

public class PostAd {

    private UUID id;
    private UUID idAccount;

    private JSONObject jsonText;
    private JSONObject jsonTags;
    private JSONArray jsonPathImage;
    private JSONArray jsonPathAvatar;
    private Timestamp timestamp;
    public PostAd(UUID id, UUID idAccount, JSONObject jsonText, JSONObject jsonTags, JSONArray jsonPathImage, JSONArray jsonPathAvatar, Timestamp timestamp){

        this.id = id;
        this.idAccount = idAccount;
        this.jsonText = jsonText;
        this.jsonTags = jsonTags;
        this.jsonPathImage = jsonPathImage;
        this.jsonPathAvatar = jsonPathAvatar;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdAccount() {
        return idAccount;
    }
    public void setIdAccount(UUID idAccount) {
        this.idAccount = idAccount;
    }

    public JSONObject getJsonText() {
        return jsonText;
    }
    public void setJsonText(JSONObject jsonText) {
        this.jsonText = jsonText;
    }

    public JSONObject getJsonTags() {
        return jsonTags;
    }
    public void setJsonTags(JSONObject jsonTags) {
        this.jsonTags = jsonTags;
    }

    public JSONArray getJsonPathImage() {
        return jsonPathImage;
    }
    public void setJsonPathImage(JSONArray jsonPathImage) {
        this.jsonPathImage = jsonPathImage;
    }

    public JSONArray getJsonPathAvatar() {
        return jsonPathAvatar;
    }
    public void setJsonPathAvatar(JSONArray jsonPathAvatar) {
        this.jsonPathAvatar = jsonPathAvatar;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "\n{"
                + "\tid :" + id+"\n"
                + "\tidAccount : " + idAccount+",\n"
                + "\tjsonText : " + jsonText+",\n"
                + "\tjsonTags : " + jsonTags+",\n"
                + "\tjsonPathImage : " + jsonPathImage+",\n"
                + "\tjsonPathAvatar : " + jsonPathAvatar+",\n"
                + "\ttimestamp : " + timestamp+"\n"
                +"}\n";
    }


}

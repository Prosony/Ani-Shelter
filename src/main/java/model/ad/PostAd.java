package model.ad;
/**
 * Simple generic for ad post
 * @author Prosony
 * @since 0.0.1
 */

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.UUID;

public class PostAd {

    private UUID id;
    private UUID idAccount;

    private JsonObject jsonText;
    private JsonObject jsonTags;
    private JsonArray jsonPathImage;
    private JsonArray jsonPathAvatar;

    public PostAd(UUID id, UUID idAccount, JsonObject jsonText, JsonObject jsonTags, JsonArray jsonPathImage, JsonArray jsonPathAvatar){

        this.id = id;
        this.idAccount = idAccount;
        this.jsonText = jsonText;
        this.jsonTags = jsonTags;
        this.jsonPathImage = jsonPathImage;
        this.jsonPathAvatar = jsonPathAvatar;
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

    public JsonObject getJsonText() {
        return jsonText;
    }
    public void setJsonText(JsonObject jsonText) {
        this.jsonText = jsonText;
    }

    public JsonObject getJsonTags() {
        return jsonTags;
    }
    public void setJsonTags(JsonObject jsonTags) {
        this.jsonTags = jsonTags;
    }

    public JsonArray getJsonPathImage() {
        return jsonPathImage;
    }
    public void setJsonPathImage(JsonArray jsonPathImage) {
        this.jsonPathImage = jsonPathImage;
    }

    public JsonArray getJsonPathAvatar() {
        return jsonPathAvatar;
    }
    public void setJsonPathAvatar(JsonArray jsonPathAvatar) {
        this.jsonPathAvatar = jsonPathAvatar;
    }

    @Override
    public String toString() {
        return "\n{"
                + "\tid :" + id+"\n"
                + "\tidAccount : " + idAccount+"\n"
                + "\tjsonText : " + jsonText+"\n"
                + "\tjsonTags : " + jsonTags+"\n"
                + "\tjsonPathImage : " + jsonPathImage+"\n"
                + "\tjsonPathAvatar : " + jsonPathAvatar+"\n"
                +"}\n";
    }
}

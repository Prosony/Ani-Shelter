package model.tags;

import java.util.ArrayList;
import java.util.UUID;

public class Tags {
    private UUID id;
    private String type;
    private ArrayList<String> tags;

    public Tags(String type, ArrayList<String> tags) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.tags = tags;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID idTags) {
        this.id= idTags;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}

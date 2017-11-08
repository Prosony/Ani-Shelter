package model.ad;
/**
 * Simple generic for ad post
 * @author Prosony
 * @since 0.0.1
 */

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.UUID;

public class PostAd {

    private UUID id;
    private UUID idAccount;

    private LocalDate date;
    private String header;
    private String wallText;
    private String pathToImageFirst;
    private String pathToImageSecond;
    private ArrayList<String> listPath;
    private ArrayList<String> listTag;

    public PostAd(UUID idAccount, String header, String wallPostText, String pathToImageFirst, String pathToImageSecond, ArrayList<String> list, ArrayList<String> listTag){
        this.header = header;
        this.pathToImageFirst = pathToImageFirst;
        this.pathToImageSecond = pathToImageSecond;
        this.listPath = list;
        this.listTag = listTag;

        this.id = UUID.randomUUID();
        this.idAccount = idAccount;
        this.date = LocalDate.now();
        this.wallText = wallPostText;
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

    public LocalDate getPostDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }

    public void setWallPostText(String wallPostText) {
        this.wallText = wallPostText;
    }
    public String getWallPostText() {
        return wallText;
    }


    public String getPathToImageFirst() {
        return pathToImageFirst;
    }
    public void setPathToImageFirst(String pathToImageFirst) {
        this.pathToImageFirst = pathToImageFirst;
    }

    public String getPathToImageSecond() {
        return pathToImageSecond;
    }
    public void setPathToImageSecond(String pathToImageSecond) {
        this.pathToImageSecond = pathToImageSecond;
    }


    public ArrayList<String> getListPath() {
        return listPath;
    }
    public void setListPath (ArrayList<String> list) {
        this.listPath = list;
    }

    public ArrayList<String> getListTag() {
        return listTag;
    }
    public void setListTag(ArrayList<String> listTag) {
        this.listTag = listTag;
    }

    @Override
    public String toString() {
        return " idAccount: " + getIdAccount() + " date: "  + getPostDate() + " wallPostText " + getWallPostText() +" path to first image: "+pathToImageFirst+" path to second image: "+pathToImageSecond;
    }


}
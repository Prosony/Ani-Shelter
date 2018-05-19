package memcache;
/**
 * Simple service for ad post
 * @author Prosony
 * @since 0.0.1
 */
import model.ad.PostAd;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PostAdCache {

    private static final PostAdCache instance = new PostAdCache();

    public static PostAdCache getInstance() {
        return instance;
    }

    private final Map<UUID, PostAd> mapPostAd;
    private final Map<UUID, ArrayList<PostAd>> mapListPostAd;

    private PostAdCache(){
        mapPostAd = new ConcurrentHashMap<>();
        mapListPostAd = new ConcurrentHashMap<>();

    }

    /** simple generic post ad*/
    public void addPostAd(UUID idPostAd, PostAd content){
        mapPostAd.put(idPostAd, content);
    }

    public PostAd getPostAdByIdPostAd(UUID idPostAd){
        return mapPostAd.get(idPostAd);
    }

    public Set<UUID> getAllIdPostAd(){
        return mapPostAd.keySet();
    }

    public void deletePostContentByIdPostAd(UUID idPostAd){
        mapPostAd.remove(idPostAd);
    }

    /** ArrayList post ad*/
    public void addListPostAd(UUID idAccount, ArrayList<PostAd> content){
        mapListPostAd.put(idAccount, content);
    }

    public ArrayList<PostAd> getListPostAdByIdAccount(UUID idAccount){
        return mapListPostAd.get(idAccount);
    }

    public void deleteListPostAdByIdAccount(UUID idAccount){
        mapListPostAd.remove(idAccount);
    }

}

package services.ad;
/**
 * Simple service for posts ad
 * @author Prosony
 * @since 0.0.1
 */
import com.google.gson.Gson;
import memcach.PostAdCache;
import model.ad.PostAd;
import model.favorites.Favorites;
import test.TestLog;

import java.util.ArrayList;

public class PostAdService {
    private TestLog testLog = new TestLog();
    private PostAdCache postAdCache = PostAdCache.getInstance();

    public ArrayList<PostAd> getListPostAd(ArrayList<Favorites> postAd){

        if(postAd != null && !postAd.isEmpty()){

            ArrayList<PostAd> listPostAd = new ArrayList<>();

            for (Favorites favorites : postAd){

                listPostAd.add(postAdCache.getPostAdByIdPostAd(favorites.getIdFavoritesAd()));

            }
            testLog.sendToConsoleMessage("[class PostAdService] [method getListPostAd] lisPostAd: "+ new Gson().toJson(listPostAd));
            return listPostAd;
        }
        return null;
    }
}

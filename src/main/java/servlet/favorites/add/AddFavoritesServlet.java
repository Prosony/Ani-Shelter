package servlet.favorites.add;
/**
 *
 * @author Prosony
 * @since 0.0.1
 */
import memcache.FavoritesCache;
import memcache.JsonWebTokenCache;
import memcache.PostAdCache;
import model.account.Account;
import model.ad.PostAd;
import model.favorites.Favorites;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet("/favorites/add")
public class AddFavoritesServlet extends HttpServlet{

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private FavoritesCache favoritesCache = FavoritesCache.getInstance();
    private PostAdCache postAdCache = PostAdCache.getInstance();

    private TestLog testLog = new TestLog();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String token = (String) jsonObject.get("token");
        UUID idPostAd = UUID.fromString((String) jsonObject.get("id"));

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            if (idPostAd != null && !idPostAd.toString().isEmpty() &&  !idPostAd.toString().equals("null")) {

                Account account = tokenCache.getAccountByToken(token);

                if (account != null) {

                    UUID idAccount = account.getId();
                    ArrayList<Favorites> favoritesList = favoritesCache.getListFavoritesByIdAccount(idAccount);

                    PostAd postAd = getPostId(idPostAd);
                    if (postAd !=null){
                        if (favoritesList != null){
                            favoritesList.add(new Favorites(idAccount, postAd.getId()));
                            favoritesCache.deleteListFavorites(idAccount);
                            favoritesCache.addListFavorites(idAccount, favoritesList);
                            testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [SUCCESS]");
                        }else {
                            favoritesList = new ArrayList<>();
                            favoritesList.add(new Favorites(idAccount, postAd.getId()));
                            favoritesCache.addListFavorites(idAccount, favoritesList);
                            testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [SUCCESS] first favorites");

                        }
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [FAIL] post ad not found");
                        otherService.sendToClient(response, 204);
                    }


                } else {
                    testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [FAIL] account not found");
                    otherService.sendToClient(response, 204);
                }
            }else {
                testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [FAIL] id is empty");
                otherService.sendToClient(response,204);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] [FAIL] token not found");
            otherService.sendToClient(response,401);
        }

    }

    private PostAd getPostId(UUID idPostAd){
        PostAd postAd = postAdCache.getPostAdByIdPostAd(idPostAd);
        if (postAd == null){
            SelectQueryDB selectQueryDB = new SelectQueryDB();
            postAd = selectQueryDB.getPostAdByIdPostAd(idPostAd);
            postAdCache.addPostAd(postAd.getId(),postAd);
        }
        return postAd;
    }
}

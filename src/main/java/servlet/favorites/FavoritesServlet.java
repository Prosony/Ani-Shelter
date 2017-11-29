package servlet.favorites;

/**
 * Post methods return to frontend json with id Favorites and their profile
 * @author Prosony
 * @since 0.0.1
 */

import com.google.gson.Gson;
import memcach.FavoritesCache;
import memcach.JsonWebTokenCache;
import model.account.Account;
import model.ad.PostAd;
import model.favorites.Favorites;
import org.json.simple.JSONObject;
import services.*;
import services.ad.PostAdService;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet("/favorites")
public class FavoritesServlet extends HttpServlet {

    private TestLog testLog = new TestLog();
    private OtherService otherService = new OtherService();
    private FavoritesCache favoritesCache = FavoritesCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String jwtKey = (String) jsonObject.get("token");

        if (jwtKey!= null && !jwtKey.equals("") && !jwtKey.equals("null")){
            Account account = tokenCache.getAccountByJws(jwtKey);
            if (account != null) {

                PostAdService postAdService = new PostAdService();

                UUID idAccount = account.getId();
                testLog.sendToConsoleMessage("#TEST [class FriendsServlet] id from tokenJWT: " + idAccount);

                ArrayList<Favorites> listFavorites = favoritesCache.getListFavoritesByIdAccount(idAccount);

                ArrayList<PostAd> listPostAd = postAdService.getListPostAd(listFavorites);

                if (listPostAd != null && !listPostAd.isEmpty()){
                    otherService.answerToClient(response, new Gson().toJson(listPostAd));
                }else{
                    testLog.sendToConsoleMessage("#TEST [class FavoritesServlet] [FAIL] favorites list is empty");
                    otherService.errorToClient(response, 204);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class FavoritesServlet] [FAIL] account not found");
                otherService.errorToClient(response, 401);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class FavoritesServlet] [FAIL] token not found");
            otherService.errorToClient(response, 401);
        }
    }
}

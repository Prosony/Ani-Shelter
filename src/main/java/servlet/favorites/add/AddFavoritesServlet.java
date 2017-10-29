package servlet.favorites.add;
/**
 *
 * @author Prosony
 * @since 0.0.1
 */
import memcach.FavoritesCache;
import memcach.JsonWebTokenCache;
import memcach.PostAdCache;
import model.account.Account;
import model.ad.PostAd;
import model.favorites.Favorites;
import org.json.simple.JSONObject;
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
        String jwtToken = (String) jsonObject.get("token");
        UUID idPostAd = (UUID) jsonObject.get("id");

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {
            if (idPostAd != null && !idPostAd.toString().isEmpty() &&  !idPostAd.toString().equals("null")) {

                Account account = tokenCache.getAccountByJws(jwtToken);

                if (account != null) {

                    UUID idAccount = account.getId();
                    ArrayList<Favorites> favoritesList= favoritesCache.getListFavoritesByIdAccount(idAccount);
                    PostAd postAd = postAdCache.getPostAdByIdPostAd(idPostAd);
                    if (favoritesList != null){
                        favoritesList.add(new Favorites(idAccount, postAd.getId()));
                        favoritesCache.deleteListFavorites(idAccount);
                        favoritesCache.addListFavorites(idAccount, favoritesList);
                    }else {
                        favoritesList = new ArrayList<>();
                        favoritesList.add(new Favorites(idAccount, postAd.getId()));
                        favoritesCache.addListFavorites(idAccount, favoritesList);
                    }

                } else {
                    testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] account not found");
                    otherService.errorToClient(response, 401);
                }
            }else {
                testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] id is empty");
                otherService.errorToClient(response,400);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class AddFavoritesServlet] token not found");
            otherService.errorToClient(response,401);
        }

    }
}

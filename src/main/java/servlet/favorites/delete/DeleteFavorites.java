package servlet.favorites.delete;
/**
 * @author Prosony
 * @since 0.2.7.1 alpha
 */
import memcach.FavoritesCache;
import memcach.JsonWebTokenCache;
import model.account.Account;
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

@WebServlet("/favorites/delete")
public class DeleteFavorites extends HttpServlet{

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private FavoritesCache favoritesCache = FavoritesCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = (String) jsonObject.get("token");
        String idPostAd = (String) jsonObject.get("id");

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {
            if (idPostAd != null && !idPostAd.isEmpty() &&  !idPostAd.equals("null")) {

                Account account = tokenCache.getAccountByJws(jwtToken);
                if (account != null){
                    UUID idAccount = account.getId();
                    ArrayList<Favorites> favorites = favoritesCache.getListFavoritesByIdAccount(idAccount);
                    if (favorites != null && !favorites.isEmpty()){

                        favorites = deleteFavoritesFromList(UUID.fromString(idPostAd), favorites);
                        favoritesCache.deleteListFavorites(idAccount);
                        favoritesCache.addListFavorites(idAccount,favorites);
                    }else{
                        favoritesCache.deleteListFavorites(idAccount);
                    }

                }else{
                    testLog.sendToConsoleMessage("#TEST [class DeleteFriend] account not found");
                    otherService.errorToClient(response, 401);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class DeleteFriend] id friend not found");
                otherService.errorToClient(response, 400);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class DeleteFriend] token not found");
            otherService.errorToClient(response, 401);
        }

    }
    private ArrayList<Favorites> deleteFavoritesFromList(UUID idPostAd , ArrayList<Favorites> favoritesList){
        Favorites favorites;
        for (int index = 0; index < favoritesList.size(); index++){
            favorites = favoritesList.get(index);
            if (favorites.getIdFavoritesAd().equals(idPostAd)){
                favoritesList.remove(index);
            }
        }
        return favoritesList;
    }
}

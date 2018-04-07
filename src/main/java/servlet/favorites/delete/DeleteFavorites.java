package servlet.favorites.delete;
/**
 * @author Prosony
 * @since 0.0.1
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
        String token = (String) jsonObject.get("token");
        UUID idPostAd = UUID.fromString((String) jsonObject.get("id"));

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            if (idPostAd != null && !idPostAd.toString().isEmpty() &&  !idPostAd.toString().equals("null")) {

                Account account = tokenCache.getAccountByToken(token);
                if (account != null){
                    UUID idAccount = account.getId();
                    ArrayList<Favorites> favorites = favoritesCache.getListFavoritesByIdAccount(idAccount);
                    if (favorites != null && !favorites.isEmpty()){

                        favorites = deleteFavoritesFromList(idPostAd, favorites);
                        favoritesCache.deleteListFavorites(idAccount);
                        favoritesCache.addListFavorites(idAccount,favorites);
                        testLog.sendToConsoleMessage("#TEST [class DeleteFriend] [SUCCESS]");
                    }else{
                        favoritesCache.deleteListFavorites(idAccount);
                    }

                }else{
                    testLog.sendToConsoleMessage("#TEST [class DeleteFriend] [FAIL] account not found");
                    otherService.sendToClient(response, 401);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class DeleteFriend] [FAIL] id friend not found");
                otherService.sendToClient(response, 400);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class DeleteFriend] [FAIL] token not found");
            otherService.sendToClient(response, 401);
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

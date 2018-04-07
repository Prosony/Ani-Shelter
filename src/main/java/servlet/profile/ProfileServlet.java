package servlet.profile;
/**
 * @author Prosony
 * @since 0.0.1
 */
import com.google.gson.*;

import memcach.AccountCache;
import memcach.JsonWebTokenCache;
import model.account.Account;
import model.profile.Profile;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private TestLog log = TestLog.getInstance();
    private AccountCache accountCache = AccountCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

            JsonHandler jsonHandler = new JsonHandler();
            JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
            String token = jsonObject.get("token").toString();
            String idAccountProfile = (String) jsonObject.get("id");

        if (token != null && !token.isEmpty()) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null){
                if (idAccountProfile != null && !idAccountProfile.isEmpty() && !idAccountProfile.equals("null")) {
                    log.sendToConsoleMessage("#TEST [class ProfileServlet] idAccountRequest: " +idAccountProfile);
                    log.sendToConsoleMessage("#INFO [ProfileServlet][/profile] idAccountProfile: "+idAccountProfile);
                    sendProfileById(response, UUID.fromString(idAccountProfile));
                } else {
                    log.sendToConsoleMessage("#TEST [class ProfileServlet] idAccountRequest is null: " +idAccountProfile);
                    sendProfileByToken(response, account.getId());
                }
            }else{
                log.sendToConsoleMessage("#TEST [class ProfileServlet] account not found");
                otherService.sendToClient(response, 401);
            }
        }else{
            log.sendToConsoleMessage("#TEST [class ProfileServlet] token not found");
            otherService.sendToClient(response,401);
        }
    }

    private void sendProfileByToken(HttpServletResponse response, UUID id){
            Profile profile = accountCache.getProfileById(id);
            if (profile != null){
                otherService.answerToClient(response, new Gson().toJson(profile));
            }else{
                log.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [CACHE]!");
                sendProfileFromDB(response, id);
            }
    }

    private void sendProfileById(HttpServletResponse response, UUID id){
        Profile profile = accountCache.getProfileById(id);
        if (profile != null){
            log.sendToConsoleMessage("#INFO [ProfileServlet][sendProfileById]: "+profile);
            otherService.answerToClient(response, new Gson().toJson(profile));
        }else {
            log.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [CACHE]!");
            sendProfileFromDB(response, id);
        }
    }
    private void sendProfileFromDB(HttpServletResponse response, UUID id){

        Profile profile;
        SelectQueryDB selectQueryDB = new SelectQueryDB();
        profile =selectQueryDB.getProfileById(id);
        if (profile != null){
//            log.sendToConsoleMessage("#INFO [ProfileServlet][sendProfileFromDB]: "+profile);
            otherService.answerToClient(response, new Gson().toJson(profile));
        }else{
//            log.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [DB]!");
            otherService.sendToClient(response, 204);
        }
    }
}
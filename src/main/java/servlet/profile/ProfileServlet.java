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
import services.*;
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

    private TestLog testLog = TestLog.getInstance();
    private AccountCache accountCache = AccountCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

            JsonHandler jsonHandler = new JsonHandler();
            JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
            String jwtKey = (String) jsonObject.get("token");
            String idAccountProfile = (String) jsonObject.get("id");

        if (jwtKey != null && !jwtKey.isEmpty()) {
            Account account = tokenCache.getAccountByJws(jwtKey);
            if (account != null){
                if (idAccountProfile != null && !idAccountProfile.isEmpty() && !idAccountProfile.equals("null")) {
                    testLog.sendToConsoleMessage("#TEST [class ProfileServlet] idAccountRequest: " +idAccountProfile);
                    sendProfileById(response, UUID.fromString(idAccountProfile));
                } else {
                    testLog.sendToConsoleMessage("#TEST [class ProfileServlet] idAccountRequest is null: " +idAccountProfile);
                    sendProfileByJWT(response, account.getId());
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class ProfileServlet] account not found");
                otherService.errorToClient(response, 401);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class ProfileServlet] token not found");
            otherService.errorToClient(response,401);
        }
    }

    private void sendProfileByJWT(HttpServletResponse response, UUID id){
            Profile profile = accountCache.getProfileById(id);
            if (profile != null){
                otherService.answerToClient(response, new Gson().toJson(profile));
            }else{
                testLog.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [CACHE]!");
                sendProfileFromDB(response, id);
            }
    }

    private void sendProfileById(HttpServletResponse response, UUID id){
        Profile profile = accountCache.getProfileById(id);
        if (profile != null){
            otherService.answerToClient(response, new Gson().toJson(profile));
        }else {
            testLog.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [CACHE]!");
            sendProfileFromDB(response, id);
        }
    }
    private void sendProfileFromDB(HttpServletResponse response, UUID id){

        Profile profile;
        SelectQueryDB selectQueryDB = new SelectQueryDB();
        profile =selectQueryDB.getProfileById(id);
        if (profile != null){
            otherService.answerToClient(response, new Gson().toJson(profile));
        }else{
            testLog.sendToConsoleMessage("#TEST [class ProfileServlet] method [sendProfileById] account not found in [DB]!");
            otherService.errorToClient(response, 204);
        }
    }
}
package servlet.tags;

import com.google.gson.Gson;
import memcach.AccountCache;
import model.account.Account;
import org.json.simple.JSONObject;
import services.JWTService;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tags")
public class TagsServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private AccountCache accountCache = AccountCache.getInstance();
    private JWTService jwtService = new JWTService();
    private OtherService otherService = new OtherService();


    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String jwtKey = (String) jsonObject.get("token");
        String title = (String) jsonObject.get("title");

        if (jwtKey != null && !jwtKey.isEmpty()) {
            if(title != null && !title.isEmpty() && !title.equals("null")){
                Account account = jwtService.checkJWT(jwtKey);
                if (account != null) {
                    SelectQueryDB selectQueryDB = new SelectQueryDB();
                    String  jsonTag = selectQueryDB.getTagsByTitle(title);
                    testLog.sendToConsoleMessage("#TEST [class ProfileServlet] jsonTag: "+jsonTag);
                    if (jsonTag != null && !jsonTag.isEmpty() && !jsonTag.equals("null")){
                        otherService.answerToClient(response, new Gson().toJson(jsonTag));
                    }else{
                        otherService.errorToClient(response, 204);
                    }
                }else{
                    otherService.errorToClient(response, 401);
                }
            }else{
                otherService.errorToClient(response, 401);
            }
        }else{
            otherService.errorToClient(response, 204);
        }
    }
}

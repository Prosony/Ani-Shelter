package servlet.tags;

import com.google.gson.Gson;
import memcach.AccountCache;
import memcach.JsonWebTokenCache;
import model.account.Account;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tags/category")
public class TagsCategoryServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private AccountCache accountCache = AccountCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();


    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String token = (String) jsonObject.get("token");
        String title = (String) jsonObject.get("title");

        if (token != null && !token.isEmpty()) {
            if(title != null && !title.isEmpty() && !title.equals("null")){
                Account account = tokenCache.getAccountByToken(token);
                if (account != null) {
                    SelectQueryDB selectQueryDB = new SelectQueryDB();
                    String  jsonTag = selectQueryDB.getTagCategoryByTitle(title); //TODO rewrite this shit
                    testLog.sendToConsoleMessage("#TEST [class TagsCategoryServlet] jsonTag: "+jsonTag);
                    if (jsonTag != null && !jsonTag.isEmpty() && !jsonTag.equals("null")){
                        otherService.answerToClient(response, new Gson().toJson(jsonTag));
                    }else{
                        otherService.sendToClient(response, 204);
                    }
                }else{
                    otherService.sendToClient(response, 401);
                }
            }else{
                otherService.sendToClient(response, 401);
            }
        }else{
            otherService.sendToClient(response, 204);
        }
    }
}

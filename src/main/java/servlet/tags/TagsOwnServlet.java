package servlet.tags;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import memcach.AccountCache;
import model.account.Account;
import org.json.simple.JSONObject;
import services.JWTService;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tags/own")
public class TagsOwnServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private AccountCache accountCache = AccountCache.getInstance();
    private JWTService jwtService = new JWTService();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String jwtKey = (String) jsonObject.get("token");
        String stringTags = jsonObject.get("tags").toString();


        if (jwtKey != null && !jwtKey.isEmpty()) {
            if(stringTags != null && !stringTags.isEmpty() && !stringTags.equals("null")){
                Account account = jwtService.checkJWT(jwtKey);
                if (account != null) {
                    JsonParser jsonParser = new JsonParser();
                    JsonArray jsonTags = (JsonArray)jsonParser.parse(stringTags);
                    testLog.sendToConsoleMessage("#TEST [class TagsOwnServlet] jsonTags: "+jsonTags);
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
//select * from tags where tags.json_tag->'$[0]' like CONCAT('%','lar','%');
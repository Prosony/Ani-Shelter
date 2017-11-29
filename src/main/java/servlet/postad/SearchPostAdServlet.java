package servlet.postad;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import memcach.JsonWebTokenCache;
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

@WebServlet("/search")
public class SearchPostAdServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String jwtKey = (String) jsonObject.get("token");

        String stringTags = jsonObject.get("json_tags").toString();

        if (jwtKey != null && !jwtKey.isEmpty()) {
            if (stringTags != null && !stringTags.isEmpty() && !stringTags.equals("null")) {
                Account account = tokenCache.getAccountByJws(jwtKey);
                if (account != null) {
                    JsonParser jsonParser = new JsonParser();
                    JsonObject jsonTags = (JsonObject)jsonParser.parse(stringTags);
                    testLog.sendToConsoleMessage("#INFO [SearchPostAdServlet] jsonTags: "+jsonTags);

                }
            }
        }

    }

}

package servlet.tags;

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

        if (jwtKey != null && !jwtKey.isEmpty()) {
            Account account = jwtService.checkJWT(jwtKey);
            if (account != null) {

            }
        }
    }
}

package servlet.messages;

import com.google.gson.Gson;
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

@WebServlet("/unread-messages")
public class GetCountUnreadMessagesServlet extends HttpServlet{

    private TestLog log = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonHandler = new JsonHandler().getJsonFromRequest(request);
        String token = jsonHandler.get("token").toString();

        if (token != null && !token.isEmpty() && !token.equalsIgnoreCase("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) { //TODO write cache
                SelectQueryDB selectQueryDB = new SelectQueryDB();
                int count = selectQueryDB.getCountUnreadMessages(account.getId().toString());
                log.sendToConsoleMessage("#INFO [GetCountUnreadMessagesServlet][/unread-messages] count unread message: "+count);
                otherService.answerToClient(response, new Gson().toJson(count));
            }
        }
    }
}

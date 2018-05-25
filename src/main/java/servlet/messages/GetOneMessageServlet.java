package servlet.messages;

import com.google.gson.Gson;
import memcache.JsonWebTokenCache;
import model.account.Account;
import model.message.Messages;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/message")
public class GetOneMessageServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonHandler = new JsonHandler().getJsonFromRequest(request);
        String token = jsonHandler.get("token").toString();
        String idMessage = jsonHandler.get("id_message").toString();

        if (token != null && !token.isEmpty() && !token.equalsIgnoreCase("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) { //TODO write cache

                if (idMessage != null && !idMessage.isEmpty() && !idMessage.equalsIgnoreCase("null")){
                    SelectQueryDB selectQueryDB = new SelectQueryDB();
                    Messages message = selectQueryDB.getMessageByIdMessage(idMessage);

                    if (message != null){
                        otherService.answerToClient(response, new Gson().toJson(message));
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class GetOneMessageServlet] [ERROR] message not fount");
                        otherService.sendToClient(response, 204);
                    }
                }else {
                    testLog.sendToConsoleMessage("#TEST [class GetOneMessageServlet] [ERROR] idMessage is empty");
                    otherService.sendToClient(response, 204);
                }
            }else {
                testLog.sendToConsoleMessage("#TEST [class GetOneMessageServlet] [ERROR]  account not found");
                otherService.sendToClient(response, 401);
            }
        }else {
            testLog.sendToConsoleMessage("#TEST [class GetOneMessageServlet] [ERROR]  token not found");
            otherService.sendToClient(response, 401);
        }
    }
}

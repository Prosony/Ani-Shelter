package servlet.messages;

import com.google.gson.Gson;
import memcach.JsonWebTokenCache;
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

//select * from messages where messages.timestamp = (select MAX(messages.timestamp) from messages);
@WebServlet("/message-last")
public class GetLastMessageDialogServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();


    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JsonHandler().getJsonFromRequest(request);
        String idDialog = String.valueOf(json.get("id_dialog"));
        String token    = String.valueOf(json.get("token"));

        if (token != null && !token.isEmpty() && !token.equalsIgnoreCase("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) { //TODO write cache
                if (idDialog != null && !idDialog.isEmpty() && !idDialog.equalsIgnoreCase("null")) {
                    SelectQueryDB selectQueryDB = new SelectQueryDB();
                    Messages message = selectQueryDB.getLastMessageByIdDialog(idDialog);
                    testLog.sendToConsoleMessage("#INFO [GetLastMessageDialogServlet] message: "+message);
                    if (message != null){
                        otherService.answerToClient(response, new Gson().toJson(message));
                    }else{
                        testLog.sendToConsoleMessage("#ERROR [GetLastMessageDialogServlet] message not found!");
                        otherService.sendToClient(response, 204);
                    }

                }
            }
        }
    }
}

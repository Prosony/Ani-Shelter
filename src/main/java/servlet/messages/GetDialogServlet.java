package servlet.messages;

import com.google.gson.Gson;
import memcache.JsonWebTokenCache;
import model.account.Account;
import model.message.Dialog;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/dialogs")
public class GetDialogServlet extends HttpServlet{

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();
    private TestLog log = new TestLog();
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JSONObject json = new JsonHandler().getJsonFromRequest(request);
        String token = String.valueOf(json.get("token"));
        String idInterlocutor = String.valueOf(json.get("id_interlocutor"));

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) { //TODO write cache
                SelectQueryDB selectQueryDB = new SelectQueryDB();
                if (idInterlocutor != null && !idInterlocutor.isEmpty() && !idInterlocutor.equalsIgnoreCase("null")){
                    System.out.println("idInterlocutor: "+idInterlocutor);
                    Dialog dialog = selectQueryDB.getDialogByIdInterlocutor(account.getId().toString(),idInterlocutor);
                    if (dialog != null){
                        otherService.answerToClient(response, new Gson().toJson(dialog));
                    }else{
                        log.sendToConsoleMessage("#INFO [GetDialogServlet] DIALOG not found by [id_interlocutor]!");
                    }
                }else{
                    System.out.println("#INFO [GetDialogServlet] SEND ALL DIALOGS BY TOKEN!");
                    ArrayList<Dialog> list = selectQueryDB.getAllDialogsByIdAccount(account.getId().toString());
                    if (!list.isEmpty()){
                        otherService.answerToClient(response, new Gson().toJson(list));
                    }else{
                        log.sendToConsoleMessage("#INFO [GetDialogServlet] DIALOGS not found by [token]!");
                    }
                }
            }else{
                log.sendToConsoleMessage("#INFO [GetDialogServlet] ACCOUNT not found by [token]!");
            }
        }
    }

}

package servlet.messages;

import com.google.gson.Gson;
import memcach.JsonWebTokenCache;
import model.account.Account;
import model.message.Dialog;
import org.json.simple.JSONObject;
import services.db.InsertQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet("/dialogs/add")
public class AddDialogServlet extends HttpServlet {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();
    private TestLog log = new TestLog();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JsonHandler().getJsonFromRequest(request);
        String token = String.valueOf(json.get("token"));
        String idAccountOutcoming = String.valueOf(json.get("id_account_outcoming"));
        String idAccountIncoming = String.valueOf(json.get("id_account_incoming"));

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) {
                InsertQueryDB db = new InsertQueryDB();
                String idDialog = db.insertDialog(idAccountOutcoming,idAccountIncoming);
                if (idDialog != null ){
                    Dialog dialog = new Dialog(UUID.fromString(idDialog),UUID.fromString(idAccountOutcoming),UUID.fromString(idAccountIncoming));
                    otherService.answerToClient(response,new Gson().toJson(dialog));
                }else{
                    log.sendToConsoleMessage("#ERROR [GetDialogServlet] something wrong!");
                    otherService.sendToClient(response,500);
                }
            } else{
                log.sendToConsoleMessage("#INFO [GetDialogServlet] ACCOUNT not found by [token]!");
                otherService.sendToClient(response,401);
            }
        }else{
            log.sendToConsoleMessage("#INFO [GetDialogServlet] TOKEN not found!");
            otherService.sendToClient(response,401);
        }

    }
}
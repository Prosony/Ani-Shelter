package servlet.messages;

import com.google.gson.Gson;
import memcach.JsonWebTokenCache;
import model.account.Account;
import model.message.Dialog;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/dialogs")
public class GetAllDialogServlet extends HttpServlet{

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String token = new JsonHandler().getJsonFromRequest(request).get("token").toString();

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByJws(token);
            if (account != null) { //TODO write cache
                SelectQueryDB selectQueryDB = new SelectQueryDB();
                ArrayList<Dialog> list = selectQueryDB.getAllDialogsByIdAccount(account.getId().toString());
                if (!list.isEmpty()){
                    otherService.answerToClient(response, new Gson().toJson(list));
                }
            }
        }
    }

}

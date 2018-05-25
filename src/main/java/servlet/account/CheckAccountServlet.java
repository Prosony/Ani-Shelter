package servlet.account;

import com.google.gson.Gson;
import memcache.AccountCache;
import model.account.Account;
import services.json.JsonHandler;
import services.other.OtherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet("/check-account")
public class CheckAccountServlet extends HttpServlet {

    private AccountCache accountCache = AccountCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        UUID id = UUID.fromString((String) new JsonHandler().getJsonFromRequest(request).get("id"));
        if (id != null && !id.toString().isEmpty() && !id.toString().equals("null")) {
            Account account = accountCache.getAccountById(id);
            if (account != null) {
                otherService.answerToClient(response, new Gson().toJson(true));
            } else {
                otherService.answerToClient(response, new Gson().toJson(false));
            }
        } else {
            otherService.answerToClient(response, new Gson().toJson(false));
        }
    }
}


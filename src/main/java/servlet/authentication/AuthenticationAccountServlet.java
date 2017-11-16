package servlet.authentication;
/**
 * @author Prosony
 * @since 0.0.1
 */
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import memcach.AccountCache;
import memcach.JsonWebTokenCache;
import model.callback.AccountCallBack;
import org.json.simple.JSONObject;
import services.*;
import model.account.Account;
import services.account.AccountService;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authentication/sign-in")
public class AuthenticationAccountServlet extends HttpServlet {


    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private OtherService otherService = new OtherService();
    /**
     * POST method check account by id. If phone and password equals password and email,
     * which we get,
     * will return JSON id account and json web token.
     * @param request
     * @param response
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String email = (String) jsonObject.get("email");
        String password = (String) jsonObject.get("password");

        if ((email != null && !email.equals("")) && (password != null && !password.equals(""))){

            Account account = new AccountService().searchAccountByEmail(email); //check in cache


            if (account != null) {
                testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] [CACHE]: "+account);

                if (email.equalsIgnoreCase(account.getEmail()) && password.equals(account.getPassword())) {
                    String compactJws = new JWTService().createJWT(request);

                    if (tokenCache.getAccountByJws(compactJws) == null){
                        tokenCache.addJws(compactJws, account);
                        otherService.answerToClient(response, new Gson().toJson(new AccountCallBack(account.getId(),compactJws)));
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] Account already online!");
                    }
                }else {
                    testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] Email or password is invalid");
                    otherService.errorToClient(response, 204);
                }
            }else {
                testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] Account not found in cache, search in db");
                SelectQueryDB selectQueryDB = new SelectQueryDB();
                account = selectQueryDB.getAccountByEmail(email, password);

                if (account != null){
                    testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] [DB]: "+account);
                    String compactJws = new JWTService().createJWT(request);
                    tokenCache.addJws(compactJws, account);
                    otherService.answerToClient(response, new Gson().toJson(new AccountCallBack(account.getId(), compactJws)));
                }else{
                    testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] [ERROR] Account not found in db");
                    otherService.errorToClient(response, 204);
                }
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class AuthenticationAccountServlet] Phone or password is invalid: email: "+email+" and password: "+password);
            otherService.errorToClient(response, 204);
        }
    }
}

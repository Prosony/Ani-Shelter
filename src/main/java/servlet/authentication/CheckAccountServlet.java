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
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/authentication/sign-in")
public class CheckAccountServlet extends HttpServlet {


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
            Account account = new AccountService().searchAccountByEmail(email);
            testLog.sendToConsoleMessage("sing in: "+account);

            if (account != null) {
                if (email.equalsIgnoreCase(account.getEmail()) && password.equals(account.getPassword())) {

                    String compactJws = new JWTService().createJWT(request);

                    if (tokenCache.getAccountByJws(compactJws) == null){
                        testLog.sendToConsoleMessage("#TEST [class CheckAccountServlet] compactJws "+compactJws);
                        tokenCache.addJws(compactJws, account);
                        otherService.answerToClient(response, new Gson().toJson(new AccountCallBack(account.getId(),compactJws)));
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class CheckAccountServlet] Account already online!");
                    }
                }else {
                    testLog.sendToConsoleMessage("#TEST [class CheckAccountServlet] Email or password is invalid");
                    otherService.errorToClient(response, 400);
                }
            }else {
                testLog.sendToConsoleMessage("#TEST [class CheckAccountServlet] Account not found");
                otherService.errorToClient(response, 400);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class CheckAccountServlet] Phone or password is invalid: email: "+email+" and password: "+password);
            otherService.errorToClient(response, 400);
        }
    }
}

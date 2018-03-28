package servlet.authentication;
/**
 * @author Prosony
 * @since 0.0.1
 */
import memcach.JsonWebTokenCache;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/authentication/sign-out")
public class SingOutServlet extends HttpServlet {

    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private TestLog testLog = TestLog.getInstance();

    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        String token = (String) new JsonHandler().getJsonFromRequest(request).get("token");

        if (token != null && !token.equals("")){
            if(tokenCache.getAccountByJws(token) != null) {
                tokenCache.deleteJws(token);
                testLog.sendToConsoleMessage("#TEST [class SingOutServlet] Token was deleted from cache ");
            }else{
                testLog.sendToConsoleMessage("#TEST [class SingOutServlet] Can't find account by token");
                otherService.sendToClient(response,204);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class SingOutServlet] Token was not found");
            otherService.sendToClient(response,401);
        }
    }


}

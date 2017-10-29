package servlet.postad;
/**
 *
 * @author Prosony
 * @since 0.2.6
 */
import com.google.gson.Gson;
import memcach.JsonWebTokenCache;
import memcach.PostAdCache;
import model.account.Account;
import model.ad.PostAd;
import org.json.simple.JSONObject;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.UUID;

@WebServlet("/post-ad")
public class PostAdServlet extends HttpServlet {


    private TestLog testLog = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private PostAdCache postAdCache= PostAdCache.getInstance();
   private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();


    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = (String) jsonObject.get("token");
        String id = (String) jsonObject.get("id");
        testLog.sendToConsoleMessage("#TEST [class PostAdServlet] idAccount: "+id );
        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {

            if (id != null && !id.isEmpty() && !id.equals("null")){
                UUID idAccount = UUID.fromString(id);
                Account account = tokenCache.getAccountByJws(jwtToken);

                if (account != null) {

                    ArrayList<PostAd> content = postAdCache.getListPostAdByIdAccount(idAccount);

                    if (content != null && !content.isEmpty()){
                        otherService.answerToClient(response, new Gson().toJson(content));
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class PostAdServlet] post ad not found");
                    }

                }else{
                    testLog.sendToConsoleMessage("#TEST [class PostAdServlet] account not found");
                    otherService.errorToClient(response, 401);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class PostAdServlet] id account is null, id: "+id);
                otherService.errorToClient(response, 401);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class PostAdServlet] token not found");
            otherService.errorToClient(response, 401);
        }
    }
}

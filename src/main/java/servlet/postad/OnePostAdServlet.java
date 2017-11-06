package servlet.postad;

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
import java.util.UUID;

@WebServlet("/post-ad-one")
public class OnePostAdServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = (String) jsonObject.get("token");
        UUID id = UUID.fromString((String) jsonObject.get("id"));
        testLog.sendToConsoleMessage("#TEST [class PostAdServlet] idAccount: " + id);

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {

            if (id != null && !id.toString().isEmpty() && !id.toString().equals("null")) {

                Account account = tokenCache.getAccountByJws(jwtToken);

                if (account != null) {
                    PostAdCache postAdCache = PostAdCache.getInstance();
                    PostAd postAd = postAdCache.getPostAdByIdPostAd(id);
                    if (postAd != null){
                        testLog.sendToConsoleMessage("#TEST [class OnePostAdServlet] [SUCCESS] postAd: "+postAd );
                        otherService.answerToClient(response, new Gson().toJson(postAd));
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class OnePostAdServlet] post ad not found");
                        otherService.errorToClient(response, 204);
                    }
                }else{
                    testLog.sendToConsoleMessage("#TEST [class OnePostAdServlet] account not found");
                    otherService.errorToClient(response, 401);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class OnePostAdServlet] id post ad not found");
                otherService.errorToClient(response, 204);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class OnePostAdServlet] token not found");
            otherService.errorToClient(response, 204);
        }
    }
}
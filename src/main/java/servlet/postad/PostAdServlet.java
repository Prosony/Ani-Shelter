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
import services.db.SelectQueryDB;
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


    private TestLog log = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private PostAdCache postAdCache= PostAdCache.getInstance();
   private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();


    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String token = (String) jsonObject.get("token");
        String id = (String) jsonObject.get("id");
        log.sendToConsoleMessage("#TEST [class PostAdServlet] idAccount: "+id );
        if (token != null && !token.isEmpty() && !token.equals("null")) {

            if (id != null && !id.isEmpty() && !id.equals("null")){
                UUID idAccount = UUID.fromString(id);
                Account account = tokenCache.getAccountByToken(token);

                if (account != null) {

                    ArrayList<PostAd> content = postAdCache.getListPostAdByIdAccount(idAccount);

                    if (content != null && !content.isEmpty()){
                        otherService.answerToClient(response, new Gson().toJson(content));
                    }else{
                        log.sendToConsoleMessage("#TEST [class PostAdServlet] post ad not found in cache");
                        SelectQueryDB selectQueryDB = new SelectQueryDB();

                        ArrayList<PostAd> list =  selectQueryDB.getAllPostAdByIdAccount(idAccount);
                        System.out.println("#TEST [class PostAdServlet] [DB] list post_ad from DB "+list);
                        otherService.answerToClient(response, new Gson().toJson(list));
                    }

                }else{
                    log.sendToConsoleMessage("#TEST [class PostAdServlet] account not found");
                    otherService.sendToClient(response, 401);
                }
            }else{
                log.sendToConsoleMessage("#TEST [class PostAdServlet] id account is null, id: "+id);
                otherService.sendToClient(response, 401);
            }
        }else{
            log.sendToConsoleMessage("#TEST [class PostAdServlet] token not found");
            otherService.sendToClient(response, 401);
        }
    }
}

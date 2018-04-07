package servlet.people;
/**
 * @author Prosony
 * @since 0.0.1
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

@WebServlet("/all-post-ad")
public class PeopleAdServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private PostAdCache postAdCache = PostAdCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String token = (String) jsonObject.get("token");

        if (token != null && !token.isEmpty() && !token.equals("null")) {

            Account account = tokenCache.getAccountByToken(token);

            if (account != null) {
                SelectQueryDB selectQueryDB = new SelectQueryDB();
                ArrayList<PostAd> list =selectQueryDB.getPostAdPeople(account.getId());
                otherService.answerToClient(response, new Gson().toJson(list));
            }else{
                testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] Account not found");
                otherService.sendToClient(response,401);
            }
        }else {
            testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] Token not found");
            otherService.sendToClient(response, 401);
        }
    }
}


//    Set<UUID> idAll = postAdCache.getAllIdPostAd();
//                testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] all id post ad: "+idAll);
//
//                        if (idAll != null && !idAll.isEmpty()) {
//
//                        UUID idAccount = account.getId();
//                        ArrayList<PostAd> listAd = new ArrayList<>();
//        PostAd postAd;
//        for (UUID idPostAd : idAll) {
//        postAd = postAdCache.getPostAdByIdPostAd(idPostAd);
//        if (!idAccount.equals(postAd.getIdAccount())){ //do something with favorites post ad
//        listAd.add(postAd);
//        }
//        }
//        testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] list ad people: "+new Gson().toJson(listAd));
//
//        if (!listAd.isEmpty()){
//        otherService.answerToClient(response, new Gson().toJson(listAd));
//        }else{
//        testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] ad people not found (null or empty)");
//        otherService.sendToClient(response,204);
//        }
//
//        }else{
//        testLog.sendToConsoleMessage("#TEST [class PeopleAdServlet] not found post ad in cache");
//
//        otherService.sendToClient(response,204);
//        }

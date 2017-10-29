package servlet.postad;
/**
 *
 * @author Prosony
 * @since 0.0.1
 */
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

@WebServlet("/post-content/add")
public class AddPostAdServlet extends HttpServlet{

    private TestLog testLog = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private PostAdCache postAdCache = PostAdCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = (String) jsonObject.get("token");
        String postText = (String) jsonObject.get("text");
        String header = (String) jsonObject.get("header");
        String pathToImageFirst = (String) jsonObject.get("image-fist");
        String pathToImageSecond = (String) jsonObject.get("image-second");

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {
            Account account = tokenCache.getAccountByJws(jwtToken);
            if (account != null) {
                UUID idAccount = account.getId();
                ArrayList<PostAd> content = postAdCache.getListPostAdByIdAccount(idAccount);
               if (content != null && !content.isEmpty()){
                   //TODO SOMETHING WITH BITS IMAGE
                   PostAd postAd = new PostAd(idAccount, header, postText,pathToImageFirst,pathToImageSecond);
                   content.add(postAd);

                   postAdCache.addPostAd(postAd.getId(),postAd);
                   postAdCache.deleteListPostAdByIdAccount(idAccount);

                   postAdCache.addListPostAd(idAccount, content);
               }else{
                   testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] post content is empty");
                   content = new ArrayList<>();
                   //TODO SOMETHING WITH BITS IMAGE
                   PostAd postAd = new PostAd(idAccount, header, postText,pathToImageFirst,pathToImageSecond);
                   content.add(postAd);
                   postAdCache.addPostAd(postAd.getId(),postAd);
                   postAdCache.addListPostAd(idAccount,content);

               }
            }else{
                testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] account not found");
                otherService.errorToClient(response, 401);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] token not found");
            otherService.errorToClient(response, 401);
        }
    }
}

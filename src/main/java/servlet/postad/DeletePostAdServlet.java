package servlet.postad;
/**
 *
 * @author Prosony
 * @since 0.2.7.2
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

@WebServlet("/post-ad/delete")
public class DeletePostAdServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private PostAdCache postAdCache = PostAdCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = (String) jsonObject.get("token");
        UUID idPostAd  = (UUID) jsonObject.get("id");


        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {
            Account account = tokenCache.getAccountByJws(jwtToken);
            if (account != null) {
                UUID idAccount = account.getId();

                ArrayList<PostAd> contents = postAdCache.getListPostAdByIdAccount(idAccount);

                if (idPostAd != null && !idPostAd.toString().isEmpty() && !idPostAd.toString().equals("null")){
                    if (contents != null) {

                        int indexForDelete = 0;
                        boolean delete = false;

                        for (int index = 0; index < contents.size(); index++) {

                            UUID idPost = contents.get(index).getId();
                            UUID idPostByAccount = contents.get(index).getIdAccount();

                            testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] idPost: "+idPost+", id: "+idPostAd);
                            testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] idPostByAccount: "+idPostByAccount+", idAccount: "+idAccount);
                            if (idPost.equals(idPostAd) && idPostByAccount.equals(idAccount)) {
                                indexForDelete = index;
                                delete = true;
                                break;
                            }
                        }
                        if (delete) {
                            postAdCache.deletePostContentByIdPostAd(contents.get(indexForDelete).getId());
                            contents.remove(indexForDelete);
                            testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] post:"+indexForDelete+" was deleted, contents: "+contents);
                            postAdCache.deleteListPostAdByIdAccount(idAccount);
                            postAdCache.addListPostAd(idAccount, contents);
                        } else {
                            testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] pust not found");
                            otherService.errorToClient(response, 204);
                        }
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] contents is null");
                        otherService.errorToClient(response,204);
                    }
                }else{
                    testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] idPostAd is null , idPostAd: "+idPostAd);
                    otherService.errorToClient(response,400);
                }


            }else{
                testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] account not found");
                otherService.errorToClient(response,401);
            }
        }else {
            otherService.errorToClient(response,401);
            testLog.sendToConsoleMessage("#TEST [class DeletePostAdServlet] token not found");
        }
    }
}
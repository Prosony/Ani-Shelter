package servlet.tags;

import memcach.JsonWebTokenCache;
import model.account.Account;
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

@WebServlet("/tags/own")
public class TagsOwnServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        JsonHandler jsonHandler = new JsonHandler();
        JSONObject jsonObject = jsonHandler.getJsonFromRequest(request);
        String token = (String) jsonObject.get("token");
        String title = jsonObject.get("title").toString();


        if (token != null && !token.isEmpty()) {
            if(title != null && !title.isEmpty() && !title.equals("null")){
                Account account = tokenCache.getAccountByJws(token);
                if (account != null) {
                    SelectQueryDB selectQueryDB = new SelectQueryDB();
                    ArrayList<String> list = selectQueryDB.getTagsByTitle(title);

                    if (!list.isEmpty()){
                        testLog.sendToConsoleMessage("#TEST [class TagsOwnServlet] list tags by title ["+title+"]: "+list);
                        otherService.answerToClient(response, list.toString());
                    }else{
                        testLog.sendToConsoleMessage("#TEST [class TagsOwnServlet] tags not found by title");
                        otherService.sendToClient(response, 204);
                    }
                }else{
                    otherService.sendToClient(response, 401);
                }
            }else{
                otherService.sendToClient(response, 401);
            }
        }else{
            otherService.sendToClient(response, 204);
        }
    }
}
//select * from tags where tags.json_tag->'$[0]' like CONCAT('%','lar','%');
package servlet.authentication;

import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/check/email")
public class EmailIsUsedServlet extends HttpServlet {

    private TestLog log = TestLog.getInstance();
    private OtherService otherService = new OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String email = new JsonHandler().getJsonFromRequest(request).get("email").toString();
        if (email != null && !email.isEmpty()){
            SelectQueryDB db = new SelectQueryDB();
            boolean isUsed = db.emailIsUsed(email);
//            JsonObject jsonObject = new JsonObject();
            JSONObject object = new JSONObject();
            object.put("is_used", isUsed);
//            jsonObject.addProperty("email", email);
            log.sendToConsoleMessage("#INFO [EmailsIsUsedServlet] jsonObject: " + object.toJSONString());
            otherService.answerToClient(response,object.toJSONString());
        }else{
            otherService.sendToClient(response,204);
        }
    }
}


//    String email = new JsonHandler().getJsonFromRequest(request).get("email").toString();
//        if (email != null && !email.isEmpty()){
//                SelectQueryDB db = new SelectQueryDB();
//                boolean isUsed = db.emailIsUsed(email);
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("email", email);
//                log.sendToConsoleMessage("#INFO [EmailsIsUsedServlet] jsonObject: " + jsonObject.getAsString());
//                otherService.answerToClient(response,jsonObject.getAsString());
//                }else{
//                otherService.sendToClient(response,204);
//                }
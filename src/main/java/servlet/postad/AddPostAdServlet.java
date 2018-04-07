package servlet.postad;
/**
 *
 * @author Prosony
 * @since 0.0.1
 */

import com.google.gson.*;
import memcach.JsonWebTokenCache;
import model.account.Account;
import org.json.simple.JSONObject;
import services.FileService;
import services.db.InsertQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/post-content/add")
public class AddPostAdServlet extends HttpServlet{

    private TestLog testLog = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String token = jsonObject.get("token").toString();
        String stringText = jsonObject.get("array_text").toString();
        String stringTags = jsonObject.get("array_tags").toString();
        String stringImageBase64 = jsonObject.get("array_image").toString();

        String data = jsonObject.get("timestamp").toString();
        Long time = Long.valueOf(data);
        Timestamp timestamp = new Timestamp(time);
        testLog.sendToConsoleMessage("#INFO [AddPostAdServlet][/post-content/add] jsonText: "+stringText+", stringTags: "+stringTags);

        JsonParser jsonParser = new JsonParser();
        JsonArray objectJsonImageBase64 = (JsonArray)jsonParser.parse(stringImageBase64);
        JsonObject jsonText = (JsonObject)jsonParser.parse(stringText);
        JsonObject jsonTags = (JsonObject)jsonParser.parse(stringTags);

        LocalDate date = LocalDate.now();
        jsonText.addProperty("date", date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear());

        if (token != null && !token.isEmpty() && !token.equals("null")) {
            Account account = tokenCache.getAccountByToken(token);
            if (account != null) {
                UUID idAccount = account.getId();
                UUID idPostAd = UUID.randomUUID();
                JsonArray jsonArrayPath = new FileService().saveArrayFileOnFS(idAccount, idPostAd, objectJsonImageBase64);
                if (jsonArrayPath != null && !jsonArrayPath.isJsonNull()){
                    new InsertQueryDB().insertPostAd(idPostAd,idAccount,jsonText,jsonTags,jsonArrayPath, timestamp);
                    testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] post ad was add!");
                    otherService.answerToClient(response,"true");
                }else{
                    testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] jsonArrayPath.isJsonNull()");
                    otherService.sendToClient(response, 500);
                }
            }else{
                testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] account not found");
                otherService.sendToClient(response, 401);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] token not found");
            otherService.sendToClient(response, 401);
        }
    }

}

package servlet.postad;
/**
 *
 * @author Prosony
 * @since 0.0.1
 */

import com.google.gson.*;
import memcach.JsonWebTokenCache;
import memcach.PostAdCache;
import model.account.Account;
import org.json.simple.JSONObject;
import services.db.InsertQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@WebServlet("/post-content/add")
public class AddPostAdServlet extends HttpServlet{

    private TestLog testLog = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    private PostAdCache postAdCache = PostAdCache.getInstance();
    private JsonWebTokenCache tokenCache = JsonWebTokenCache.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String jwtToken = jsonObject.get("token").toString();
        String stringText = jsonObject.get("array_text").toString();
        String stringTags = jsonObject.get("array_tags").toString();
        String stringImageBase64 = jsonObject.get("array_image").toString();

        testLog.sendToConsoleMessage("jwtToken: "+jwtToken+", jsonText: "+stringText+", stringTags: "+stringTags);

        JsonParser jsonParser = new JsonParser();
        JsonArray objectJsonImageBase64 = (JsonArray)jsonParser.parse(stringImageBase64);
        JsonObject jsonText = (JsonObject)jsonParser.parse(stringText);
        JsonObject jsonTags = (JsonObject)jsonParser.parse(stringTags);

        LocalDate date = LocalDate.now();
        jsonText.addProperty("date", date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear());

        if (jwtToken != null && !jwtToken.isEmpty() && !jwtToken.equals("null")) {
            Account account = tokenCache.getAccountByJws(jwtToken);
            if (account != null) {
                UUID idAccount = account.getId();
                UUID idPostAd = UUID.randomUUID();
                JsonArray jsonArrayPath = saveFileOnFS(idAccount, idPostAd, objectJsonImageBase64);
                if (jsonArrayPath != null && !jsonArrayPath.isJsonNull()){
                    new InsertQueryDB().insertPostAd(idPostAd,idAccount,jsonText,jsonTags,jsonArrayPath);
                    testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] post ad was add!");
                }else{
                    testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] jsonArrayPath.isJsonNull()");
                    otherService.errorToClient(response, 500);
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
    private JsonArray saveFileOnFS(UUID idAccount, UUID idPostAd, JsonArray objectJsonImageBase64){

        JsonArray resultArray = new JsonArray();

        boolean isCreated;
        try {
            String path = "E:/file/"+idAccount+"/"+idPostAd;
            Path pathCheck = Paths.get(path);
            if (!Files.exists(pathCheck)) {
                File folder = new File(path);
                isCreated = folder.mkdir();
            } else {
                isCreated = true;
            }
            if (isCreated){
                Random random = new Random();
                int value;

                for(int index = 0; index < objectJsonImageBase64.size(); index++){

                    value = random.nextInt(1000); //TODO rewrite this shit
                    resultArray.add(path+"/"+objectJsonImageBase64.get(index).getAsString().substring(value,value+10)+".jpg"); //TODO rewrite this shit

                    File newTextFile = new File(resultArray.get(index).getAsString());
                    FileWriter fw = new FileWriter(newTextFile);
                    fw.write(objectJsonImageBase64.get(index).getAsString());
                    fw.close();
                }
                testLog.sendToConsoleMessage("resultArray: "+resultArray);
                return resultArray;
            }else{
                testLog.sendToConsoleMessage("#TEST [class AddPostAdServlet] [saveFileOnFS] [ERROR]: Something wrong with path [E:/file/"+idAccount+"/"+idPostAd+"]");
            }
        } catch (IOException iox) {
            iox.printStackTrace();
        }
        return null;
    }
}

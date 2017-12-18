package servlet.others;

import com.google.gson.Gson;
import model.message.Messages;
import org.json.simple.JSONObject;
import services.db.SelectQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet("/generate-uuid")
public class GenerateUuidServlet extends HttpServlet {

    private OtherService otherService = new OtherService();
    private TestLog testLog = TestLog.getInstance();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonHandler = new JsonHandler().getJsonFromRequest(request);
        String tableTitle = jsonHandler.get("table").toString();
        SelectQueryDB selectQueryDB = new SelectQueryDB();
        UUID uuid;

        if (tableTitle.equalsIgnoreCase("messages")){
            while(true){
                uuid = UUID.randomUUID();
                Messages message = selectQueryDB.getMessageByIdMessage(uuid.toString());
                if (message == null){
                    break;
                }
                testLog.sendToConsoleMessage("#INFO [GenerateUuidServlet] ["+tableTitle+"]  uuid: "+uuid+" is not unique");
            }
            testLog.sendToConsoleMessage("#INFO [GenerateUuidServlet] ["+tableTitle+"] [SUCCESS] generate unique uuid: "+uuid);
            otherService.answerToClient(response, new Gson().toJson(uuid));
        }
    }
}

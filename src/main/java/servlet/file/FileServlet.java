package servlet.file;


import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.FileService;
import services.other.OtherService;
import services.json.JsonHandler;
import test.TestLog;
/**
 * @author Prosony
 * @since 0.0.1
 */

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/files")
public class FileServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();

    private OtherService otherService = new  OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONArray path = (JSONArray) new JsonHandler().getJsonFromRequest(request).get("path");

        if (path != null && !path.isEmpty()){

            testLog.sendToConsoleMessage("#TEST [class FileServlet] [JSON]: "+path.get(0)+ " [SIZE]:"+path.size());
            ArrayList<String> base64image = new ArrayList<>();
            FileService service = new FileService();

            int index = 0;
            for (Object aPath : path) {

                testLog.sendToConsoleMessage("#TEST [class FileServlet] [INDEX]: "+index+" [PATH]: "+aPath.toString());
                base64image.add(new String(service.getFileByPath(aPath.toString())));
                index++;
            }
//            for (Object base64: base64image){
//                System.out.println("#TEST [class FileServlet] base64: "+base64);
//            }
            if (!base64image.isEmpty()){

                otherService.answerToClient(response, new Gson().toJson(base64image));
            }else{
                testLog.sendToConsoleMessage("#TEST [class FileServlet] file not found");
                otherService.errorToClient(response,204);
            }
        }else{
            testLog.sendToConsoleMessage("#TEST [class FileServlet] path is empty");
            otherService.errorToClient(response,400);
        }

        //
//            System.out.println(user);
//        }
//        JsonParser parser = new JsonParser();
//        JsonObject mainObject = parser.parse(path).getAsJsonObject();
//        JsonArray pItem = mainObject.getAsJsonArray("path");

//        for (JsonElement user : pItem) {
//
//            System.out.println(user);
//        }
//        if (path != null && !path.isEmpty()){
//
//            otherService.answerToClient(response,new Gson().toJson(new String(new FileService().getFileByPath(path))));
//        }else{
//            testLog.sendToConsoleMessage("#TEST [class FileServlet] path is empty");
//            otherService.errorToClient(response,400);
//        }

    }


}

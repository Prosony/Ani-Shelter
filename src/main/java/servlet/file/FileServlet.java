package servlet.file;


import com.google.gson.*;
import memcach.FileCache;
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
    private FileCache cache = FileCache.getInstance();
    private OtherService otherService = new  OtherService();


    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONArray path = (JSONArray) new JsonHandler().getJsonFromRequest(request).get("path");

        if (path != null && !path.isEmpty()){

            ArrayList<String> base64image = new ArrayList<>();
            FileService service = new FileService();

            int index = 0;
            for (Object aPath : path) {

                String aPathString = aPath.toString();

                String base64 = cache.getFileByPath(aPathString);

                if (base64 != null && !base64.isEmpty()){
                    testLog.sendToConsoleMessage("#TEST [class FileServlet] file from cache: [INDEX]: "+index+" [PATH]: "+aPathString);
                    base64image.add(base64);
                    index++;
                }else{
                    testLog.sendToConsoleMessage("#TEST [class FileServlet] file from FS: [INDEX]: "+index+" [PATH]: "+aPathString);
                    String file = new String(service.getFileByPath(aPathString));
                    base64image.add(file);
                    cache.addListFavorites(aPathString, file);
                    index++;
                }
            }

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
    }


}

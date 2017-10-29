package servlet.file;


import com.google.gson.Gson;
import org.json.simple.JSONObject;
import services.FileService;
import services.other.OtherService;
import test.CheckProperties;
import services.json.JsonHandler;
import test.TestLog;
/**
 * @author Prosony
 * @since 0.2.4
 */

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/files")
public class FileServlet extends HttpServlet {

    private TestLog testLog = TestLog.getInstance();

    private OtherService otherService = new  OtherService();

    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        testLog.sendToConsoleMessage("#TEST [class FileServlet] jsonObject.toJSONString(): "+ jsonObject.toJSONString());
        String path = (String) jsonObject.get("path");

        if (path != null && !path.isEmpty()){

            otherService.answerToClient(response,new Gson().toJson(new String(new FileService().getFileByPath(path))));
        }else{
            testLog.sendToConsoleMessage("#TEST [class FileServlet] path is empty");
            otherService.errorToClient(response,400);
        }

    }


}

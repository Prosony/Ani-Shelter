package servlet.authentication;

import com.google.gson.JsonArray;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.FileService;
import services.account.AccountService;
import services.db.InsertQueryDB;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet("/get-started")
public class GetStartedServlet extends HttpServlet {

    private TestLog log = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
        String email = jsonObject.get("email").toString();
        String password = jsonObject.get("password").toString();
        String name = jsonObject.get("name").toString();
        String surname = jsonObject.get("surname").toString();
        String phone = jsonObject.get("phone").toString();
        String birthday = jsonObject.get("birthday").toString();
//        String pathToAvatar =  jsonObject.get("path_avatar").toString();
        String stringImageBase64 = jsonObject.get("base64_image").toString();
        String about = jsonObject.get("about").toString();
        String dateCreateAccount = jsonObject.get("date_create_account").toString();
        FileService fileService = new FileService();
        JsonArray array = new JsonArray ();
        array.add(stringImageBase64);
        JsonArray path = fileService.saveArrayFileOnFS(UUID.randomUUID(), UUID.randomUUID(), array);
        String pathToAvatar = path.get(0).getAsString();
        boolean allRight = new AccountService().checkData(email,password,name,surname,phone,birthday,pathToAvatar,about,dateCreateAccount);
       if (allRight){
           InsertQueryDB db = new InsertQueryDB();
           db.insertAccount(email,password,name,surname,phone,birthday,pathToAvatar,about,dateCreateAccount);
           otherService.sendToClient(response,200);
       }else {
           log.sendToConsoleMessage("#ERROR [GetStartedServlet] something wrong with data! [null or empty]");
           otherService.sendToClient(response, 400);
       }

    }
}

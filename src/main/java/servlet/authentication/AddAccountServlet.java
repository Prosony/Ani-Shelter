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
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet("/authentication/account/add")
public class AddAccountServlet extends HttpServlet {

    private TestLog log = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        JSONObject json = new JsonHandler().getJsonFromRequest(request);

        String email = String.valueOf(json.get("email"));
        String password = String.valueOf(json.get("password"));
        String phone = String.valueOf(json.get("phone"));
        String name = String.valueOf(json.get("firstname"));
        String surname = String.valueOf(json.get("lastname"));
        String base64= String.valueOf(json.get("base64avatar"));
        String birthday = String.valueOf(json.get("birthday"));
        String about = String.valueOf(json.get("about"));
        Timestamp timestamp = new Timestamp(Long.valueOf(String.valueOf(json.get("date_create_account"))));

        FileService fileService = new FileService();
        JsonArray array = new JsonArray ();
        array.add(base64);
        JsonArray path = fileService.saveArrayFileOnFS(UUID.randomUUID(), UUID.randomUUID(), array);
        String pathToAvatar = path.get(0).getAsString();

        boolean allRight = new AccountService().checkData(email,password,name,surname,phone,birthday,pathToAvatar,about, timestamp);

        if (allRight){
           InsertQueryDB db = new InsertQueryDB();
           db.insertAccount(email,password,name,surname,phone,birthday,pathToAvatar,about, timestamp);
           otherService.sendToClient(response,200);

        }else {
           log.sendToConsoleMessage("#ERROR [AddAccountServlet] something wrong with data! [null or empty]");
           otherService.sendToClient(response, 400);
           }
    }
}

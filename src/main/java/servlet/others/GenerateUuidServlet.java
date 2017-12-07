package servlet.others;

import com.google.gson.Gson;
import services.other.OtherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet("/generate-uuid")
public class GenerateUuidServlet extends HttpServlet {

    private OtherService otherService = new OtherService();

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        otherService.answerToClient(response, new Gson().toJson(UUID.randomUUID()));
    }
}

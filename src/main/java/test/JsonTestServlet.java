package test;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/json")
public class JsonTestServlet extends HttpServlet{
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        JsonObject jsonText = new JsonObject();
        LocalDate date = LocalDate.now();
        jsonText.addProperty("date", date.getDayOfMonth()+"."+date.getMonthValue()+"."+date.getYear());
        jsonText.addProperty("base64", "E:/file/fc1e7d51-fb0c-4285-9f69-49b6935faaf4/20e021ef-09fb-4acd-bce8-95ed9c751230/AAAAAAAAAA.jpg");


        System.out.println(jsonText);

    }
}

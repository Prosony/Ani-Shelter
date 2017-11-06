package services.json;
/**
 * @author Prosony
 * @since 0.0.1
 */
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import test.TestLog;
import test.CheckProperties;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonHandler {

    private TestLog testLog = TestLog.getInstance();

    public JSONObject getJsonFromRequest(HttpServletRequest request){

        StringBuilder builder = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
            if (!builder.toString().isEmpty()){
                JSONParser parser = new JSONParser();
                testLog.sendToConsoleMessage("#INFO [class JsonHandler] [method getJsonFromRequest] builder.toString(): "+builder.toString());
                Object obj = parser.parse(builder.toString());
                JSONObject jsonObject = (JSONObject) obj;
                testLog.sendToConsoleMessage("#TEST [class JsonHandler] jsonObject: "+jsonObject);
                return jsonObject;
            }
            return null;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    return null;

    }
}

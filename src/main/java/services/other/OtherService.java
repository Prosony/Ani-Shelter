package services.other;
/**
 *
 * @author Prosony
 * @since 0.2.7 alpha
 */

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OtherService {

    public void errorToClient(HttpServletResponse response, int answer) {
        try {

            response.setCharacterEncoding("UTF-8");
            switch (answer){
                case 200:
                    response.sendError(HttpServletResponse.SC_OK);
                    break;
                case 204:
                    response.sendError(HttpServletResponse.SC_NO_CONTENT);
                    break;
                case 400:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                case 401:
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void answerToClient(HttpServletResponse response, String list){
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

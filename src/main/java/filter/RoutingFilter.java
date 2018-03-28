package filter;
/**
 * @author Prosony
 * @since 0.0.1
 */
import org.json.simple.JSONObject;
import services.json.JsonHandler;
import services.other.OtherService;
import test.TestLog;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

public class RoutingFilter implements Filter {
    private TestLog log = TestLog.getInstance();
    private OtherService otherService = new OtherService();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String URL = request.getRequestURL().toString();
        log.sendToConsoleMessage("\n#FILTER [RoutingFilter]" + new Date() +", Remote address: "+request.getRemoteAddr()
                    +", Remote host: "+request.getRemoteHost()+", Servlet Path: " + request.getServletPath() + ", URL: " + request.getRequestURL());

//
//        switch (URL){
//            case "http://185.77.204.249:8080/check-account":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/authentication/sign-in":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/get-started":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
////                break;
//            case "http://185.77.204.249:8080/files":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/generate-uuid":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/js/jquery-3.2.1.min.js":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/js/c13f37b67e.js":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/css/semantic.css":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/js/semantic.js":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/css/themes/default/assets/fonts/icons.ttf":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/css/themes/default/assets/fonts/icons.woff2":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            case "http://185.77.204.249:8080/resources/css/themes/default/assets/fonts/icons.woff":
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            default:
//                JSONObject jsonObject = new JsonHandler().getJsonFromRequest(request);
//                System.out.println("#FILTER json: "+jsonObject);
//                if (jsonObject != null){
//                    String token = String.valueOf(jsonObject.get("token"));
//                    if (token != null && !token.isEmpty()){
//                        filterChain.doFilter(servletRequest, servletResponse);
//                    }else{
//                        otherService.sendToClient(response,401);
//                    }
//                }else{
//                    otherService.sendToClient(response,401);
//                }
//
//        }
//        parseRequest(request);
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private void parseRequest(HttpServletRequest request){
        byte[] data = new byte[request.getContentLength()];
        try {
            InputStream inputStream = request.getInputStream();
            inputStream.read(data);
            System.out.println("#FILTER request byte: "+Arrays.toString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void destroy() { }
}

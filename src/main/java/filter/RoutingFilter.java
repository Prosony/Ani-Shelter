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
        addCorsHeader(response);
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private void addCorsHeader(HttpServletResponse response){
        //TODO: externalize the Allow-Origin
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }
    @Override
    public void destroy() { }
}

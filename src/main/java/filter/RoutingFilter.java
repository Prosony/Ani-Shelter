package filter;
/**
 * @author Prosony
 * @since 0.0.1
 */
import test.TestLog;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

public class RoutingFilter implements Filter {
    private TestLog testLog = TestLog.getInstance();

    public void init(FilterConfig filterConfig) throws ServletException { }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        testLog.sendToConsoleMessage("\n#INFO [FILTER] [class RoutingFilter]" + new Date() +", Remote address: "+request.getRemoteAddr()
                    +", Remote host: "+request.getRemoteHost()+", Servlet Path: " + request.getServletPath() + ", URL: " + request.getRequestURL());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() { }
}

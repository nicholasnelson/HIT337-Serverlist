package io.nicknelson._hit337.serverlist;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class AuthenticationFilter implements Filter {
  
  // The filter configuration object we are associated with.  If
  // this value is null, this filter instance is not currently
  // configured. 
  private FilterConfig filterConfig = null;
  
  public AuthenticationFilter() {
  }  

  /**
   *
   * @param request The servlet request we are processing
   * @param response The servlet response we are creating
   * @param chain The filter chain we are processing
   *
   * @exception IOException if an input/output error occurs
   * @exception ServletException if a servlet error occurs
   */
  public void doFilter(ServletRequest request, ServletResponse response,
          FilterChain chain)
          throws IOException, ServletException {
    
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    
    HttpSession session = req.getSession();
    if(session.getAttribute("username") != null) {
      chain.doFilter(request, response);
    } else {
      res.sendRedirect("./login");
    }
    
  }

  /**
   * Destroy method for this filter
   */
  public void destroy() {    
  }

  /**
   * Init method for this filter
   */
  public void init(FilterConfig filterConfig) {    
  }
  
}

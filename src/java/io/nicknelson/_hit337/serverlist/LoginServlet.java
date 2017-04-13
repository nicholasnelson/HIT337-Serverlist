package io.nicknelson._hit337.serverlist;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class LoginServlet extends HttpServlet {

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
    PrintWriter out = response.getWriter();
    
    out.print("<html><head></head><body>");
    out.print("<h1>Login</h1>");
    out.print("<form action=\"./login\" method=\"POST\">");
    out.print("<label>Username: <input type=\"text\" name=\"username\"></label>");
    out.print("<input type=\"submit\" value=\"Submit\">");
    out.print("</form></body></html>");
    
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession();
    
    String username = request.getParameter("username");
    
    session.setAttribute("username", username);
    
    response.sendRedirect("./");
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Servlet to handle user logins";
  }

}

package io.nicknelson._hit337.serverlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class RootServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   * @throws SQLException if the ServerCatalogue errors
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException, SQLException, Exception {
    // Get the session data
    HttpSession session = request.getSession();  
    // Get data from ServerCatalogue
    ServerCatalogue sc = new DerbyServerCatalogue(this.getServletContext().getInitParameter("DB_NAME"));
    List<ServerItem> serverList;
    
    if(session.getAttribute("username").equals("admin")) {
      serverList = sc.getAllServers();
    } else {
      serverList = sc.getAllServersForUser((String)session.getAttribute("username"));
    }
            

    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet RootServlet</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Server List</h1>");
      out.println("<table>");
      out.println("<tbody>");
      out.println("<tr><th>ID</th><th>Owner</th><th>Hostname</th><th>IP</th><th>Location</th></tr>");
      for (ServerItem s : serverList) {
        out.println("<tr>");
        out.println("<td><a href=\"./edit?id=" + s.getID() + "\">" + s.getID() + "</a></td>");
        out.println("<td>" + s.getOwner() + "</td>");
        out.println("<td>" + s.getHostname() + "</td>");
        out.println("<td>" + s.getIP() + "</td>");
        out.println("<td>" + s.getLocation() + "</td>");
      }
      out.println("</tbody>");
      out.println("</table>");
      out.println("<a href=\"./edit\">New Server</a>");
      out.println("<a href=\"./logout\">Log Out</a>");
      out.println("</body>");
      out.println("</html>");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
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
    try {
      processRequest(request, response);
    } catch (Exception ex) {
      throw new ServletException(ex);
    }
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Displays a list of all servers for the current user";
  }// </editor-fold>

}

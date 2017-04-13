package io.nicknelson._hit337.serverlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class EditServlet extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet Edit</title>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>Servlet Edit at " + request.getContextPath() + "</h1>");
      out.println("</body>");
      out.println("</html>");
    }
  }

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

    HttpSession session = request.getSession();

    ServerItem serverItem;

    try {
      int serverID = Integer.parseInt(request.getParameter("id"));
      ServerCatalogue sc = new DerbyServerCatalogue(this.getServletContext().getInitParameter("DB_NAME"));
      serverItem = sc.getServerWithID(serverID);
    } catch (Exception e) {
      // Invalid serverID, or failure create a new blank server
      serverItem = new ServerItem();
    }

    response.setContentType("text/html;charset=UTF-8");

    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Server</title>");
      out.println("</head>");
      out.println("<body>");
      if(serverItem.getID() > 0) {
        out.println("<h1>Server Edit</h1>");
      } else {
        out.println("<h1>New Server</h1>");
      }
      out.println("<form action=\"./edit\" method=\"POST\">");
      out.println("<div><label>ID: <input type=\"number\" name=\"id\" value=\"" + serverItem.getID() + "\" readonly></label></div>");
      out.println("<div><label>Hostname: <input type=\"text\" name=\"hostname\" value=\"" + serverItem.getHostname() + "\"></label></div>");
      out.println("<div><label>IP: <input type=\"text\" name=\"ip\" value=\"" + serverItem.getIP() + "\"></label></div>");
      out.println("<div><label>Location: <input type=\"text\" name=\"location\" value=\"" + serverItem.getLocation() + "\"></label></div>");
      out.println("<div><input type=\"submit\" value=\"Submit\"></div>");
      out.println("</form>");
      out.println("<form action=\"./edit\" method=\"POST\">");
      out.println("<input type=\"hidden\" name=\"delete\" value=\"delete\" readonly>");
      out.println("<input type=\"hidden\" name=\"id\" value=\"" + serverItem.getID() + "\" readonly>");
      out.println("<div><input type=\"submit\" value=\"Delete\"></div>");
      out.println("</form>");
      out.println("</body>");
      out.println("</html>");
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

    HttpSession session = request.getSession();

    int id = Integer.parseInt(request.getParameter("id"));
    ServerItem serverItem = new ServerItem(
            id,
            (String) session.getAttribute("username"),
            request.getParameter("hostname"),
            request.getParameter("ip"),
            request.getParameter("location"));

    try {
      ServerCatalogue sc = new DerbyServerCatalogue(this.getServletContext().getInitParameter("DB_NAME"));

      if (serverItem.getID() > 0) {
        if (request.getParameter("delete") == null) {
          sc.updateServer(serverItem);
        } else {
          sc.removeServer(serverItem.getID());
        }
      } else {
        sc.addServer(serverItem);
      }
    } catch (Exception e) {
      throw new ServletException(e);
    }
    
    // Redirect to main page
    response.sendRedirect("./");
  }
  
  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Servlet to handle editing server info";
  }

}

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageUserEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        String idStr = req.getParameter("userId"); 
        UserData user = UserData.getUser(connection, idStr);

        toClient.println(Utils.header("User form"));

        toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='Account?userId="+user.ID+"'>Account</a><a style='padding-right:15px' href='Start'>Sign out</a></div>");
        toClient.println("</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='Account?userId="+user.ID+"'>< Back</a>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<h1>Edit information</h1>");
        toClient.println("<form action='UserUpdate' method='GET'>");
        toClient.println("<table style='padding-bottom:15px'>");
        toClient.println("<input type='hidden' name='userId' value='" + user.ID + "' /> ");
        toClient.println("<tr><td>Email</td>");
        toClient.println("<td><input name='email' value='" + user.email + "'></td></tr>");
        toClient.println("<tr><td>First name</td>");
        toClient.println("<td><input name='first_name' value='" + user.first_name + "'></td></tr>");
        toClient.println("<tr><td>Last name</td>");
        toClient.println("<td><input name='last_name' value='" + user.last_name + "'></td></tr>");
        toClient.println("<tr><td>Street</td>");
        toClient.println("<td><input name='street' value='" + user.street + "'></td></tr>");
        toClient.println("<tr><td>Postal code</td>");
        toClient.println("<td><input name='postal_code' value='" + user.postal_code + "'></td></tr>");
        toClient.println("<tr><td>City</td>");
        toClient.println("<td><input name='city' value='" + user.city + "'></td></tr>");
        toClient.println("<tr><td>Password</td>");
        toClient.println("<td><input name='password' value='" + user.password + "'></td></tr>");
        

        toClient.println("</table>");
        toClient.println("<input type='submit' value='Update'>");
        toClient.println("</form>");

        toClient.println(Utils.footer());
        toClient.close();
    }
}
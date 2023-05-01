import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdminUserEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("User form"));

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");
        toClient.println("<div class='body'>");
        toClient.println("<h1>Edit user</h1>");
        toClient.println("<form action='UserUpdate' method='GET'>");
        toClient.println("<table style='padding-bottom:15px'>");

        String idStr = req.getParameter("id"); 
        UserData user = UserData.getUser(connection, idStr);

        toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='ID' value='" + user.ID + "'></td></tr>");
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
        toClient.println("<tr><td>Is admin?</td>");
        System.out.println(user.is_admin);
        if (user.is_admin == 0)
            toClient.println("<td><input type='checkbox' name='is_admin'></td></tr>");
        else 
            toClient.println("<td><input type='checkbox' name='is_admin' checked></td></tr>");

        toClient.println("</table>");
        toClient.println("<input type='submit' value='Update user'>");
        toClient.println("</form>");
        toClient.println("</div>");

        toClient.println(Utils.footer());
        toClient.close();
    }
}
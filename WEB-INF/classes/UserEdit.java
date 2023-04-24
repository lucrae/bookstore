import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class UserEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<form action='UserUpdate' method='GET'>");
        toClient.println("<table border='1'>");
        String idStr = req.getParameter("id"); // important to check the url, so id=.. is in there
        UserData user = UserData.getUser(connection, idStr); 
        toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='ID' value='" + user.ID + "'></td></tr>");
        toClient.println("<tr><td>First name</td>");
        toClient.println("<td><input name='first_name' value='" + user.first_name + "'></td></tr>");
        toClient.println("<tr><td>Last name</td>");
        toClient.println("<td><input name='last_name' value='" + user.last_name + "'></td></tr>");

        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.close();
    }
}
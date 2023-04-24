import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class UserList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        // String city = req.getParameter("ciudad");

        toClient.println("<table border='1'>");
        toClient.println("<tr>");
        toClient.println("<td>ID</td>");
        toClient.println("<td>title</td>");
        toClient.println("<td>author</td>");
        toClient.println("<td>cover_image</td>");
        toClient.println("</tr>");

        Vector<UserData> userList = UserData.getUserList(connection);


        for(int i=0; i< userList.size(); i++){
                UserData user = userList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + user.ID + " </td>");
                toClient.println("<td>" + user.email + " </td>");
                toClient.println("<td>" + user.first_name + " </td>");
                toClient.println("<td><img src='" + user.street + "'> </td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");

        toClient.close();
    }
}
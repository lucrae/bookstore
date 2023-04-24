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
        toClient.println("<td>email</td>");
        toClient.println("<td>first name</td>");
        toClient.println("<td>street</td>");
        toClient.println("<td>edit</td>");
        toClient.println("</tr>");

    /*    
        int newId = userList.size() + 1;
        UserData newUser = new UserData(newId, "'abc@hotmail.com'", "'John'", "'Doe'", "'Cali St'", 789, "'West Side'", "'pass88'", false);
        int nAffected = UserData.registerUser(connection, newUser);
        userList = UserData.getUserList(connection);
    */    
    
        Vector<UserData> userList = UserData.getUserList(connection);
     
        for(int i=0; i< userList.size(); i++){
                UserData user = userList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + user.ID + " </td>");
                toClient.println("<td>" + user.email + " </td>");
                toClient.println("<td>" + user.first_name + " </td>");
                toClient.println("<td>" + user.street + "</td>");
                toClient.println("<td><a href='UserEdit?id=" + user.ID + "'>Edit</a></td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.close();
    }
}
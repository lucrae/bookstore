import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdminUsersSearch extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        String idStr = req.getParameter("search");
        
        toClient.println(Utils.header("Users"));

        toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='AdminUsers'>Users</a><a style='padding-right:15px' href='AdminBooks'>Books</a><a href='Start'>Sign Out</a></div>");
        toClient.println("</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='Admin'>< Back</a>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<br>");

        toClient.println("<div class='left-right'>");
        toClient.println("<div><h1>List of Users</h1>");
        toClient.println("<form action='AdminUsersSearch' method='GET'><input type='text' placeholder='Search User' id='search' name='search'><input type='submit' value='Search'></form>");
        toClient.println("</div>");
        toClient.println("</div>");

        toClient.println("<br>");
        toClient.println("<br>");

        toClient.println("<div>");
        toClient.println("<table class='user-table'>");
        toClient.println("<div>");
        toClient.println("<tr>");
        toClient.println("<td><b>ID</b></td>");
        toClient.println("<td><b>Email</b></td>");
        toClient.println("<td><b>First name</b></td>");
        toClient.println("<td><b>Last name</b></td>");
        toClient.println("<td><b>City</b></td>");
        toClient.println("</tr>");
        toClient.println("</div>");

    /*    
        int newId = userList.size() + 1;
        UserData newUser = new UserData(newId, "'abc@hotmail.com'", "'John'", "'Doe'", "'Cali St'", 789, "'West Side'", "'pass88'", false);
        int nAffected = UserData.registerUser(connection, newUser);
        userList = UserData.getUserList(connection);
    */    
    
        Vector<UserData> userSearch = UserData.getUserSearch(connection, idStr);
     
        for(int i=0; i< userSearch.size(); i++){
                UserData user = userSearch.elementAt(i);
                toClient.println("<div>");
                toClient.println("<tr>");
                toClient.println("<td>" + user.ID + " </td>");
                toClient.println("<td>" + user.email + " </td>");
                toClient.println("<td>" + user.first_name + " </td>");
                toClient.println("<td>" + user.last_name + " </td>");
                toClient.println("<td>" + user.city + "</td>");
                toClient.println("<td><a href='AdminUserEdit?id=" + user.ID + "'>Edit</a></td>");
                toClient.println("</tr>");
                toClient.println("</div>");
        }

        toClient.println("</table>");
        toClient.println("</div>");
        toClient.println("<div>");
        toClient.close();
    }
}
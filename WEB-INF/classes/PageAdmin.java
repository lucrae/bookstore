import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdmin extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Admin"));

        // String city = req.getParameter("ciudad");

        toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='AdminUsers'>Users</a><a style='padding-right:15px' href='/books.html'>Books</a><a href='/''>Sign Out</a></div>");
        toClient.println("</div>");

        toClient.println("<div class='body'>");
        toClient.println("<h2>Welcome, you are logged in as Admin</h2>");
        toClient.println("<h3>Here you can edit and add information</h3>");
        toClient.println("<p>To see a list of all <strong>users</strong> press <a href='AdminUsers'>here.</a></p>");
        toClient.println("<p>To see a list of all <strong>books</strong> press <a href='/Bookstore/BookList'>here.</a></p>");
        
        toClient.close();
    }
}
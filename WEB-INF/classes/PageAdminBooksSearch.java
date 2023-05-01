import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdminBooksSearch extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        String idStr = req.getParameter("search");
        
        toClient.println(Utils.header("Books"));

        toClient.println("<div class='header'>");
        toClient.println("<div class='header-title'>DKL Bookstore</div>");
        toClient.println("<div><a style='padding-right:15px' href='AdminUsers'>Users</a><a style='padding-right:15px' href='AdminBooks'>Books</a><a href='/''>Sign Out</a></div>");
        toClient.println("</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='Admin'>< Back</a>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<br>");

        toClient.println("<div class='left-right'>");
        toClient.println("<div><h1>List of Books</h1><a style='padding-right:15px padding-bottom:10px' href='AdminAddBook'>Add new book</a>");
        toClient.println("<form action='AdminBooksSearch' method='GET'><input type='text' placeholder='Search Book' id='search' name='search'><input type='submit' value='Search'></form>");
        toClient.println("</div>");
        toClient.println("</div>");

        toClient.println("<br>");
        toClient.println("<br>");

        toClient.println("<div>");
        toClient.println("<table class='user-table'>");
        toClient.println("<div>");
        toClient.println("<tr>");
        toClient.println("<td><b>ID</b></td>");
        toClient.println("<td><b>Title</b></td>");
        toClient.println("<td><b>Author</b></td>");
        toClient.println("<td><b>Publish year</b></td>");
        // toClient.println("<td><b>Description</b></td>");
        toClient.println("<td><b>Genre</b></td>");
        // toClient.println("<td><b>Cover image</b></td>");
        // toClient.println("<td><b>Price</b></td>");
        // toClient.println("<td><b>Stock</b></td>");
        toClient.println("</tr>");
        toClient.println("</div>");

    /*    
        int newId = userList.size() + 1;
        UserData newUser = new UserData(newId, "'abc@hotmail.com'", "'John'", "'Doe'", "'Cali St'", 789, "'West Side'", "'pass88'", false);
        int nAffected = UserData.registerUser(connection, newUser);
        userList = UserData.getUserList(connection);
    */    
    
        Vector<BookData> bookList = BookData.getBySearch(connection, idStr);
     
        for(int i=0; i< bookList.size(); i++){
                BookData book = bookList.elementAt(i);
                toClient.println("<div>");
                toClient.println("<tr>");
                toClient.println("<td>" + book.ID + " </td>");
                toClient.println("<td>" + book.title + " </td>");
                toClient.println("<td>" + book.author + " </td>");
                toClient.println("<td>" + book.publish_year + " </td>");
                // toClient.println("<td>" + book.blurb + " </td>");
                toClient.println("<td>" + book.genre + "</td>");
                // toClient.println("<td>" + book.cover_image + "</td>");
                // toClient.println("<td>" + book.price + "</td>");
                // toClient.println("<td>" + book.stock + "</td>");
                toClient.println("<td><a href='AdminBookEdit?id=" + book.ID + "'>Edit</a></td>");
                toClient.println("</tr>");
                toClient.println("</div>");
        }

        toClient.println("</table>");
        toClient.println("</div>");
        toClient.println("<div>");
        toClient.close();
    }
}
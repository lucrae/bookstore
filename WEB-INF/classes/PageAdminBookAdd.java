import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdminBookAdd extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        String idStr = req.getParameter("id"); 
        BookData book = BookData.getBook(connection, idStr);

        toClient.println(Utils.header("Book form"));

        toClient.println("<div class='header header-title'>DKL Bookstore</div>");

        toClient.println("<div class='body'>");
        toClient.println("<a href='AdminBooks'>< Back</a>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<br>");
        toClient.println("<h3>Add a new book:</h3>");
        toClient.println("<form action='BookAdd' method='GET'>");
        toClient.println("<table style='padding-bottom:15px'>");
        
        toClient.println("<tr><td>Title</td>");
        toClient.println("<td><input name='title' required></td></tr>");
        toClient.println("<tr><td>Author</td>");
        toClient.println("<td><input name='author' required></td></tr>");
        toClient.println("<tr><td>Publish year</td>");
        toClient.println("<td><input name='publish_year' required></td></tr>");
        toClient.println("<tr><td>Description</td>");
        toClient.println("<td><input name='blurb' required></td></tr>");
        toClient.println("<tr><td>Genre</td>");
        toClient.println("<td><input name='genre' required></td></tr>");
        toClient.println("<tr><td>Cover image</td>");
        toClient.println("<td><input id='ISBN' onChange='getImage(this.value)' placeholder='ISBN'><button type='button' onclick='getImage(document.getElementById(\"ISBN\").value)'>Get cover image</button></td></tr>");
        toClient.println("<tr><td></td>");
        toClient.println("<td id='cover_img'></td></tr>");
        toClient.println("<tr><td>Price</td>");
        toClient.println("<td><input name='price' required></td></tr>");
        toClient.println("<tr><td>Stock</td>");
        toClient.println("<td><input name='stock' required></td></tr>");

        toClient.println("</table>");
        toClient.println("<input type='submit' value='Add book'>");
        toClient.println("</form>");
        toClient.println("</div>");

        toClient.println(Utils.footer());
        toClient.close();
    }
}
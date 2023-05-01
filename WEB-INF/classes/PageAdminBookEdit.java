import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class PageAdminBookEdit extends HttpServlet {
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
        toClient.println("<h3>Edit: <strong>" + book.title + "</strong></h3>");
        toClient.println("<form action='BookUpdate' method='GET'>");
        toClient.println("<table style='padding-bottom:15px'>");

        toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='ID' value='" + book.ID + "'></td></tr>");
        toClient.println("<tr><td>Title</td>");
        toClient.println("<td><input name='title' value='" + book.title + "'></td></tr>");
        toClient.println("<tr><td>Author</td>");
        toClient.println("<td><input name='author' value='" + book.author + "'></td></tr>");
        toClient.println("<tr><td>Publish year</td>");
        toClient.println("<td><input name='publish_year' value='" + book.publish_year + "'></td></tr>");
        toClient.println("<tr><td>Description</td>");
        toClient.println("<td><input name='blurb' value='" + book.blurb + "'></td></tr>");
        toClient.println("<tr><td>Genre</td>");
        toClient.println("<td><input name='genre' value='" + book.genre + "'></td></tr>");
        toClient.println("<tr><td>Cover image</td>");
        //  Add java function that gets image from ISBN number
        // Why not get everything from ISBN??
        toClient.println("<td><img class='cover' src='" + book.cover_image + "'><br><input name='cover_image' value='" + book.cover_image + "'></td></tr>");
        toClient.println("<tr><td>Price</td>");
        toClient.println("<td><input name='price' value='" + book.price + "'></td></tr>");
        toClient.println("<tr><td>Stock</td>");
        toClient.println("<td><input name='stock' value='" + book.stock + "'></td></tr>");

        toClient.println("</table>");
        toClient.println("<input type='submit' value='Update book'>");
        toClient.println("</form>");
        toClient.println("</div>");

        toClient.println(Utils.footer());
        toClient.close();
    }
}